package cc.canyi.core.command;

import cc.canyi.core.RuomoeCorePlugin;
import lombok.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

@Data
public class CommandEntity {
    private final String rootName;
    private final HashMap<String, ISubCommand> subCommandMap;

    public void apply(CommandSender sender, Command command, String label, String[] args) {
        if(args.length > 0) {
            String path = args[0];
            if(!testRun(path, sender, args)) {
                if(RuomoeCorePlugin.isDebug())
                    RuomoeCorePlugin.getInfoLogger().info("TryRun Failed -> " + path);
                testRun("help", sender, args);
            }
        }else testRun("help", sender, args);
    }

    public boolean testRun(String path, CommandSender sender, String[] args) {
        if(subCommandMap.containsKey(path)) {
            if(RuomoeCorePlugin.isDebug())
                RuomoeCorePlugin.getInfoLogger().info("TryRun Success -> " + path);
            subCommandMap.get(path).runCommand(sender, args);
            return true;
        }
        return false;
    }
}
