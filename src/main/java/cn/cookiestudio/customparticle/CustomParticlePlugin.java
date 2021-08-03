package cn.cookiestudio.customparticle;

import cn.cookiestudio.customparticle.command.CustomParticleCommand;
import cn.cookiestudio.customparticle.customparticle.JavaScriptCustomParticle;
import cn.cookiestudio.customparticle.util.Identifier;
import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import lombok.Getter;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
public class CustomParticlePlugin extends PluginBase {

    @Getter
    private static CustomParticlePlugin instance;
    private CustomParticlePool customParticlePool;
    private ParticleSender particleSender;
    private Path particleFilePath;

    @Override
    public void onEnable() {
        instance = this;
        customParticlePool = new CustomParticlePool();
        particleSender = new ParticleSender();
        particleFilePath = this.getDataFolder().toPath().resolve("particle");
        try {
            if(!Files.exists(particleFilePath))
                Files.createDirectories(particleFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadParticle();
        registerCommands();
        this.getLogger().info("§f============================================================");
        this.getLogger().info("§aPlugin enabled！");
        this.getLogger().info("§aThis plugin is free,if you get this by money,you may be cheated!");
        this.getLogger().info("§aCookie-Studio QQ Group:107533634");
        this.getLogger().info("§f============================================================");
    }

    public void reloadParticle(){
        this.customParticlePool.getCustomParticlePool().clear();
        Server.getInstance().getScheduler().cancelTask(this);
        loadParticle();
        this.getLogger().info("§aSuccessfully reload all particle files");
    }

    private void loadParticle(){
        try {
            Files.newDirectoryStream(particleFilePath,"*.js").forEach(script -> {
                String[] str = script.toFile().getName().split("\\.");
                customParticlePool.register(Identifier.from(str[0],str[1]),new JavaScriptCustomParticle(script));
                this.getLogger().info("§aSuccessfully loaded javascript particle file: " + script);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerCommands(){
        Server.getInstance().getCommandMap().register("",new CustomParticleCommand("cp"));
    }
}
