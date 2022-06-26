package cc.canyi.core.utils;

import cc.canyi.core.task.ITask;
import cc.canyi.core.task.TaskHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class BukkitTaskUtils {
    public static TaskHandler runTaskTimer(Plugin plugin, ITask task, long firstDelay, long delay) {
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, task, firstDelay, delay);
        return new TaskHandler(bukkitTask, task);
    }

    public static TaskHandler runTaskTimerAsync(Plugin plugin, ITask task, long firstDelay, long delay) {
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, task, firstDelay, delay);
        return new TaskHandler(bukkitTask, task);
    }

    public static TaskHandler runTaskLater(Plugin plugin, ITask task, long delay) {
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskLater(plugin, task, delay);
        return new TaskHandler(bukkitTask, task);
    }
    public static TaskHandler runTaskLaterAsync(Plugin plugin, ITask task, long delay) {
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delay);
        return new TaskHandler(bukkitTask, task);
    }
}
