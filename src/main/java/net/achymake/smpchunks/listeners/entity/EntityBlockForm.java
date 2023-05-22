package net.achymake.smpchunks.listeners.entity;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.files.ChunkStorage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;

public class EntityBlockForm implements Listener {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    public EntityBlockForm(SMPChunks smpChunks) {
        smpChunks.getServer().getPluginManager().registerEvents(this, smpChunks);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityBlockForm(EntityBlockFormEvent event) {
        if (chunkStorage.isProtected(event.getBlock().getChunk())) {
            event.setCancelled(true);
        }
    }
}