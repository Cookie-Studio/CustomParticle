package cn.cookiestudio.customparticle.customparticle;

import cn.cookiestudio.customparticle.CustomParticlePlugin;
import cn.cookiestudio.customparticle.util.Identifier;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Position;
import cn.nukkit.scheduler.PluginTask;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@Getter
public abstract class CustomParticle implements BiFunction<Long,Position, Map<String,List<Position>>>{

    private Identifier identifier;

    public CustomParticle(Identifier identifier){
        this.identifier = identifier;
    }

    public void play(Position pos,boolean follow){
        Server.getInstance().getScheduler().scheduleRepeatingTask(CustomParticlePlugin.getInstance(),new ParticlePlayTask(pos,follow),1);
    }

    public class ParticlePlayTask extends PluginTask {

        private Position pos;
        private Map<String,List<Position>> particlePos;
        private Position startPos;
        private boolean follow;
        private long tick = 1;

        public ParticlePlayTask(Position pos,boolean follow) {
            super(CustomParticlePlugin.getInstance());
            this.pos = pos;
            this.startPos = pos.clone();
            this.follow = follow;
        }

        @Override
        public void onRun(int i) {
            if (follow){
                if ((particlePos = apply(tick,pos)) != null){
                    particlePos.forEach((effect,list) -> list.forEach(pos -> CustomParticlePlugin.getInstance().getParticleSender().sendParticle(effect,pos)));
                    tick++;
                    return;
                }
                this.cancel();
            }else{
                if ((particlePos = apply(tick,startPos)) != null){
                    particlePos.forEach((effect,list) -> list.forEach(pos -> CustomParticlePlugin.getInstance().getParticleSender().sendParticle(effect,pos)));
                    tick++;
                    return;
                }
                this.cancel();
            }
        }
    }
}
