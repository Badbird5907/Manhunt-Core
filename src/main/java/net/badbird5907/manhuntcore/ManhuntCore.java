package net.badbird5907.manhuntcore;

import lombok.Getter;
import lombok.Setter;
import net.badbird5907.blib.bLib;
import net.badbird5907.manhuntcore.manager.impl.NameTagManager;
import net.badbird5907.manhuntcore.manager.impl.PlayerManager;
import net.badbird5907.manhuntcore.manager.impl.ScenarioManager;
import net.badbird5907.manhuntcore.manager.impl.TrackerManager;
import net.badbird5907.manhuntcore.object.UpdateRunnable;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@Setter
public final class ManhuntCore extends JavaPlugin {
    public ManhuntCore() {
        super();
        instance = this;
    }
    @Getter private static ManhuntCore instance;

    private ScenarioManager scenarioManager;
    private PlayerManager playerManager;
    private TrackerManager trackerManager;
    private NameTagManager nameTagManager;

    @Override
    public void onEnable() {
        bLib.create(this);
        bLib.getCommandFramework().registerCommandsInPackage("net.badbird5907.manhuntcore.command");

        scenarioManager = new ScenarioManager();
        playerManager = new PlayerManager();
        trackerManager = new TrackerManager();
        nameTagManager = new NameTagManager();

        scenarioManager.init(this);
        playerManager.init(this);
        trackerManager.init(this);
        nameTagManager.init(this);

        new UpdateRunnable().runTaskTimer(this, 0, 10);
    }

    @Override
    public void onDisable() {
        scenarioManager.disable(this);
        playerManager.disable(this);
        trackerManager.disable(this);
        nameTagManager.disable(this);
    }
}
