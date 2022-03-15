package net.badbird5907.manhuntcore.manager.impl;

import lombok.Getter;
import net.badbird5907.blib.util.CC;
import net.badbird5907.manhuntcore.ManhuntCore;
import net.badbird5907.manhuntcore.api.events.PlayerRoleUpdateEvent;
import net.badbird5907.manhuntcore.manager.Manager;
import net.badbird5907.manhuntcore.object.OnlinePlayerData;
import net.badbird5907.manhuntcore.object.Role;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerManager extends Manager implements Listener {
    @Getter
    private static Map<UUID, Role> players = new ConcurrentHashMap<>();
    @Getter
    private static Map<UUID, OnlinePlayerData> onlinePlayerDataMap = new ConcurrentHashMap<>();
    @Getter
    private static List<UUID> runners = new CopyOnWriteArrayList<>();
    @Getter
    private static List<UUID> hunters = new CopyOnWriteArrayList<>();
    @Override
    public void init(ManhuntCore plugin) {
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    public static void setPlayerRole(Player player,Role role){
        PlayerRoleUpdateEvent event = new PlayerRoleUpdateEvent(player,role);
        event.call();
        if (event.isCancelled())
            return;
        players.remove(player.getUniqueId());
        runners.remove(player.getUniqueId());
        hunters.remove(player.getUniqueId());

        players.put(player.getUniqueId(),role);

        player.sendMessage(CC.GREEN + "Your role has been changed to: " + CC.AQUA + role);
        if (role == Role.RUNNER){
            runners.add(player.getUniqueId());
        }else if (role == Role.HUNTER){
            hunters.add(player.getUniqueId());
        }
    }
    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        runners.remove(event.getPlayer().getUniqueId());
        hunters.remove(event.getPlayer().getUniqueId());
        onlinePlayerDataMap.remove(event.getPlayer().getUniqueId());
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if (players.containsKey(event.getPlayer().getUniqueId()))
            return;
        onlinePlayerDataMap.put(event.getPlayer().getUniqueId(),new OnlinePlayerData(event.getPlayer().getUniqueId(),event.getPlayer().getName()));
        Role role = players.get(event.getPlayer().getUniqueId());
        if (role == Role.RUNNER){
            runners.add(event.getPlayer().getUniqueId());
        }else if (role == Role.HUNTER){
            hunters.add(event.getPlayer().getUniqueId());
        }
    }
    public static Role getRole(Player player){
        if (players.get(player.getUniqueId()) == null)
            return Role.SPECTATOR;
        return players.get(player.getUniqueId());
    }
    public static boolean isRunner(Player player){
        return players.get(player.getUniqueId()) == Role.RUNNER;
    }
    public static boolean isHunter(Player player){
        return players.get(player.getUniqueId()) == Role.HUNTER;
    }
    public static OnlinePlayerData getOnlinePlayerData(Object object){
        if (object instanceof Player){
            return getOnlinePlayerData(((Player) object).getUniqueId());
        }
        return onlinePlayerDataMap.get((UUID) object);
    }
    @Override
    public void disable(ManhuntCore plugin) {

    }
}
