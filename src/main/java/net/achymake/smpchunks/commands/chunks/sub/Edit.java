package net.achymake.smpchunks.commands.chunks.sub;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.commands.chunks.ChunksSubCommand;
import net.achymake.smpchunks.files.Message;
import net.achymake.smpchunks.files.ChunkStorage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Edit extends ChunksSubCommand {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    private final Message message = SMPChunks.getMessage();
    @Override
    public String getName() {
        return "edit";
    }
    @Override
    public String getDescription() {
        return "allow to edit chunk";
    }
    @Override
    public String getSyntax() {
        return "/chunks edit";
    }
    @Override
    public void perform(CommandSender sender, String[] args) {
        if (sender.hasPermission("smpchunks.command.chunks.edit")) {
            if (args.length == 1) {
                if (sender instanceof Player) {
                    if (chunkStorage.hasChunkEdit(((Player) sender))) {
                        chunkStorage.getChunkEditors().remove((Player) sender);
                        message.send(sender, "&6You exited chunk edit");
                    } else {
                        chunkStorage.getChunkEditors().add((Player) sender);
                        message.send(sender, "&6You entered chunk edit");
                    }
                }
            }
        }
    }
}