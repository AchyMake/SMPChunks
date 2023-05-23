package net.achymake.smpchunks.listeners.bed;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.files.ChunkStorage;
import net.achymake.smpchunks.files.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class PlayerBedEnter implements Listener {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    private final Message message = SMPChunks.getMessage();
    public PlayerBedEnter(SMPChunks smpChunks) {
        smpChunks.getServer().getPluginManager().registerEvents(this, smpChunks);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        if (chunkStorage.isProtected(event.getBed().getChunk())) {
            if (chunkStorage.hasAccess(event.getPlayer(), event.getBed().getChunk()))return;
            event.setCancelled(true);
            message.sendActionBar(event.getPlayer(), "&cChunk is protected by&f Server");
        }
        if (chunkStorage.isClaimed(event.getBed().getChunk())) {
            if (chunkStorage.hasAccess(event.getPlayer(), event.getBed().getChunk()))return;
            event.setCancelled(true);
            message.sendActionBar(event.getPlayer(), "&cChunk is owned by&f " + chunkStorage.getOwner(event.getBed().getChunk()).getName());
        }
    }
}