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
public class PluginMain extends PluginBase {

    @Getter
    private static PluginMain instance;
    private CustomParticlePool customParticlePool;
    private Path particleFilePath;

    @Override
    public void onEnable() {
        instance = this;
        customParticlePool = new CustomParticlePool();
        particleFilePath = this.getDataFolder().toPath().resolve("particle");
        try {
            if(!Files.exists(particleFilePath))
                Files.createDirectories(particleFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadParticle();
        registerCommands();
        this.getLogger().info("§b插件启动完成！");
        this.getLogger().info("§e本插件完全免费，如果你是花钱买来的那你可能被坑啦~");
        this.getLogger().info("§aCookie-Studio QQ群:107533634");
    }

    public void reloadParticle(){
        this.customParticlePool.getCustomParticlePool().clear();
        Server.getInstance().getScheduler().cancelTask(this);
        loadParticle();
    }

    private void loadParticle(){
        try {
            Files.newDirectoryStream(particleFilePath,"*.js").forEach(script -> {
                String[] str = script.toFile().getName().split("\\.");
                customParticlePool.register(Identifier.from(str[0],str[1]),new JavaScriptCustomParticle(script));
                this.getLogger().info("成功加载js粒子文件: " + script);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerCommands(){
        Server.getInstance().getCommandMap().register("",new CustomParticleCommand("cp"));
    }
}
