package net.badbird5907.manhuntcore.menu;

import lombok.RequiredArgsConstructor;
import net.badbird5907.blib.menu.buttons.Button;
import net.badbird5907.blib.menu.menu.PaginatedMenu;
import net.badbird5907.blib.util.CC;
import net.badbird5907.blib.util.ItemBuilder;
import net.badbird5907.blib.util.XMaterial;
import net.badbird5907.manhuntcore.manager.impl.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

    private class PlayerButton extends Button {
        private final Player player1;

        public PlayerButton(Player player1) {
            this.player1 = player1;
            this.runner = PlayerManager.isRunner(player1);
        }

        boolean runner;

        @Override
        public ItemStack getItem(Player player) {
            boolean trackingRightNow = PlayerManager.getOnlinePlayerData(player).getTracking() == player1.getUniqueId();
            return new ItemBuilder(XMaterial.PLAYER_HEAD.parseMaterial()).name((runner ? CC.GREEN : CC.RED) + player1.getName()).lore(trackingRightNow ? CC.RED + "Click to remove tracker" : CC.YELLOW + "Click to track!").toSkullBuilder().withOwner(player1.getUniqueId()).buildSkull();
        }

        @Override
        public int getSlot() {
            return i++;
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {
            boolean trackingRightNow = PlayerManager.getOnlinePlayerData(player).getTracking() == player1.getUniqueId();
            if (trackingRightNow) {
                PlayerManager.getOnlinePlayerData(player).setTracking(null);
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1L, 1L);
                player.sendMessage(CC.GREEN + "You are no longer tracking " + player1.getName());
                for (ItemStack content : player.getInventory().getContents()) {
                    if (content.getType() == Material.COMPASS) {
                        ItemMeta meta = content.getItemMeta();
                        List<String> lore = meta.getLore();
                        lore.removeIf(s -> s.contains("Tracking"));
                        lore.add(CC.GREEN + "Tracking: " + CC.RED + "None");
                        meta.setLore(lore);
                    }
                }
                player.closeInventory();
                return;
            }
            PlayerManager.getOnlinePlayerData(player).setTracking(player1.getUniqueId());
            PlayerManager.getOnlinePlayerData(player).setTracking(null);
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1L, 1L);
            player.sendMessage(CC.GREEN + "You are now tracking " + player1.getName());
            for (ItemStack content : player.getInventory().getContents()) {
                if (content == null || content.getType() == Material.AIR)
                    continue;
                if (content.getType() == Material.COMPASS) {
                    ItemMeta meta = content.getItemMeta();
                    if (meta == null || meta.getLore() == null)
                        continue;
                    List<String> lore = meta.getLore();
                    lore.removeIf(s -> s.contains("Tracking"));
                    lore.add(CC.GREEN + "Tracking: " + CC.RED + player1.getName());
                    meta.setLore(lore);
                }
            }
            player.closeInventory();
        }
    }
}
