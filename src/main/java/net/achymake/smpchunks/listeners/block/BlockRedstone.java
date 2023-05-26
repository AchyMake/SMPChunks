package net.achymake.smpchunks.listeners.block;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.files.ChunkStorage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockRedstone implements Listener {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    public BlockRedstone(SMPChunks smpChunks) {
        smpChunks.getServer().getPluginManager().registerEvents(this, smpChunks);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockRedstone(BlockRedstoneEvent event) {
        if (!SMPChunks.getInstance().getConfig().getBoolean("claim.redstone-only-inside"))return;
        if (!(chunkStorage.isClaimed(event.getBlock().getChunk()) || chunkStorage.isProtected(event.getBlock().getChunk()))) {
            event.setNewCurrent(0);
        }
    }
}