package net.badbird5907.manhuntcore.scenario;

import net.badbird5907.manhuntcore.ManhuntCore;
import org.bukkit.event.Listener;

public abstract class Scenario implements Listener {
    public abstract void load(ManhuntCore plugin);
    public abstract void enable(ManhuntCore plugin);
    public abstract void disable(ManhuntCore plugin);
    public ScenarioInfo getScenarioInfo() {
        return getClass().getAnnotation(ScenarioInfo.class);
    }
}
