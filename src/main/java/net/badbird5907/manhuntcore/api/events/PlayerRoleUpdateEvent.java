package net.badbird5907.manhuntcore.api.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.badbird5907.blib.event.SimpleEvent;
import net.badbird5907.manhuntcore.object.Role;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

@Getter
@RequiredArgsConstructor
public class PlayerRoleUpdateEvent extends SimpleEvent implements Cancellable {
    private final Player player;
    private final Role role;
    private boolean cancelled = false;
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
