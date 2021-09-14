package net.badbird5907.manhuntcore.command;

import net.badbird5907.blib.command.BaseCommand;
import net.badbird5907.blib.command.Command;
import net.badbird5907.blib.command.CommandResult;
import net.badbird5907.blib.command.Sender;
import net.badbird5907.blib.util.CC;

public class ManHuntCommand extends BaseCommand {
    @Command(name = "manhunt",aliases = {"manhuntcore"})
    public CommandResult execute(Sender sender, String[] args) {
        sender.sendMessage(CC.SEPARATOR);
        sender.sendMessage("&a/runner &6<add/list> [player] &b | Add a runner or list all the runners");
        sender.sendMessage("&a/hunter &6<add/list> [player] &b | Add a hunter or list all the hunters");
        sender.sendMessage("&a/manhunt &6scenario &b | Opens a menu to manage all scenarios");
        sender.sendMessage(CC.SEPARATOR);
        return CommandResult.SUCCESS;
    }
}
