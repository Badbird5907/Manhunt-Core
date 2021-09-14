package net.badbird5907.manhuntcore.command;

import net.badbird5907.blib.command.BaseCommand;
import net.badbird5907.blib.command.Command;
import net.badbird5907.blib.command.CommandResult;
import net.badbird5907.blib.command.Sender;
import net.badbird5907.blib.util.CC;
import net.badbird5907.manhuntcore.manager.impl.PlayerManager;
import net.badbird5907.manhuntcore.object.Role;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HunterCommand extends BaseCommand {
    @Command(name = "hunter",permission = "manhunt.command.hunter",usage = "<add/list> [player]")
    public CommandResult execute(Sender sender, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("list")){
            sender.sendMessage(CC.SEPARATOR);
            PlayerManager.getPlayers().forEach(((uuid, role) -> {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null){
                    if (role != Role.HUNTER)
                        return;
                    sender.sendMessage(CC.AQUA + player.getName() + CC.GREEN + " | Hunter");
                }
            }));
            sender.sendMessage(CC.SEPARATOR);
            return CommandResult.SUCCESS;
        }
        else if (args.length == 2 && args[0].equalsIgnoreCase("add")){
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null)
                return CommandResult.INVALID_PLAYER;
            try{
                PlayerManager.setPlayerRole(target,Role.HUNTER);
            } catch (IllegalArgumentException e) {
                return CommandResult.INVALID_ARGS;
            }
        }
        return CommandResult.INVALID_ARGS;
    }
}
