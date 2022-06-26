package cc.canyi.core.task;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

@Getter
@RequiredArgsConstructor
public class TaskHandler {
    private final BukkitTask bukkitTask;
    private final ITask iTask;

    public void cancel() {
        this.bukkitTask.cancel();
        this.iTask.setCancel(true);
        Bukkit.getScheduler().cancelTask(this.bukkitTask.getTaskId());
    }
}
