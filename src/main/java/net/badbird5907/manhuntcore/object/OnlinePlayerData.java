package net.badbird5907.manhuntcore.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class OnlinePlayerData {
    private final UUID uuid;
    private final String name;
    private UUID tracking = null;
    public Player getPlayer(){
        return Bukkit.getPlayer(uuid);
    }
}
