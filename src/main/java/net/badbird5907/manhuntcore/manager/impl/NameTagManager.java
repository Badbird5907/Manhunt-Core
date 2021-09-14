package net.badbird5907.manhuntcore.manager.impl;

import lombok.Getter;
import net.badbird5907.blib.util.CC;
import net.badbird5907.manhuntcore.ManhuntCore;
import net.badbird5907.manhuntcore.manager.Manager;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

@Getter
public class NameTagManager extends Manager {
    @Getter
    private static Team greenTeam,redTeam;
    @Override
    public void init(ManhuntCore plugin) {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        greenTeam = sb.registerNewTeam("runner");
        redTeam = sb.registerNewTeam("hunter");
        greenTeam.setPrefix(CC.GREEN);
        redTeam.setPrefix(CC.RED);
    }

    @Override
    public void disable(ManhuntCore plugin) {

    }
}
