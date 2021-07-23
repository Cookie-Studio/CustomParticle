package cn.cookiestudio.customparticle.customparticle;

import cn.cookiestudio.customparticle.math.MathUtil;
import cn.nukkit.level.ParticleEffect;
import cn.nukkit.level.Position;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import lombok.Getter;
import lombok.Setter;
import javax.script.ScriptEngineManager;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Getter
@Setter
public class JavaScriptCustomParticle extends CustomParticle{

    private Path scriptPath;
    private ScriptEngineManager scriptEngineManager;
    private NashornScriptEngine scriptEngine;
    private ParticleEffect particleEffect = ParticleEffect.LAVA_PARTICLE;

    public JavaScriptCustomParticle(Path scriptPath){
        this.scriptPath = scriptPath;
        scriptEngineManager = new ScriptEngineManager();
        scriptEngine = (NashornScriptEngine) scriptEngineManager.getEngineByName("nashorn");
        scriptEngine.put("mathUtil", MathUtil.getInstance());
        scriptEngine.put("particleEffect", ParticleEffect.values());
        scriptEngine.put("particle",this);
        try {
            scriptEngine.eval(Files.newBufferedReader(scriptPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Position> apply(Long tick, Position pos) {
        List<Position> returnValue = null;
        try {
            returnValue = (List<Position>) scriptEngine.invokeFunction("apply", tick, pos);
        }catch(Exception e){
            throw new RuntimeException("catch an exception when running script " + scriptPath,e);
        }
        return returnValue;
    }
}
