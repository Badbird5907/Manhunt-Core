package net.badbird5907.manhuntcore.menu;

import lombok.RequiredArgsConstructor;
import net.badbird5907.blib.menu.buttons.Button;
import net.badbird5907.blib.menu.menu.PaginatedMenu;
import net.badbird5907.blib.util.CC;
import net.badbird5907.blib.util.ItemBuilder;
import net.badbird5907.blib.util.XMaterial;
import net.badbird5907.manhuntcore.manager.impl.PlayerManager;
import net.badbird5907.manhuntcore.object.OnlinePlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayerManagerMenu extends PaginatedMenu {
    @Override
    public String getPagesTitle(Player player) {
        return CC.AQUA + "Player Manager";
    }

    @Override
    public List<Button> getPaginatedButtons(Player player) {
        List<Button> buttons = new ArrayList<>();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            buttons.add(new PlayerButton(onlinePlayer));
        }
        return buttons;
    }

    @Override
    public List<Button> getEveryMenuSlots(Player player) {
        return null;
    }
    private int i = 0;
    @RequiredArgsConstructor
    private class PlayerButton extends Button {
        private final Player target;
        @Override
        public ItemStack getItem(Player player) {
            OnlinePlayerData opd = PlayerManager.getOnlinePlayerData(target);
            boolean runner = PlayerManager.isRunner(target),hunter = PlayerManager.isHunter(target);
            return new ItemBuilder(XMaterial.PLAYER_HEAD.parseMaterial()).name((runner ? CC.GREEN : (hunter ? CC.RED : CC.GRAY)) + target.getName()).lore(CC.YELLOW + "Click to change role!").toSkullBuilder().withOwner(target.getUniqueId()).buildSkull();
        }

        @Override
        public int getSlot() {
            return i++;
        }

        @Override
        public void onClick(Player player, int slot, ClickType clickType) {

        }
    }
}
