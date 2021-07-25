package cn.cookiestudio.customparticle.command;

import cn.cookiestudio.customparticle.CustomParticlePlugin;
import cn.cookiestudio.customparticle.util.Identifier;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.level.Position;

public class CustomParticleCommand extends Command {
    public CustomParticleCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] str) {
        if (commandSender instanceof ConsoleCommandSender)
            return true;
        else if (!commandSender.isOp())
            return true;
        if (str[0].equals("play")) {
            Identifier identifier = Identifier.fromString(str[1]);
            if (str.length <= 2){
                CustomParticlePlugin.getInstance().getCustomParticlePool().get(identifier).play((Position)commandSender, true);
            }else{
                if (str[2].equals("false")){
                    CustomParticlePlugin.getInstance().getCustomParticlePool().get(identifier).play((Position)commandSender, false);
                }else if (str[2].equals("true")){
                    CustomParticlePlugin.getInstance().getCustomParticlePool().get(identifier).play((Position)commandSender, true);
                }
            }
            return true;
        }else if (str[0].equals("reload")) {
            CustomParticlePlugin.getInstance().reloadParticle();
        }else if (str[0].equals("clear")) {
            Server.getInstance().getScheduler().cancelTask(CustomParticlePlugin.getInstance());
        }else if (str[0].equals("list")) {
            commandSender.sendMessage("§eParticle List:");
            CustomParticlePlugin.getInstance().getCustomParticlePool().getCustomParticlePool().forEach((id, p) -> {
                commandSender.sendMessage("ID: §a" + id);
            });
        }
        return true;
    }
}
