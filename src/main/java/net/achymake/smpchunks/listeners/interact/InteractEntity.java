package net.achymake.smpchunks.listeners.interact;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.files.ChunkStorage;
import net.achymake.smpchunks.files.Message;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class InteractEntity implements Listener {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    private final Message message = SMPChunks.getMessage();
    public InteractEntity(SMPChunks smpChunks) {
        smpChunks.getServer().getPluginManager().registerEvents(this, smpChunks);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        if (chunkStorage.isProtected(event.getRightClicked().getLocation().getChunk())) {
            if (event.getRightClicked().getType().equals(EntityType.PLAYER))return;
            if (event.getRightClicked().getType().equals(EntityType.MINECART))return;
            if (event.getRightClicked().getType().equals(EntityType.BOAT))return;
            if (event.getRightClicked().isInvulnerable())return;
            if (chunkStorage.hasAccess(event.getPlayer(), event.getRightClicked().getLocation().getChunk()))return;
            if (SMPChunks.getInstance().getConfig().getBoolean("is-hostile." + event.getRightClicked().getType()))return;
            event.setCancelled(true);
            message.sendActionBar(event.getPlayer(), "&cChunk is protected by&f Server");
        }
        if (chunkStorage.isClaimed(event.getRightClicked().getLocation().getChunk())) {
            if (event.getRightClicked().getType().equals(EntityType.PLAYER))return;
            if (event.getRightClicked().getType().equals(EntityType.MINECART))return;
            if (event.getRightClicked().getType().equals(EntityType.BOAT))return;
            if (event.getRightClicked().isInvulnerable())return;
            if (chunkStorage.hasAccess(event.getPlayer(), event.getRightClicked().getLocation().getChunk()))return;
            if (SMPChunks.getInstance().getConfig().getBoolean("is-hostile." + event.getRightClicked().getType()))return;
            event.setCancelled(true);
            message.sendActionBar(event.getPlayer(), "&cChunk is owned by&f " + chunkStorage.getOwner(event.getRightClicked().getLocation().getChunk()).getName());
        }
    }
}