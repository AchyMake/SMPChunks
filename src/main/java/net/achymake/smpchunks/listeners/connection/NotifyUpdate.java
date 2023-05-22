package net.achymake.smpchunks.listeners.connection;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.version.UpdateChecker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class NotifyUpdate implements Listener {
    public NotifyUpdate(SMPChunks smpChunks) {
        smpChunks.getServer().getPluginManager().registerEvents(this, smpChunks);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onNotifyUpdate(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("chunks.command.chunks.reload"))return;
        new UpdateChecker(SMPChunks.getInstance(), 0).sendMessage(player);
    }
}