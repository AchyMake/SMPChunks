package net.achymake.smpchunks.listeners.block;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.files.ChunkStorage;
import net.achymake.smpchunks.files.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    private final Message message = SMPChunks.getMessage();
    public BlockBreak(SMPChunks smpChunks) {
        smpChunks.getServer().getPluginManager().registerEvents(this, smpChunks);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreak(BlockBreakEvent event) {
        if (chunkStorage.isProtected(event.getBlock().getChunk())) {
            if (chunkStorage.hasAccess(event.getPlayer(), event.getBlock().getChunk()))return;
            event.setCancelled(true);
            message.sendActionBar(event.getPlayer(), "&cChunk is owned by&f Server");
        }
        if (chunkStorage.isClaimed(event.getBlock().getChunk())) {
            if (chunkStorage.hasAccess(event.getPlayer(), event.getBlock().getChunk()))return;
            event.setCancelled(true);
            message.sendActionBar(event.getPlayer(), "&cChunk is owned by&f " + chunkStorage.getOwner(event.getBlock().getChunk()).getName());
        }
    }
}