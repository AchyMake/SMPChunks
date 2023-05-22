package net.achymake.smpchunks.listeners.entity;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.files.ChunkStorage;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplode implements Listener {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    public EntityExplode(SMPChunks smpChunks) {
        smpChunks.getServer().getPluginManager().registerEvents(this, smpChunks);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (chunkStorage.isProtected(event.getEntity().getLocation().getChunk())) {
            event.setCancelled(true);
        }
        if (chunkStorage.isClaimed(event.getEntity().getLocation().getChunk())) {
            if (event.getEntity().getType().equals(EntityType.MINECART_TNT))return;
            if (event.getEntity().getType().equals(EntityType.PRIMED_TNT))return;
            event.setCancelled(true);
        }
    }
}