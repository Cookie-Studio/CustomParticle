package cn.cookiestudio.customparticle.customparticle;

import cn.cookiestudio.customparticle.PluginMain;
import cn.nukkit.Server;
import cn.nukkit.level.ParticleEffect;
import cn.nukkit.level.Position;
import cn.nukkit.scheduler.PluginTask;

import java.util.Arrays;
import java.util.function.BiFunction;

public abstract class CustomParticle implements BiFunction<Long,Position, Position[]> {
    abstract ParticleEffect getParticleEffect();
    abstract void setParticleEffect(ParticleEffect particleEffect);
    public void play(Position pos,boolean follow){
        Server.getInstance().getScheduler().scheduleRepeatingTask(PluginMain.getInstance(),new ParticlePlayTask(pos,follow),1);
    }

    public class ParticlePlayTask extends PluginTask {

        private Position pos;
        private Position[] particlePos;
        private Position startPos;
        private boolean follow;
        private long tick = 1;

        public ParticlePlayTask(Position pos,boolean follow) {
            super(PluginMain.getInstance());
            this.pos = pos;
            this.startPos = pos.clone();
            this.follow = follow;
        }

        @Override
        public void onRun(int i) {
            if (follow){
                if ((particlePos = apply(tick,pos)) != null){
                    Arrays.stream(particlePos).forEach(p -> startPos.level.addParticleEffect(p,getParticleEffect()));
                    tick++;
                    return;
                }
                this.cancel();
            }else{
                if ((particlePos = apply(tick,startPos)) != null){
                    Arrays.stream(particlePos).forEach(p -> startPos.level.addParticleEffect(p,getParticleEffect()));
                    tick++;
                    return;
                }
                this.cancel();
            }
        }
    }
}
