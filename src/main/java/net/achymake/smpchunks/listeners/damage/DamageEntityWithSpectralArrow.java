package net.achymake.smpchunks.listeners.damage;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.files.ChunkStorage;
import net.achymake.smpchunks.files.Message;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEntityWithSpectralArrow implements Listener {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    private final Message message = SMPChunks.getMessage();
    public DamageEntityWithSpectralArrow(SMPChunks smpChunks) {
        smpChunks.getServer().getPluginManager().registerEvents(this, smpChunks);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamageEntityWithSpectralArrow(EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.SPECTRAL_ARROW))return;
        if (event.getEntity().getType().equals(EntityType.PLAYER))return;
        if (chunkStorage.isProtected(event.getEntity().getLocation().getChunk())) {
            SpectralArrow damager = (SpectralArrow) event.getDamager();
            if (damager.getShooter() instanceof Player) {
                if (chunkStorage.hasAccess((Player) damager.getShooter(), event.getEntity().getLocation().getChunk()))return;
                if (SMPChunks.getInstance().getConfig().getBoolean("is-hostile." + event.getEntity().getType()))return;
                if (event.getEntity().isInvulnerable())return;
                event.setCancelled(true);
                if (damager.getShooter() == null)return;
                message.sendActionBar((Player) damager.getShooter(), "&cChunk is protected by&f Server");
            }
        }
        if (chunkStorage.isClaimed(event.getEntity().getLocation().getChunk())) {
            SpectralArrow damager = (SpectralArrow) event.getDamager();
            if (damager.getShooter() instanceof Player) {
                if (chunkStorage.hasAccess((Player) damager.getShooter(), event.getEntity().getLocation().getChunk()))return;
                if (SMPChunks.getInstance().getConfig().getBoolean("is-hostile." + event.getEntity().getType()))return;
                if (event.getEntity().isInvulnerable())return;
                event.setCancelled(true);
                if (damager.getShooter() == null)return;
                message.sendActionBar((Player) damager.getShooter(), "&cChunk is claimed by&f " + chunkStorage.getOwner(event.getEntity().getLocation().getChunk()).getName());
            }
        }
    }
}