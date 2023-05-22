package net.achymake.smpchunks.listeners.interact;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.files.ChunkStorage;
import net.achymake.smpchunks.files.Message;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractBlock implements Listener {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    private final Message message = SMPChunks.getMessage();
    public InteractBlock(SMPChunks smpChunks) {
        smpChunks.getServer().getPluginManager().registerEvents(this, smpChunks);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteractBlock(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))return;
        if (event.getClickedBlock() == null)return;
        if (chunkStorage.isProtected(event.getClickedBlock().getLocation().getChunk())) {
            if (chunkStorage.hasAccess(event.getPlayer(), event.getClickedBlock().getLocation().getChunk()))return;
            if (!isCancelledProtected(event.getClickedBlock()))return;
            event.setCancelled(true);
            message.sendActionBar(event.getPlayer(), "&cChunk is owned by&f Server");
        }
        if (chunkStorage.isClaimed(event.getClickedBlock().getLocation().getChunk())) {
            if (chunkStorage.hasAccess(event.getPlayer(), event.getClickedBlock().getLocation().getChunk()))return;
            if (!isCancelledClaimed(event.getClickedBlock()))return;
            event.setCancelled(true);
            message.sendActionBar(event.getPlayer(), "&cChunk is owned by&f " + chunkStorage.getOwner(event.getClickedBlock().getChunk()).getName());
        }
    }
    public static boolean isCancelledClaimed(Block block) {
        if (Tag.CROPS.isTagged(block.getType())) {
            return true;
        }
        if (Tag.SHULKER_BOXES.isTagged(block.getType())) {
            return true;
        }
        if (Tag.FLOWER_POTS.isTagged(block.getType())) {
            return true;
        }
        if (Tag.ANVIL.isTagged(block.getType())) {
            return true;
        }
        if (Tag.CAMPFIRES.isTagged(block.getType())) {
            return true;
        }
        if (Tag.LOGS.isTagged(block.getType())) {
            return true;
        }
        if (Tag.TRAPDOORS.isTagged(block.getType())) {
            return true;
        }
        if (Tag.DOORS.isTagged(block.getType())) {
            return true;
        }
        if (Tag.BUTTONS.isTagged(block.getType())) {
            return true;
        }
        if (Tag.FENCE_GATES.isTagged(block.getType())) {
            return true;
        }
        if (Tag.CANDLES.isTagged(block.getType())) {
            return true;
        }
        if (block.getType().equals(Material.DISPENSER)) {
            return true;
        }
        if (block.getType().equals(Material.DROPPER)) {
            return true;
        }
        if (block.getType().equals(Material.HOPPER)) {
            return true;
        }
        if (block.getType().equals(Material.DAYLIGHT_DETECTOR)) {
            return true;
        }
        if (block.getType().equals(Material.LECTERN)) {
            return true;
        }
        if (block.getType().equals(Material.BARREL)) {
            return true;
        }
        if (block.getType().equals(Material.COMPARATOR)) {
            return true;
        }
        if (block.getType().equals(Material.REPEATER)) {
            return true;
        }
        if (block.getType().equals(Material.REDSTONE)) {
            return true;
        }
        if (block.getType().equals(Material.LEVER)) {
            return true;
        }
        if (block.getType().equals(Material.JUKEBOX)) {
            return true;
        }
        if (block.getType().equals(Material.NOTE_BLOCK)) {
            return true;
        }
        if (block.getType().equals(Material.BEEHIVE)) {
            return true;
        }
        if (block.getType().equals(Material.BEE_NEST)) {
            return true;
        }
        if (block.getType().equals(Material.RESPAWN_ANCHOR)) {
            return true;
        }
        if (block.getType().equals(Material.LODESTONE)) {
            return true;
        }
        if (block.getType().equals(Material.BEACON)) {
            return true;
        }
        if (block.getType().equals(Material.BELL)) {
            return true;
        }
        if (block.getType().equals(Material.BREWING_STAND)) {
            return true;
        }
        if (block.getType().equals(Material.SMOKER)) {
            return true;
        }
        if (block.getType().equals(Material.BLAST_FURNACE)) {
            return true;
        }
        if (block.getType().equals(Material.FURNACE)) {
            return true;
        }
        if (block.getType().equals(Material.CHEST)) {
            return true;
        }
        return false;
    }
    private boolean isCancelledProtected(Block block) {
        if (Tag.CROPS.isTagged(block.getType())) {
            return true;
        }
        if (Tag.FENCE_GATES.isTagged(block.getType())) {
            return true;
        }
        if (Tag.FLOWER_POTS.isTagged(block.getType())) {
            return true;
        }
        if (Tag.ANVIL.isTagged(block.getType())) {
            return true;
        }
        if (Tag.CANDLES.isTagged(block.getType())) {
            return true;
        }
        if (Tag.LOGS.isTagged(block.getType())) {
            return true;
        }
        if (Tag.TRAPDOORS.isTagged(block.getType())) {
            return true;
        }
        return false;
    }
}