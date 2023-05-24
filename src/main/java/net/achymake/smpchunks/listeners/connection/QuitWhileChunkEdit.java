package net.achymake.smpchunks.listeners.connection;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.files.ChunkStorage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitWhileChunkEdit implements Listener {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    public QuitWhileChunkEdit(SMPChunks smpChunks) {
        smpChunks.getServer().getPluginManager().registerEvents(this, smpChunks);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onQuitWhileChunkEdit(PlayerQuitEvent event) {
        if (!chunkStorage.getChunkEditors().contains(event.getPlayer()))return;
        chunkStorage.getChunkEditors().remove(event.getPlayer());
    }
}