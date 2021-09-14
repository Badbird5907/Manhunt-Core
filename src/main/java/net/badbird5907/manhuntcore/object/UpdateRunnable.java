package net.badbird5907.manhuntcore.object;

import net.badbird5907.manhuntcore.manager.impl.TrackerManager;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateRunnable extends BukkitRunnable {
    @Override
    public void run() {
        TrackerManager.update();
    }
}
