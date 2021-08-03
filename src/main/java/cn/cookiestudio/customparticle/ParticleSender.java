package cn.cookiestudio.customparticle;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.level.Position;
import cn.nukkit.network.protocol.SpawnParticleEffectPacket;
import cn.nukkit.utils.Config;
import lombok.Getter;
import java.util.HashMap;

@Getter
public class ParticleSender {

    private Config setting = new Config(CustomParticlePlugin.getInstance().getDataFolder() + "/particleLimit.yml");
    private HashMap<Player,Long> sendCount = new HashMap<>();

    {
        Server.getInstance().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onPlayerQuit(PlayerQuitEvent event){
                sendCount.remove(event.getPlayer());
            }

            @EventHandler
            public void onPlayerJoin(PlayerJoinEvent event){
                sendCount.put(event.getPlayer(),0L);
                if (!setting.exists(event.getPlayer().getName())){
                    setting.set(event.getPlayer().getName(),1);
                    setting.save();
                }
            }
        },CustomParticlePlugin.getInstance());
    }

    public void sendParticle(String identifier, Position pos){
        sendParticle(identifier,pos,pos.level.getPlayers().values().toArray(new Player[0]));
    }

    public void sendParticle(String identifier, Position pos, Player[] players){
        for (Player player : players){
            double factor = setting.getDouble(player.getName());
            if (sendCount.get(player) % factor < 1){
                SpawnParticleEffectPacket packet = new SpawnParticleEffectPacket();
                packet.identifier = identifier;
                packet.dimensionId = pos.getLevel().getDimension();
                packet.position = pos.asVector3f();
                player.dataPacket(packet);
            }
            addSendCount(player);
        }
    }

    public void reloadSettingFile(){
        this.setting.reload();
    }

    private void addSendCount(Player player){
        sendCount.put(player,sendCount.get(player) + 1);
    }
}
