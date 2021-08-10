package cn.cookiestudio.customparticle.customparticle;

import cn.cookiestudio.customparticle.CustomParticlePlugin;
import cn.cookiestudio.customparticle.math.BVector3;
import cn.cookiestudio.customparticle.math.MathUtil;
import cn.cookiestudio.customparticle.util.Identifier;
import cn.nukkit.level.ParticleEffect;
import cn.nukkit.level.Position;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import lombok.Getter;
import lombok.Setter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class JavaScriptCustomParticle extends CustomParticle{

    private Path scriptPath;
    private NashornScriptEngine scriptEngine;

    public JavaScriptCustomParticle(Path scriptPath, Identifier identifier){
        super(identifier);
        this.scriptPath = scriptPath;
        NashornScriptEngineFactory scriptEngineFactory = new NashornScriptEngineFactory();
        scriptEngine = (NashornScriptEngine) scriptEngineFactory.getScriptEngine(new String[]{"-doe"}, CustomParticlePlugin.getInstance().getClass().getClassLoader(), str -> true);
        scriptEngine.put("effect", Arrays.stream(ParticleEffect.values()).map(effect -> effect.getIdentifier()).collect(Collectors.toList()).toArray());//todo: support custom particle
        scriptEngine.put("particle",this);
        try {
            scriptEngine.eval("var BVector3 = Java.type('" + BVector3.class.getName() + "');");
            scriptEngine.eval("var ArrayList = Java.type('" + ArrayList.class.getName() + "');");
            scriptEngine.eval("var HashMap = Java.type('" + HashMap.class.getName() + "');");
            scriptEngine.eval("var MathUtil = Java.type('" + MathUtil.class.getName() + "');");
            scriptEngine.eval(Files.newBufferedReader(scriptPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String,List<Position>> apply(Long tick, Position pos) {
        Map<String,List<Position>> returnValue = null;
        try {
            returnValue = (Map<String,List<Position>>) scriptEngine.invokeFunction("apply", tick, pos);
        }catch(Exception e){
            throw new RuntimeException("catch an exception when running script " + scriptPath,e);
        }
        return returnValue;
    }
}
