package net.achymake.smpchunks.commands.chunk.sub;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.commands.chunk.ChunkSubCommand;
import net.achymake.smpcore.SMPCore;
import net.achymake.smpchunks.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class Members extends ChunkSubCommand {
    private final Message message = SMPChunks.getMessage();
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    @Override
    public String getName() {
        return "members";
    }
    @Override
    public String getDescription() {
        return "add or removes members to the chunk";
    }
    @Override
    public String getSyntax() {
        return "/chunk members add/remove target";
    }
    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("smpchunks.command.chunk.members")) {
            if (args.length == 1) {
                if (playerConfig.get(player).getStringList("members").isEmpty()){
                    message.send(player, "&cYou don't have any members");
                } else {
                    message.send(player, "&6Chunk Members:");
                    for (String uuidListed : playerConfig.get(player).getStringList("members")) {
                        message.send(player, "- " + player.getServer().getOfflinePlayer(UUID.fromString(uuidListed)).getName());
                    }
                }
            }
            if (args.length == 3) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[2]);
                if (args[1].equalsIgnoreCase("add")) {
                    if (playerConfig.get(player).getStringList("members").contains(target.getUniqueId().toString())) {
                        message.send(player, "&cYou already have&f " + target.getName() + "&c as member");
                    } else {

                        List<String> members = playerConfig.get(player).getStringList("members");
                        members.add(target.getUniqueId().toString());
                        playerConfig.setStringList(player, "members", members);
                        message.send(player, "&6You added&f " + target.getName() + "&6 to members");
                    }
                }
                if (args[1].equalsIgnoreCase("remove")) {
                    if (playerConfig.get(player).getStringList("members").contains(target.getUniqueId().toString())) {
                        List<String> members = playerConfig.get(player).getStringList("members");
                        members.remove(target.getUniqueId().toString());
                        playerConfig.setStringList(player, "members", members);
                        message.send(player, "&6You removed&f " + target.getName() + "&6 from members");
                    } else {
                        message.send(player, "&6You don't have&f " + target.getName() + "&6 as member");
                    }
                }
            }
        }
    }
}