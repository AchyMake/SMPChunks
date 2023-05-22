package net.achymake.smpchunks.commands.chunks.sub;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.commands.chunks.ChunksSubCommand;
import net.achymake.smpchunks.files.Message;
import org.bukkit.command.CommandSender;

public class Reload extends ChunksSubCommand {
    private final SMPChunks smpChunks = SMPChunks.getInstance();
    private final Message message = SMPChunks.getMessage();
    @Override
    public String getName() {
        return "reload";
    }
    @Override
    public String getDescription() {
        return "reload config files";
    }
    @Override
    public String getSyntax() {
        return "/chunks reload";
    }
    @Override
    public void perform(CommandSender sender, String[] args) {
        if (sender.hasPermission("smpchunks.command.chunks.reload")) {
            if (args.length == 1) {
                smpChunks.reload();
                message.send(sender, "&6SMPChunks reloaded");
            }
        }
    }
}