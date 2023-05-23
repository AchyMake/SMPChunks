package net.achymake.smpchunks.commands.chunks.sub;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.commands.chunks.ChunksSubCommand;
import net.achymake.smpchunks.files.ChunkStorage;
import net.achymake.smpchunks.files.Message;
import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Protect extends ChunksSubCommand {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final Message message = SMPChunks.getMessage();
    @Override
    public String getName() {
        return "protect";
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
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                Player player = (Player) sender;
                if (player.hasPermission("smpchunks.command.chunks.protect")) {
                    Chunk chunk = player.getLocation().getChunk();
                    if (chunkStorage.isProtected(chunk)) {
                        message.sendActionBar(player, "&cChunk is already protected");
                    } else if (chunkStorage.isClaimed(chunk)) {
                        message.sendActionBar(player, "&cChunk already owned by &f " + chunkStorage.getOwner(chunk).getName());
                    } else {
                        message.sendActionBar(player, "&6Chunk is now protected");
                        chunkStorage.protect(chunk);
                        chunkStorage.claimEffect(player);
                    }
                }
            }
        }
    }
}