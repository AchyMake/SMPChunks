package net.achymake.smpchunks.commands.chunks.sub;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.commands.chunks.ChunksSubCommand;
import net.achymake.smpchunks.files.ChunkStorage;
import net.achymake.smpchunks.files.Message;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Delete extends ChunksSubCommand {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    private final Message message = SMPChunks.getMessage();
    @Override
    public String getName() {
        return "delete";
    }
    @Override
    public String getDescription() {
        return "deletes the chunk";
    }
    @Override
    public String getSyntax() {
        return "/chunks delete";
    }
    @Override
    public void perform(CommandSender sender, String[] args) {
        if (sender.hasPermission("smpchunks.command.chunks.delete")) {
            if (sender instanceof Player) {
                if (args.length == 1){
                    Player player = (Player) sender;
                    Chunk chunk = player.getLocation().getChunk();
                    if (chunkStorage.isProtected(chunk)) {
                        chunkStorage.unprotect(chunk);
                        chunkStorage.unclaimEffect(player);
                        message.send(player, "&6Chunk is now unprotected");
                    } else if (chunkStorage.isClaimed(chunk)) {
                        message.send(player, "&6You safely unclaimed&f " + chunkStorage.getOwner(chunk).getName() + "&6 chunk");
                        chunkStorage.unclaim(chunk);
                        chunkStorage.unclaimEffect(player);
                    } else {
                        message.send(player, "&cChunk is already unclaimed");
                    }
                }
            }
        }
    }
}