package net.achymake.smpchunks.commands.chunks.sub;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.commands.chunks.ChunksSubCommand;
import net.achymake.smpchunks.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import net.achymake.smpchunks.files.ChunkStorage;
import net.achymake.smpcore.SMPCore;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Edit extends ChunksSubCommand {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    private final Message message = SMPChunks.getMessage();
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
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
                    if (SMPChunks.getChunkStorage().hasChunkEdit(((Player) sender))) {
                        SMPChunks.getChunkStorage().getChunkEditors().add((Player) sender);
                        message.send(sender, "&6You entered chunk edit");
                    }else{
                        SMPChunks.getChunkStorage().getChunkEditors().remove((Player) sender);
                        message.send(sender, "&6You exited chunk edit");
                    }
                }
            }
        }
    }
}