package net.achymake.smpchunks.commands.chunks.sub;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.commands.chunks.ChunksSubCommand;
import net.achymake.smpchunks.files.ChunkStorage;
import net.achymake.smpchunks.files.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Effect extends ChunksSubCommand {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    private final Message message = SMPChunks.getMessage();
    @Override
    public String getName() {
        return "effect";
    }
    @Override
    public String getDescription() {
        return "start claim/unclaim effects";
    }
    @Override
    public String getSyntax() {
        return "/chunks effect";
    }
    @Override
    public void perform(CommandSender sender, String[] args) {
        if (sender.hasPermission("smpchunks.command.chunks.effect")) {
            if (args.length == 2) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (args[1].equalsIgnoreCase("claim")) {
                        chunkStorage.claimEffect(player);
                        message.sendActionBar(player, "&6Started the effects of claiming");
                    }
                    if (args[1].equalsIgnoreCase("unclaim")) {
                        chunkStorage.unclaimEffect(player);
                        message.sendActionBar(player, "&6Started the effects of unclaiming");
                    }
                }
            }
        }
    }
}