package net.achymake.smpchunks.commands.chunk.sub;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.commands.chunk.ChunkSubCommand;
import net.achymake.smpchunks.files.ChunkStorage;
import net.achymake.smpcore.SMPCore;
import net.achymake.smpchunks.files.Message;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class Unclaim extends ChunkSubCommand {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    private final Message message = SMPChunks.getMessage();
    @Override
    public String getName() {
        return "unclaim";
    }
    @Override
    public String getDescription() {
        return "unclaims current chunk";
    }
    @Override
    public String getSyntax() {
        return "/chunk unclaim";
    }
    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("smpchunks.command.chunk.unclaim")) {
            Chunk chunk = player.getLocation().getChunk();
            if (chunkStorage.isProtected(chunk)) {
                message.send(player, "&cChunk already owned by&f Server");
            } else if (chunkStorage.isClaimed(chunk)) {
                if (chunkStorage.isOwner(player, chunk)){
                    message.send(player, "&6You unclaimed a chunk and got refunded&a " + SMPCore.getEconomyProvider().currencyNameSingular() + SMPCore.getEconomyProvider().format(SMPChunks.getInstance().getConfig().getDouble("unclaim.refund")));
                    chunkStorage.unclaim(chunk);
                    chunkStorage.startUnclaimEffect(player);
                } else {
                    message.send(player, "&cChunk already owned by&f " + chunkStorage.getOwner(chunk).getName());
                }
            } else {
                message.send(player, "&cChunk already unclaimed");
            }
        }
    }
}