package us.pcout.playtime;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlaytimeListener implements Listener {
    private final Playtime playtime;

    public PlaytimeListener(Playtime plugin) {
        playtime = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        playtime.savePlayer(event.getPlayer());
    }
}
