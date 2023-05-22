package net.achymake.smpchunks.listeners.entity;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.files.ChunkStorage;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class PrimedTNTExplode implements Listener {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    public PrimedTNTExplode(SMPChunks smpChunks) {
        smpChunks.getServer().getPluginManager().registerEvents(this, smpChunks);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPrimedTNTExplode(EntityExplodeEvent event) {
        if (!event.getEntity().getType().equals(EntityType.PRIMED_TNT))return;
        if (!chunkStorage.isClaimed(event.getEntity().getLocation().getChunk()))return;
        if (chunkStorage.TNTAllowed(event.getLocation().getChunk()))return;
        event.setCancelled(true);
    }
}