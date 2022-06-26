package cc.canyi.core.command;

import cc.canyi.core.RuomoeCorePlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CommandManager {

    private static final HashMap<String, CommandEntity> entity_cache_map = new LinkedHashMap<>();
    /**
     * 委托
     * @param sender 命令发送者
     * @param command 命令实体
     * @param label 父命令
     * @param args 参数
     * @return
     */
    public static boolean apply(CommandSender sender, Command command, String label, String[] args) {
        CommandEntity entity = entity_cache_map.get(label.toLowerCase());
        if(RuomoeCorePlugin.isDebug()) RuomoeCorePlugin.getInfoLogger().info("Apply " + entity);
        if(entity != null) {
            //有注册的命令
            entity.apply(sender, command, label, args);
        }
        return true;
    }

    /**
     * 注册子命令
     * @param commandRootName 父命令
     * @param subCommandName 子命令
     * @param subCommand 子命令接口
     */
    public static void registerSubCommand(String commandRootName, String subCommandName, ISubCommand subCommand) {
        commandRootName = commandRootName.toLowerCase();
        if(!entity_cache_map.containsKey(commandRootName)) {
            entity_cache_map.put(commandRootName, new CommandEntity(commandRootName, new HashMap<String, ISubCommand>()));
        }
        CommandEntity entity = entity_cache_map.get(commandRootName);
        entity.getSubCommandMap().put(subCommandName, subCommand);
        entity_cache_map.put(commandRootName, entity);
        if(RuomoeCorePlugin.isDebug()) {
            RuomoeCorePlugin.getInfoLogger().info("Register SubCommand Success -> " + commandRootName + " | " + subCommandName + " | " + subCommand.getClass().getName());
            RuomoeCorePlugin.getInfoLogger().info("Now Register SubCommand -> " + entity);
        }
    }

    /**
     * 清空
     */

    public static void clear() {
        entity_cache_map.clear();
    }

    /**
     * 取消注册
     * @param commandRootName 父命令
     */
    public static void unregisterCommand(String commandRootName) {
        entity_cache_map.remove(commandRootName);
    }
}
