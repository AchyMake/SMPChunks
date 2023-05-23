package net.achymake.smpchunks.listeners.spawn;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.files.ChunkStorage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawn implements Listener {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    public CreatureSpawn(SMPChunks smpChunks) {
        smpChunks.getServer().getPluginManager().registerEvents(this, smpChunks);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (chunkStorage.isProtected(event.getEntity().getLocation().getChunk())) {
            if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM))return;
            if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.COMMAND))return;
            if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG))return;
            event.setCancelled(true);
        }
    }
}