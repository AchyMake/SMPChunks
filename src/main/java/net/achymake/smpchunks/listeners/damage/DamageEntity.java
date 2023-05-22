package net.achymake.smpchunks.listeners.damage;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.files.ChunkStorage;
import net.achymake.smpchunks.files.Message;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEntity implements Listener {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    private final Message message = SMPChunks.getMessage();
    public DamageEntity(SMPChunks smpChunks) {
        smpChunks.getServer().getPluginManager().registerEvents(this, smpChunks);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamageEntity(EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.PLAYER))return;
        if (event.getEntity().getType().equals(EntityType.PLAYER))return;
        if (chunkStorage.isProtected(event.getEntity().getLocation().getChunk())) {
            if (chunkStorage.hasAccess((Player) event.getDamager(), event.getEntity().getLocation().getChunk()))return;
            if (SMPChunks.getInstance().getConfig().getBoolean("is-hostile."+event.getEntity().getType()))return;
            event.setCancelled(true);
            message.sendActionBar((Player) event.getDamager(), "&cChunk is owned by&f Server");
        }
        if (chunkStorage.isClaimed(event.getEntity().getLocation().getChunk())) {
            if (chunkStorage.hasAccess((Player) event.getDamager(), event.getEntity().getLocation().getChunk()))return;
            if (SMPChunks.getInstance().getConfig().getBoolean("is-hostile."+event.getEntity().getType()))return;
            event.setCancelled(true);
            message.sendActionBar((Player) event.getDamager(), "&cChunk is owned by&f " + chunkStorage.getOwner(event.getEntity().getLocation().getChunk()).getName());
        }
    }
}