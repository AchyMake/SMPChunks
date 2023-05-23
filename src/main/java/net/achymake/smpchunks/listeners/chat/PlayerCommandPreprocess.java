package net.achymake.smpchunks.listeners.chat;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.files.ChunkStorage;
import net.achymake.smpchunks.files.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocess implements Listener {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    private final Message message = SMPChunks.getMessage();
    public PlayerCommandPreprocess (SMPChunks smpChunks) {
        smpChunks.getServer().getPluginManager().registerEvents(this, smpChunks);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerCommandPreprocess (PlayerCommandPreprocessEvent event) {
        if (chunkStorage.isProtected(event.getPlayer().getLocation().getChunk())) {
            if (chunkStorage.hasAccess(event.getPlayer(),event.getPlayer().getLocation().getChunk()))return;
            if (!event.getMessage().startsWith("/sethome"))return;
            event.setCancelled(true);
            message.send(event.getPlayer(), "&cYou can't&f sethome&c inside&f Server&c's Chunk");
        }
        if (chunkStorage.isClaimed(event.getPlayer().getLocation().getChunk())) {
            if (chunkStorage.hasAccess(event.getPlayer(),event.getPlayer().getLocation().getChunk()))return;
            if (!event.getMessage().startsWith("/sethome"))return;
            event.setCancelled(true);
            message.sendActionBar(event.getPlayer(), "&cYou can't&f sethome&c inside&f " + chunkStorage.getOwner(event.getPlayer().getLocation().getChunk()).getName() + "&c's Chunk");
        }
    }
}