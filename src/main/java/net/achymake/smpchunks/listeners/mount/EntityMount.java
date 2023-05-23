package net.achymake.smpchunks.listeners.mount;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.files.ChunkStorage;
import net.achymake.smpchunks.files.Message;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityMountEvent;

public class EntityMount implements Listener {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    private final Message message = SMPChunks.getMessage();
    public EntityMount(SMPChunks smpChunks) {
        smpChunks.getServer().getPluginManager().registerEvents(this, smpChunks);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityMount(EntityMountEvent event) {
        if (!event.getEntity().getType().equals(EntityType.PLAYER))return;
        if (event.getMount().getType().equals(EntityType.ARMOR_STAND))return;
        if (chunkStorage.isProtected(event.getMount().getLocation().getChunk())) {
            if (chunkStorage.hasAccess((Player) event.getEntity(), event.getMount().getLocation().getChunk()))return;
            event.setCancelled(true);
            message.sendActionBar((Player) event.getEntity(), "&cChunk is protected by&f Server");
        }
        if (chunkStorage.isClaimed(event.getMount().getLocation().getChunk())) {
            if (chunkStorage.hasAccess((Player) event.getEntity(), event.getMount().getLocation().getChunk()))return;
            event.setCancelled(true);
            message.sendActionBar((Player) event.getEntity(), "&cChunk is owned by&f " + chunkStorage.getOwner(event.getMount().getLocation().getChunk()).getName());
        }
    }
}