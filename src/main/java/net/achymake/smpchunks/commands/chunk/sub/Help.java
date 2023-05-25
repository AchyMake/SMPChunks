package net.achymake.smpchunks.commands.chunk.sub;

import net.achymake.smpchunks.commands.chunk.ChunkSubCommand;
import net.achymake.smpchunks.files.Message;
import net.achymake.smpchunks.SMPChunks;
import org.bukkit.entity.Player;

public class Help extends ChunkSubCommand {
    private final Message message = SMPChunks.getMessage();
    @Override
    public String getName() {
        return "help";
    }
    @Override
    public String getDescription() {
        return "checks chunk help";
    }
    @Override
    public String getSyntax() {
        return "/chunk help";
    }
    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("smpchunks.command.chunk.help")) {
            if (args.length == 1){
                message.send(player, "&6Chunk Help:");
                if (player.hasPermission("smpchunks.command.chunk.claim")) {
                    message.send(player, "/chunk claim&7 - claims current chunk");
                }
                message.send(player, "/chunk help&7 - show this list");
                if (player.hasPermission("smpchunks.command.chunk.members")) {
                    message.send(player, "/chunk members&7 - check member list");
                    message.send(player, "/chunk members add target&7 - add member");
                    message.send(player, "/chunk members remove target&7 - remove member");
                }
                if (player.hasPermission("smpchunks.command.chunk.tnt")) {
                    message.send(player, "/chunk tnt&7 - toggle tnt for the chunk");
                }
                if (player.hasPermission("smpchunks.command.chunk.unclaim")) {
                    message.send(player, "/chunk unclaim&7 - unclaims current chunk");
                }
            }
        }
    }
}