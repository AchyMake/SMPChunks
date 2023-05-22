package net.achymake.smpchunks.commands.chunks.sub;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.commands.chunks.ChunksSubCommand;
import net.achymake.smpchunks.files.ChunkStorage;
import net.achymake.smpcore.SMPCore;
import net.achymake.smpchunks.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Info extends ChunksSubCommand {
    private final ChunkStorage chunkStorage = SMPChunks.getChunkStorage();
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final Message message = SMPChunks.getMessage();
    @Override
    public String getName() {
        return "info";
    }
    @Override
    public String getDescription() {
        return "checks chunk info";
    }
    @Override
    public String getSyntax() {
        return "/chunks info";
    }
    @Override
    public void perform(CommandSender sender, String[] args) {
        if (sender.hasPermission("smpchunks.command.chunks.info")) {
            if (sender instanceof Player) {
                if (args.length == 1){
                    Player player = (Player) sender;
                    Chunk chunk = player.getLocation().getChunk();
                    if (chunkStorage.isClaimed(chunk)) {
                        message.send(player, "&6Chunks Info:&f Chunk");
                        message.send(player, "&6Owner:&f " + chunkStorage.getOwner(chunk).getName());
                        message.send(player, "&6Date claimed:&f " + chunkStorage.getDateClaimed(chunk));
                        message.send(player, "&6Chunks claimed:&f " + chunkStorage.getClaimedCount(chunk));
                        if (chunkStorage.getMembers(chunk).isEmpty()) {
                            message.send(player, chunkStorage.getOwner(chunk).getName() + "&6 has no members");
                        } else {
                            message.send(player, chunkStorage.getOwner(chunk).getName()+"&6 members:");
                            for (UUID uuid : chunkStorage.getMembersUUID(chunk)) {
                                message.send(player, "- " + sender.getServer().getOfflinePlayer(uuid).getName());
                            }
                        }
                    } else if (chunkStorage.isProtected(chunk)) {
                        message.send(player, "&6Chunks Info:&f Chunk");
                        message.send(player, "&6Owner:&f Server");
                    }
                }
                if (args.length == 2) {
                    OfflinePlayer target = sender.getServer().getOfflinePlayer(args[1]);
                    if (playerConfig.exist(target)) {
                        message.send(sender, "&6Chunks Info:&f "+target.getName());
                        message.send(sender, "&6Chunks claimed:&f " + chunkStorage.getClaimedCount(target));
                        if (chunkStorage.getMembers(target).isEmpty()) {
                            message.send(sender, target.getName() + "&6 has no members");
                        } else {
                            message.send(sender, "&6Members:");
                            for (UUID uuid : chunkStorage.getMembersUUID(target)) {
                                message.send(sender, "- " + sender.getServer().getOfflinePlayer(uuid).getName());
                            }
                        }
                    }
                }
            }
        }
    }
}