package net.badbird5907.manhuntcore.manager.impl;

import net.badbird5907.blib.util.CC;
import net.badbird5907.blib.util.ItemBuilder;
import net.badbird5907.blib.util.XMaterial;
import net.badbird5907.manhuntcore.ManhuntCore;
import net.badbird5907.manhuntcore.api.events.PlayerRoleUpdateEvent;
import net.badbird5907.manhuntcore.manager.Manager;
import net.badbird5907.manhuntcore.menu.TrackerMenu;
import net.badbird5907.manhuntcore.object.Role;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class TrackerManager extends Manager implements Listener {
    private static final NamespacedKey key = new NamespacedKey(ManhuntCore.getInstance(),"tracker");
    private static final NamespacedKey trackingkey = new NamespacedKey(ManhuntCore.getInstance(),"tracking");
    private static final ItemStack COMPASS = new ItemBuilder(XMaterial.COMPASS.parseItem()).name(CC.AQUA + "Player Tracker").lore(CC.GREEN + "Tracking " + CC.RED + "None").glow().build();
    static {
        ItemMeta meta = COMPASS.getItemMeta();
        meta.getPersistentDataContainer().set(key,PersistentDataType.INTEGER,1);
        COMPASS.setItemMeta(meta);
    }
    @Override
    public void init(ManhuntCore plugin) {
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @Override
    public void disable(ManhuntCore plugin) {

    }
    @EventHandler
    public void onRoleUpdate(PlayerRoleUpdateEvent event){
        if (event.getRole() == Role.HUNTER){
            event.getPlayer().getInventory().addItem(COMPASS);
            NameTagManager.getRedTeam().addPlayer(event.getPlayer());
        }
        else if (event.getRole() == Role.RUNNER){
            for (ItemStack itemStack : event.getPlayer().getInventory()) {
                if (isTrackerCompass(itemStack))
                    event.getPlayer().getInventory().removeItem(itemStack);
            }
            NameTagManager.getGreenTeam().addPlayer(event.getPlayer());
        }
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        if (PlayerManager.isHunter(event.getEntity())){
            event.getDrops().removeIf(TrackerManager::isTrackerCompass);
            event.getEntity().getInventory().addItem(COMPASS);
        }
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        if (event.getPlayer().getInventory().getItemInMainHand() != null && isTrackerCompass(event.getPlayer().getInventory().getItemInMainHand())){
            if (PlayerManager.isHunter(event.getPlayer())){
                event.setCancelled(true);
                new TrackerMenu().open(event.getPlayer());
            }
        }
    }
    public static void update(){
        for (UUID hunter : PlayerManager.getHunters()) {
            Player player = Bukkit.getPlayer(hunter);
            if (player == null)
                continue;
            UUID target = PlayerManager.getOnlinePlayerData(player).getTracking();
            if (target == null)
                continue;
            Player targetPlayer = Bukkit.getPlayer(target);
            if (targetPlayer == null){
                continue;
            }
            player.setCompassTarget(targetPlayer.getLocation());
        }
    }

    /**
     * useless atm
     * @param itemStack
     */
    @Deprecated
    public static void cycleTracking(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        int index = 0;
        if (meta.getPersistentDataContainer().has(trackingkey,PersistentDataType.INTEGER)){
            index = meta.getPersistentDataContainer().get(trackingkey,PersistentDataType.INTEGER).intValue();
            meta.getPersistentDataContainer().remove(trackingkey);
        }
        int max = PlayerManager.getRunners().size();
        if (index > PlayerManager.getRunners().size())
            index = 0;

        itemStack.setItemMeta(meta);
    }
    public static boolean isTrackerCompass(ItemStack itemStack){
        if (itemStack == null)return false;
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null || meta.getPersistentDataContainer() == null)
            return false;
        if (meta.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)){
            return meta.getPersistentDataContainer().get(key,PersistentDataType.INTEGER).intValue() == 1;
        }return false;
    }
}
