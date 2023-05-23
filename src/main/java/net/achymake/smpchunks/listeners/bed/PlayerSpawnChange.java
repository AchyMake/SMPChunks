package net.achymake.smpchunks.listeners.bed;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.files.ChunkStorage;
import net.achymake.smpchunks.files.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSpawnChangeEvent;

public class PlayerSpawnChange implements Listener {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    private final Message message = SMPChunks.getMessage();
    public PlayerSpawnChange(SMPChunks smpChunks) {
        smpChunks.getServer().getPluginManager().registerEvents(this, smpChunks);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerSpawnChange(PlayerSpawnChangeEvent event) {
        if (chunkStorage.isProtected(event.getNewSpawn().getChunk())) {
            if (chunkStorage.hasAccess(event.getPlayer(), event.getNewSpawn().getChunk()))return;
            event.setCancelled(true);
            message.sendActionBar(event.getPlayer(), "&cChunk is protected by&f Server");
        }
        if (chunkStorage.isClaimed(event.getNewSpawn().getChunk())) {
            if (chunkStorage.hasAccess(event.getPlayer(), event.getNewSpawn().getChunk()))return;
            event.setCancelled(true);
            message.sendActionBar(event.getPlayer(), "&cChunk is owned by&f " + chunkStorage.getOwner(event.getNewSpawn().getChunk()).getName());
        }
    }
}