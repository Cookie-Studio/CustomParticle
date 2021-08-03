package cn.cookiestudio.customparticle.command;

import cn.cookiestudio.customparticle.CustomParticlePlugin;
import cn.cookiestudio.customparticle.util.Identifier;
import cn.cookiestudio.easy4form.window.BFormWindowCustom;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.form.element.ElementSlider;
import cn.nukkit.form.element.ElementStepSlider;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.level.Position;

import java.util.ArrayList;
import java.util.List;

public class CustomParticleCommand extends Command {
    public CustomParticleCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] str) {
        if (commandSender instanceof ConsoleCommandSender)
            return true;
        if (str[0].equals("play")) {
            if (!commandSender.isOp())
                return true;
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
            if (!commandSender.isOp())
                return true;
            CustomParticlePlugin.getInstance().reloadParticle();
        }else if (str[0].equals("clear")) {
            if (!commandSender.isOp())
                return true;
            Server.getInstance().getScheduler().cancelTask(CustomParticlePlugin.getInstance());
        }else if (str[0].equals("list")) {
            if (!commandSender.isOp())
                return true;
            commandSender.sendMessage("§eParticle List:");
            CustomParticlePlugin.getInstance().getCustomParticlePool().getCustomParticlePool().forEach((id, p) -> {
                commandSender.sendMessage("ID: §a" + id);
            });
        }else if (str[0].equals("setting")) {
            Player player = (Player)commandSender;
            List<String> steps = new ArrayList<>();
            java.text.DecimalFormat df = new java.text.DecimalFormat("#.#");
            for (double i = 1.0;i <= 10.0;i += 0.1)
                steps.add(String.valueOf(df.format(i)));
            BFormWindowCustom.getBuilder()
                    .setTitle("粒子播放设置")
                    .addElements(new ElementStepSlider("设置粒子播放密度(数值越大密度越小)",steps,(int)(CustomParticlePlugin.getInstance().getParticleSender().getSetting().getDouble(player.getName()) / 0.1 - 10)))
                    .setResponseAction(e -> {
                        if (e.getResponse() == null)
                            return;
                        FormResponseCustom response = (FormResponseCustom) e.getResponse();
                        CustomParticlePlugin.getInstance().getParticleSender().getSetting().set(player.getName(),Integer.valueOf(response.getStepSliderResponse(0).getElementContent()));
                        CustomParticlePlugin.getInstance().getParticleSender().getSetting().save();
                        CustomParticlePlugin.getInstance().getParticleSender().reloadSettingFile();
                        commandSender.sendMessage("Successfully change setting");
                    })
                    .build()
                    .sendToPlayer(player);
        }
        return true;
    }
}
