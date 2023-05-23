package net.achymake.smpchunks.commands.chunk.sub;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.commands.chunk.ChunkSubCommand;
import net.achymake.smpchunks.files.ChunkStorage;
import net.achymake.smpchunks.files.Message;
import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class Claim extends ChunkSubCommand {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final Message message = SMPChunks.getMessage();
    @Override
    public String getName() {
        return "claim";
    }
    @Override
    public String getDescription() {
        return "claims the chunk";
    }
    @Override
    public String getSyntax() {
        return "/chunk claim";
    }
    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("smpchunks.command.chunk.claim")) {
            if (args.length == 1) {
                Chunk chunk = player.getLocation().getChunk();
                if (chunkStorage.isProtected(chunk)) {
                    message.send(player, "&cChunk is protected by&f Server");
                } else if (chunkStorage.isClaimed(chunk)) {
                    if (chunkStorage.isOwner(player ,chunk)) {
                        message.send(player, "&cYou already own this chunk");
                    } else {
                        message.send(player, "&cChunk already owned by " + chunkStorage.getOwner(chunk).getName());
                    }
                } else {
                    if (SMPChunks.getInstance().getConfig().getInt("claim.max-claims") > playerConfig.get(player).getInt("chunks.claimed")) {
                        if (SMPCore.getEconomyProvider().getBalance(player) >= SMPChunks.getInstance().getConfig().getDouble("claim.cost")) {
                            chunkStorage.claim(player, chunk);
                            chunkStorage.claimEffect(player);
                            message.send(player, "&6You bought a chunk for&a " + SMPCore.getEconomyProvider().currencyNameSingular() + SMPCore.getEconomyProvider().format(SMPChunks.getInstance().getConfig().getDouble("claim.cost")));
                        } else {
                            message.send(player, "&cYou don't have&a " + SMPCore.getEconomyProvider().currencyNameSingular() + SMPCore.getEconomyProvider().format(SMPChunks.getInstance().getConfig().getDouble("claim.cost")) + "&c to buy a chunk");
                        }
                    } else {
                        message.send(player, "&cYou have reach your limit of&f " + playerConfig.get(player).getInt("chunks.claimed") + "&c claims");
                    }
                }
            }
        }
    }
}