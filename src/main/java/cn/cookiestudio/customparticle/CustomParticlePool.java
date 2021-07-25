package cn.cookiestudio.customparticle;

import cn.cookiestudio.customparticle.customparticle.CustomParticle;
import cn.cookiestudio.customparticle.util.Identifier;
import lombok.Getter;
import java.util.HashMap;

//a class storage all of the custom particle
@Getter
public class CustomParticlePool {

    private HashMap<Identifier, CustomParticle> customParticlePool = new HashMap<>();

    public void register(Identifier identifier,CustomParticle customParticle){
        this.customParticlePool.put(identifier,customParticle);
    }

    public CustomParticle remove(Identifier identifier) {
        return customParticlePool.remove(identifier);
    }

    public CustomParticle remove(String str){
        return remove(Identifier.fromString(str));
    }

    public CustomParticle get(Identifier identifier){
        if (!customParticlePool.containsKey(identifier))
            throw new RuntimeException("particle name " + identifier + " not found!");
        return customParticlePool.get(identifier);
    }

    public CustomParticle get(String str){
        return get(Identifier.fromString(str));
    }

    public CustomParticle getIfExist(Identifier identifier){
        return customParticlePool.getOrDefault(identifier,null);
    }

    public boolean containsKey(Identifier key) {
        return customParticlePool.containsKey(key);
    }
}
