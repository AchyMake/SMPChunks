package net.achymake.smpchunks.listeners.entity;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.files.ChunkStorage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class EntityChangeBlock implements Listener {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    public EntityChangeBlock(SMPChunks smpChunks) {
        smpChunks.getServer().getPluginManager().registerEvents(this, smpChunks);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (chunkStorage.isProtected(event.getBlock().getChunk())) {
            event.setCancelled(true);
        }
        if (chunkStorage.isClaimed(event.getBlock().getChunk())) {
            if (!SMPChunks.getInstance().getConfig().getBoolean("is-hostile."+event.getEntity()))return;
            event.setCancelled(true);
        }
    }
}