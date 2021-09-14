package net.badbird5907.manhuntcore.menu;

import lombok.RequiredArgsConstructor;
import net.badbird5907.blib.menu.buttons.Button;
import net.badbird5907.blib.menu.menu.PaginatedMenu;
import net.badbird5907.blib.util.CC;
import net.badbird5907.blib.util.ItemBuilder;
import net.badbird5907.blib.util.XMaterial;
import net.badbird5907.manhuntcore.manager.impl.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrackerMenu extends PaginatedMenu {
    @Override
    public String getPagesTitle(Player player) {
        return "Tracker Menu";
    }

    @Override
    public List<Button> getPaginatedButtons(Player player) {
        List<Button> buttons = new ArrayList<>();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer == player)
                continue;
            buttons.add(new PlayerButton(onlinePlayer));
        }
        return buttons;
    }

    @Override
    public List<Button> getEveryMenuSlots(Player player) {
        return Collections.emptyList();
    }
    int i = 0;
    @RequiredArgsConstructor
    private class PlayerButton extends Button {
        private final Player player1;
        boolean runner = PlayerManager.isRunner(player1);
        @Override
        public ItemStack getItem(Player player) {
            boolean trackingRightNow = PlayerManager.getOnlinePlayerData(player).getTracking() == player1.getUniqueId();
            return new ItemBuilder(XMaterial.PLAYER_HEAD.parseMaterial()).name((runner ? CC.GREEN : CC.RED) + player.getName()).lore(trackingRightNow ? CC.RED + "Click to remove tracker" : CC.YELLOW + "Click to track!").build();
        }

        @Override
        public int getSlot() {
            return i++;
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            boolean trackingRightNow = PlayerManager.getOnlinePlayerData(player).getTracking() == player1.getUniqueId();
            if (trackingRightNow){
                PlayerManager.getOnlinePlayerData(player).setTracking(null);
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1L, 1L);
                player.closeInventory();
                return;
            }
            PlayerManager.getOnlinePlayerData(player).setTracking(player1.getUniqueId());
            PlayerManager.getOnlinePlayerData(player).setTracking(null);
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1L, 1L);
            player.closeInventory();
        }
    }
}
