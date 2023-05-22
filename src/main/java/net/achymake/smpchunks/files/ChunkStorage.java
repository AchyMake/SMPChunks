package net.achymake.smpchunks.files;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChunkStorage {
    private final SMPChunks smpChunks;
    public ChunkStorage(SMPChunks smpChunks) {
        this.smpChunks = smpChunks;
    }
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final List<Player> chunkEditors = new ArrayList<>();
    public PersistentDataContainer getData(Chunk chunk) {
        return chunk.getPersistentDataContainer();
    }
    public boolean hasAccess(Player player, Chunk chunk) {
        if (isProtected(chunk)) {
            return hasChunkEdit(player);
        }
        if (isClaimed(chunk)) {
            return isOwner(player, chunk) || isMember(player, chunk) || chunkEditors.contains(player);
        }
        return true;
    }
    public boolean hasChunkEdit(Player player) {
        return chunkEditors.contains(player);
    }
    public boolean isClaimed(Chunk chunk) {
        return getData(chunk).has(NamespacedKey.minecraft("owner"), PersistentDataType.STRING);
    }
    public boolean isOwner(Player player, Chunk chunk) {
        return getOwner(chunk) == player;
    }
    public void setOwner(Player player, OfflinePlayer target, Chunk chunk) {
        getData(chunk).set(NamespacedKey.minecraft("owner"), PersistentDataType.STRING, target.getUniqueId().toString());
        getData(chunk).set(NamespacedKey.minecraft("date-claimed"), PersistentDataType.STRING, SimpleDateFormat.getDateInstance().format(player.getLastPlayed()));
    }
    public boolean TNTAllowed(Chunk chunk) {
        return getData(chunk).has(NamespacedKey.minecraft("tnt"), PersistentDataType.STRING);
    }
    public OfflinePlayer getOwner(Chunk chunk) {
        String uuidString = getData(chunk).get(NamespacedKey.minecraft("owner"), PersistentDataType.STRING);
        return Bukkit.getOfflinePlayer(UUID.fromString(uuidString));
    }
    public String getDateClaimed(Chunk chunk) {
        return getData(chunk).get(NamespacedKey.minecraft("date-claimed"), PersistentDataType.STRING);
    }
    public boolean isMember(Player player, Chunk chunk) {
        return getMembers(chunk).contains(player.getUniqueId().toString());
    }
    public int getClaimedCount(Chunk chunk) {
        return playerConfig.get(getOwner(chunk)).getInt("chunks.claimed");
    }
    public int getClaimedCount(OfflinePlayer offlinePlayer) {
        return playerConfig.get(offlinePlayer).getInt("chunks.claimed");
    }
    public List<String> getMembers(Chunk chunk) {
        if (isClaimed(chunk)){
            return playerConfig.get(getOwner(chunk)).getStringList("members");
        } else {
            return new ArrayList<>();
        }
    }
    public List<UUID> getMembersUUID(Chunk chunk) {
        List<UUID> uuids = new ArrayList<>();
        for (String uuidString : playerConfig.get(getOwner(chunk)).getStringList("members")) {
            uuids.add(UUID.fromString(uuidString));
        }
        return uuids;
    }
    public List<String> getMembers(OfflinePlayer offlinePlayer) {
        List<String> members = new ArrayList<>();
        if (playerConfig.exist(offlinePlayer)){
            return playerConfig.get(offlinePlayer).getStringList("members");
        }else{
            return members;
        }
    }
    public List<UUID> getMembersUUID(OfflinePlayer offlinePlayer) {
        List<UUID> uuids = new ArrayList<>();
        if (playerConfig.exist(offlinePlayer)){
            for (String uuidString : getMembers(offlinePlayer)) {
                uuids.add(UUID.fromString(uuidString));
            }
        }
        return uuids;
    }
    public void startClaimEffect(Player player) {
        Particle particle = Particle.valueOf(SMPChunks.getInstance().getConfig().getString("claim.particle"));
        Location locationSouth = new Location(player.getWorld(), player.getLocation().getChunk().getBlock(15, 0, 8).getX(), player.getLocation().getBlockY()-3, player.getLocation().getChunk().getBlock(15, 0, 8).getZ());
        Location locationEast = new Location(player.getWorld(), player.getLocation().getChunk().getBlock(8, 0, 15).getX(), player.getLocation().getBlockY()-3, player.getLocation().getChunk().getBlock(8, 0, 15).getZ());
        player.playSound(player.getLocation(), Sound.valueOf(SMPChunks.getInstance().getConfig().getString("claim.sound.type")), Float.parseFloat(SMPChunks.getInstance().getConfig().getString("claim.sound.volume")), Float.parseFloat(SMPChunks.getInstance().getConfig().getString("claim.sound.pitch")));
        player.spawnParticle(particle, player.getLocation().getChunk().getBlock(8, 0, 0).getX(), player.getLocation().getBlockY()-3, player.getLocation().getChunk().getBlock(8, 0, 0).getZ(), 250, 4, 12, 0, 0);
        player.spawnParticle(particle, player.getLocation().getChunk().getBlock(0, 0, 8).getX(), player.getLocation().getBlockY()-3, player.getLocation().getChunk().getBlock(0, 0, 8).getZ(), 250, 0, 12, 4, 0);
        player.spawnParticle(particle,locationSouth.add(1, 0, 0), 250, 0, 12, 4, 0);
        player.spawnParticle(particle,locationEast.add(0, 0, 1), 250, 4, 12, 0, 0);
    }
    public void startUnclaimEffect(Player player) {
        Particle particle = Particle.valueOf(SMPChunks.getInstance().getConfig().getString("unclaim.particle"));
        Location locationSouth = new Location(player.getWorld(), player.getLocation().getChunk().getBlock(15, 0, 8).getX(), player.getLocation().getBlockY()-3, player.getLocation().getChunk().getBlock(15, 0, 8).getZ());
        Location locationEast = new Location(player.getWorld(), player.getLocation().getChunk().getBlock(8, 0, 15).getX(), player.getLocation().getBlockY()-3, player.getLocation().getChunk().getBlock(8, 0, 15).getZ());
        player.playSound(player.getLocation(), Sound.valueOf(SMPChunks.getInstance().getConfig().getString("unclaim.sound.type")),Float.parseFloat(SMPChunks.getInstance().getConfig().getString("unclaim.sound.volume")), Float.parseFloat(SMPChunks.getInstance().getConfig().getString("unclaim.sound.pitch")));
        player.spawnParticle(particle, player.getLocation().getChunk().getBlock(8, 0, 0).getX(), player.getLocation().getBlockY()-3, player.getLocation().getChunk().getBlock(8, 0, 0).getZ(), 250, 4, 12, 0, 0);
        player.spawnParticle(particle, player.getLocation().getChunk().getBlock(0, 0, 8).getX(), player.getLocation().getBlockY()-3, player.getLocation().getChunk().getBlock(0, 0, 8).getZ(), 250, 0, 12, 4, 0);
        player.spawnParticle(particle,locationSouth.add(1, 0, 0), 250, 0, 12, 4, 0);
        player.spawnParticle(particle,locationEast.add(0, 0, 1), 250, 4, 12, 0, 0);
    }
    public void claim(Player player,Chunk chunk) {
        SMPCore.getEconomyProvider().withdrawPlayer(player, SMPChunks.getInstance().getConfig().getDouble("claim.cost"));
        getData(chunk).set(NamespacedKey.minecraft("owner"), PersistentDataType.STRING,player.getUniqueId().toString());
        getData(chunk).set(NamespacedKey.minecraft("date-claimed"), PersistentDataType.STRING, SimpleDateFormat.getDateInstance().format(player.getLastPlayed()));
        playerConfig.setInt(getOwner(chunk),"chunks.claimed", playerConfig.get(getOwner(chunk)).getInt("chunks.claimed") + 1);
    }
    public void unclaim(Chunk chunk) {
        SMPCore.getEconomyProvider().depositPlayer(getOwner(chunk), SMPChunks.getInstance().getConfig().getDouble("unclaim.refund"));
        getData(chunk).remove(NamespacedKey.minecraft("date-claimed"));
        getData(chunk).remove(NamespacedKey.minecraft("owner"));
        playerConfig.setInt(getOwner(chunk),"chunks.claimed", playerConfig.get(getOwner(chunk)).getInt("chunks.claimed") - 1);
    }
    public boolean isProtected(Chunk chunk) {
        return getData(chunk).has(NamespacedKey.minecraft("protected"), PersistentDataType.STRING);
    }
    public void setProtected(Chunk chunk, String region) {
        getData(chunk).set(NamespacedKey.minecraft("protected"), PersistentDataType.STRING, "true");
    }
    public void removeProtected(Chunk chunk) {
        getData(chunk).remove(NamespacedKey.minecraft("protected"));
    }
    public List<Player> getChunkEditors() {
        return chunkEditors;
    }
    public SMPChunks getSmpChunks() {
        return smpChunks;
    }
}