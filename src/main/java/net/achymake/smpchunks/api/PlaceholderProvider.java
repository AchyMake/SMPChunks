package net.achymake.smpchunks.api;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.achymake.smpchunks.SMPChunks;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceholderProvider extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "smpchunks";
    }
    @Override
    public String getAuthor() {
        return "AchyMake";
    }
    @Override
    public String getVersion() {
        return SMPChunks.getInstance().getDescription().getVersion();
    }
    @Override
    public boolean canRegister() {
        return true;
    }
    @Override
    public boolean register() {
        return super.register();
    }
    @Override
    public boolean persist() {
        return true;
    }
    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return "";
        }
        if (params.equals("owner")) {
            if (SMPChunks.getChunkStorage().isProtected(player.getLocation().getChunk())) {
                return "Server";
            }
            if (SMPChunks.getChunkStorage().isClaimed(player.getLocation().getChunk())) {
                return SMPChunks.getChunkStorage().getOwner(player.getLocation().getChunk()).getName();
            }
            return "None";
        }
        if (params.equals("access")) {
            if (SMPChunks.getChunkStorage().isProtected(player.getLocation().getChunk()) || SMPChunks.getChunkStorage().isClaimed(player.getLocation().getChunk())) {
                if (SMPChunks.getChunkStorage().hasAccess(player, player.getLocation().getChunk())) {
                    return "True";
                }else {
                    return "False";
                }
            }
            return "True";
        }
        if (params.equals("claimed")) {
            return String.valueOf(SMPChunks.getChunkStorage().getClaimedCount(player));
        }
        if (params.equals("max_claims")) {
            return String.valueOf(SMPChunks.getInstance().getConfig().getInt("claim.max-claims"));
        }
        return super.onPlaceholderRequest(player, params);
    }
}
