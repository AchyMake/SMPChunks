package net.achymake.smpchunks.listeners.leash;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.files.ChunkStorage;
import net.achymake.smpchunks.files.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerLeashEntityEvent;

public class PlayerLeashEntity implements Listener {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    private final Message message = SMPChunks.getMessage();
    public PlayerLeashEntity(SMPChunks smpChunks) {
        smpChunks.getServer().getPluginManager().registerEvents(this, smpChunks);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLeashEntity(PlayerLeashEntityEvent event) {
        if (chunkStorage.isProtected(event.getEntity().getLocation().getChunk())) {
            if (chunkStorage.hasAccess(event.getPlayer(), event.getEntity().getLocation().getChunk()))return;
            event.setCancelled(true);
            message.sendActionBar(event.getPlayer(), "&cChunk is protected by&f Server");
        }
        if (chunkStorage.isClaimed(event.getEntity().getLocation().getChunk())) {
            if (chunkStorage.hasAccess(event.getPlayer(), event.getEntity().getLocation().getChunk()))return;
            event.setCancelled(true);
            message.sendActionBar(event.getPlayer(), "&cChunk is owned by&f " + chunkStorage.getOwner(event.getEntity().getLocation().getChunk()).getName());
        }
    }
}