package net.badbird5907.manhuntcore.menu;

import net.badbird5907.blib.menu.buttons.Button;
import net.badbird5907.blib.menu.menu.Menu;
import net.badbird5907.blib.util.CC;
import org.bukkit.entity.Player;

import java.util.List;

public class MainMenu extends Menu {
    @Override
    public List<Button> getButtons(Player player) {
        return null;
    }

    @Override
    public String getName(Player player) {
        return "ManHunt-Core";
    }
}
