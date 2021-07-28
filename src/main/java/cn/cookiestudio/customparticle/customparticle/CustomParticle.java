package cn.cookiestudio.customparticle.customparticle;

import cn.cookiestudio.customparticle.CustomParticlePlugin;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Position;
import cn.nukkit.scheduler.PluginTask;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public abstract class CustomParticle implements BiFunction<Long,Position, Map<String,List<Position>>> , Cloneable{
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
                    particlePos.forEach((effect,list) -> list.forEach(pos -> pos.getLevel().addParticleEffect(pos.asVector3f(),effect,-1L,pos.getLevel().getDimension(), (Player[])null)));
                    tick++;
                    return;
                }
                this.cancel();
            }else{
                if ((particlePos = apply(tick,startPos)) != null){
                    particlePos.forEach((effect,list) -> list.forEach(pos -> pos.getLevel().addParticleEffect(pos.asVector3f(),effect,-1L,pos.getLevel().getDimension(), (Player[])null)));
                    tick++;
                    return;
                }
                this.cancel();
            }
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        throw new CloneNotSupportedException("class " + this.getClass().getName() + "haven't re-write clone() method,this is a bug,please send feedback to developer");
    }
}
