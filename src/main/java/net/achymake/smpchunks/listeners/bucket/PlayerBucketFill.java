package net.achymake.smpchunks.listeners.bucket;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.files.ChunkStorage;
import net.achymake.smpchunks.files.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class PlayerBucketFill implements Listener {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    private final Message message = SMPChunks.getMessage();
    public PlayerBucketFill(SMPChunks smpChunks) {
        smpChunks.getServer().getPluginManager().registerEvents(this, smpChunks);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerBucketFill(PlayerBucketFillEvent event) {
        if (chunkStorage.isProtected(event.getBlockClicked().getChunk())) {
            if (chunkStorage.hasAccess(event.getPlayer(), event.getBlockClicked().getChunk()))return;
            event.setCancelled(true);
            message.sendActionBar(event.getPlayer(), "&cChunk is protected by&f Server");
        }
        if (chunkStorage.isClaimed(event.getBlockClicked().getChunk())) {
            if (chunkStorage.hasAccess(event.getPlayer(), event.getBlockClicked().getChunk()))return;
            event.setCancelled(true);
            message.sendActionBar(event.getPlayer(), "&cChunk is owned by&f " + chunkStorage.getOwner(event.getBlockClicked().getChunk()).getName());
        }
    }
}