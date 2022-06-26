package cc.canyi.core.command;

import org.bukkit.command.CommandSender;

public interface ISubCommand {
    void runCommand(CommandSender sender, String[] args);
}
