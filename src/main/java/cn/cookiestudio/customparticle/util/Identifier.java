package cn.cookiestudio.customparticle.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Copyed from cloudburst-server
 */
@JsonDeserialize(
        using = Identifier.Deserializer.class
)
@JsonSerialize(
        using = ToStringSerializer.class
)
public final class Identifier implements Comparable<Identifier> {
    private static final char NAMESPACE_SEPARATOR = ':';
    public static final Identifier EMPTY = new Identifier("", "", String.valueOf(':'));
    private static final Pattern IDENTIFIER_PATTERN = Pattern.compile("^(?>minecraft:)?(?>([a-z0-9_.]*):)?([a-zA-Z0-9_.]*)$");
    private static final ThreadLocal<Matcher> MATCHER_THREAD_LOCAL = ThreadLocal.withInitial(() -> {
        return IDENTIFIER_PATTERN.matcher("");
    });
    private static final Lock READ_LOCK;
    private static final Lock WRITE_LOCK;
    private static final Map<String, Identifier> VALUES;
    private final String namespace;
    private final String name;
    private final String fullName;

    private Identifier(String namespace, String name, String fullName) {
        this.namespace = namespace;
        this.name = name;
        this.fullName = fullName;
    }

    @JsonCreator
    public static Identifier fromString(String identifier) {
        if (((String)Preconditions.checkNotNull(identifier, "identifier")).isEmpty()) {
            return EMPTY;
        } else {
            Matcher matcher = ((Matcher)MATCHER_THREAD_LOCAL.get()).reset(identifier);
            Preconditions.checkArgument(matcher.find(), "Invalid identifier: \"%s\"", identifier);
            READ_LOCK.lock();

            Identifier id;
            try {
                id = (Identifier)VALUES.get(identifier);
            } finally {
                READ_LOCK.unlock();
            }

            if (id == null) {
                String namespace = matcher.group(1);
                String name = matcher.group(2);
                String fullName = namespace == null && !identifier.startsWith("minecraft:") ? "minecraft:" + name : identifier;
                WRITE_LOCK.lock();

                try {
                    if ((id = (Identifier)VALUES.get(identifier)) == null) {
                        id = new Identifier(namespace == null ? "minecraft" : namespace, name, fullName);
                        if (namespace == null) {
                            VALUES.put(name, id);
                        }

                        VALUES.put(fullName, id);
                    }
                } finally {
                    WRITE_LOCK.unlock();
                }
            }

            return id;
        }
    }

    public static Identifier from(String space, String name) {
        if (Strings.isNullOrEmpty(space)) {
            return Strings.isNullOrEmpty(name) ? EMPTY : fromString(name);
        } else {
            return fromString(space + ":" + name);
        }
    }

    public String getName() {
        return this.name;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String toString() {
        return this.fullName;
    }

    public int compareTo(Identifier o) {
        return this.fullName.compareTo(o.fullName);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Identifier))
            return false;
        return compareTo((Identifier) obj) == 0 ? true : false;
    }

    static {
        VALUES = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        ReadWriteLock lock = new ReentrantReadWriteLock();
        READ_LOCK = lock.readLock();
        WRITE_LOCK = lock.writeLock();
    }

    static final class Deserializer extends JsonDeserializer<Identifier> {
        Deserializer() {
        }

        public Identifier deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return Identifier.fromString(p.getText());
        }
    }
}

