package net.achymake.smpchunks.listeners.interact;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.files.ChunkStorage;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractPhysical implements Listener {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    public InteractPhysical(SMPChunks smpChunks) {
        smpChunks.getServer().getPluginManager().registerEvents(this, smpChunks);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteractPhysical(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.PHYSICAL))return;
        if (event.getClickedBlock() == null)return;
        if (chunkStorage.isProtected(event.getClickedBlock().getLocation().getChunk())) {
            if (chunkStorage.hasAccess(event.getPlayer(), event.getClickedBlock().getLocation().getChunk()))return;
            if (!isCancelled(event.getClickedBlock()))return;
            event.setCancelled(true);
        }
        if (chunkStorage.isClaimed(event.getClickedBlock().getLocation().getChunk())) {
            if (chunkStorage.hasAccess(event.getPlayer(), event.getClickedBlock().getLocation().getChunk()))return;
            if (!isCancelled(event.getClickedBlock()))return;
            event.setCancelled(true);
        }
    }
    private boolean isCancelled(Block block) {
        if (Tag.PRESSURE_PLATES.isTagged(block.getType())) {
            return true;
        }
        if (block.getType().equals(Material.FARMLAND)) {
            return true;
        }
        if (block.getType().equals(Material.TURTLE_EGG)) {
            return true;
        }
        return false;
    }
}