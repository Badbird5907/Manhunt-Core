package net.badbird5907.manhuntcore;

import lombok.Getter;
import lombok.Setter;
import net.badbird5907.blib.bLib;
import net.badbird5907.manhuntcore.manager.impl.NameTagManager;
import net.badbird5907.manhuntcore.manager.impl.PlayerManager;
import net.badbird5907.manhuntcore.manager.impl.ScenarioManager;
import net.badbird5907.manhuntcore.manager.impl.TrackerManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@Setter
public final class ManhuntCore extends JavaPlugin {
    @Getter private static ManhuntCore instance;

    private ScenarioManager scenarioManager = new ScenarioManager();
    private PlayerManager playerManager = new PlayerManager();
    private TrackerManager trackerManager = new TrackerManager();
    private NameTagManager nameTagManager = new NameTagManager();

    @Override
    public void onEnable() {
        instance = this;
        bLib.create(this);
        bLib.getCommandFramework().registerCommandsInPackage("net.badbird5907.manhuntcore.command");
        scenarioManager.init(this);
        playerManager.init(this);
        trackerManager.init(this);
        nameTagManager.init(this);
    }

    @Override
    public void onDisable() {
        scenarioManager.disable(this);
        playerManager.disable(this);
        trackerManager.disable(this);
        nameTagManager.disable(this);
    }
}
