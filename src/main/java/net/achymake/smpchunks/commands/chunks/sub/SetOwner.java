package net.achymake.smpchunks.commands.chunks.sub;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.commands.chunks.ChunksSubCommand;
import net.achymake.smpchunks.files.ChunkStorage;
import net.achymake.smpchunks.files.Message;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetOwner extends ChunksSubCommand {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    private final Message message = SMPChunks.getMessage();
    @Override
    public String getName() {
        return "setowner";
    }
    @Override
    public String getDescription() {
        return "sets owner of the chunk";
    }
    @Override
    public String getSyntax() {
        return "/chunks setowner";
    }
    @Override
    public void perform(CommandSender sender, String[] args) {
        if (sender.hasPermission("smpchunks.command.chunks.setowner")) {
            if (sender instanceof Player) {
                if (args.length == 2) {
                    if (chunkStorage.isProtected(((Player) sender).getLocation().getChunk())) {
                        message.send(sender, "&cChunk is protected by&f Server");
                    } else {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                        chunkStorage.setOwner((Player) sender, target, ((Player) sender).getLocation().getChunk());
                        chunkStorage.claimEffect((Player) sender);
                        message.send(sender, "&6Chunk is now owned by&f " + chunkStorage.getOwner(((Player) sender).getLocation().getChunk()).getName());
                    }
                }
            }
        }
    }
}