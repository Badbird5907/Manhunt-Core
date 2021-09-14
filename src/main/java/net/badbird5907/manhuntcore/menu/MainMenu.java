package net.badbird5907.manhuntcore.menu;

import net.badbird5907.blib.menu.buttons.Button;
import net.badbird5907.blib.menu.menu.Menu;
import net.badbird5907.blib.util.CC;
import net.badbird5907.blib.util.ItemBuilder;
import net.badbird5907.blib.util.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class MainMenu extends Menu {
    @Override
    public List<Button> getButtons(Player player) {
        return Arrays.asList(new ConfigButton());
    }

    @Override
    public String getName(Player player) {
        return "Manhunt Core";
    }

    private class ConfigButton extends Button {

        @Override
        public ItemStack getItem(Player player) {
            return new ItemBuilder(XMaterial.COMPARATOR.parseMaterial()).name(CC.GOLD + "Edit Config").lore(CC.YELLOW + "Click to edit config!").build();
        }

        @Override
        public int getSlot() {
            return 10;
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            super.onClick(player, slot, clickType);
        }
    }
    private class PlayerManagerButton extends Button {

        @Override
        public ItemStack getItem(Player player) {
            return null;
        }

        @Override
        public int getSlot() {
            return 0;
        }
    }
}
