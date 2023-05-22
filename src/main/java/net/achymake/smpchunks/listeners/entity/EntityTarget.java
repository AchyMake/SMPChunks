package net.achymake.smpchunks.listeners.entity;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.files.ChunkStorage;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityTarget implements Listener {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    public EntityTarget(SMPChunks smpChunks) {
        smpChunks.getServer().getPluginManager().registerEvents(this, smpChunks);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityTarget(EntityTargetEvent event) {
        if (event.getTarget() == null)return;
        if (!event.getTarget().getType().equals(EntityType.PLAYER))return;
        if (chunkStorage.isProtected(event.getEntity().getLocation().getChunk())) {
            if (chunkStorage.hasAccess((Player) event.getTarget(), event.getEntity().getLocation().getChunk()))return;
            if (SMPChunks.getInstance().getConfig().getBoolean("is-hostile."+event.getEntity().getType()))return;
            event.setCancelled(true);
        }
        if (chunkStorage.isClaimed(event.getEntity().getLocation().getChunk())) {
            if (chunkStorage.hasAccess((Player) event.getTarget(), event.getEntity().getLocation().getChunk()))return;
            if (SMPChunks.getInstance().getConfig().getBoolean("is-hostile."+event.getEntity().getType()))return;
            event.setCancelled(true);
        }
    }
}