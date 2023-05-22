package net.achymake.smpchunks.commands.chunk;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.commands.chunk.sub.*;
import net.achymake.smpcore.SMPCore;
import net.achymake.smpchunks.files.Message;
import net.achymake.smpcore.files.PlayerConfig;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChunkCommand implements CommandExecutor, TabCompleter {
    private final PlayerConfig playerConfig = SMPCore.getPlayerConfig();
    private final Message message = SMPChunks.getMessage();
    private final ArrayList<ChunkSubCommand> chunkSubCommands = new ArrayList<>();

    public ChunkCommand(){
        chunkSubCommands.add(new Claim());
        chunkSubCommands.add(new Help());
        chunkSubCommands.add(new Members());
        chunkSubCommands.add(new TNT());
        chunkSubCommands.add(new Unclaim());
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            message.send(player, "&cUsage: &f/chunk help");
        }else{
            for (ChunkSubCommand commands : getSubCommands()){
                if (args[0].equals(commands.getName())){
                    commands.perform(player, args);
                }
            }
        }
        return true;
    }
    public ArrayList<ChunkSubCommand> getSubCommands(){
        return chunkSubCommands;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (args.length == 1){
            if (sender.hasPermission("smpchunks.command.chunk.claim")) {
                commands.add("claim");
            }
            if (sender.hasPermission("smpchunks.command.chunk.help")) {
                commands.add("help");
            }
            if (sender.hasPermission("smpchunks.command.chunk.members")) {
                commands.add("members");
            }
            if (sender.hasPermission("smpchunks.command.chunk.tnt")) {
                commands.add("tnt");
            }
            if (sender.hasPermission("smpchunks.command.chunk.unclaim")) {
                commands.add("unclaim");
            }
        }
        if (args.length == 2) {
            if (sender.hasPermission("smpchunks.command.chunk.members")) {
                if (args[0].equalsIgnoreCase("members")) {
                    commands.add("add");
                    commands.add("remove");
                }
            }
        }
        if (args.length == 3) {
            if (sender.hasPermission("smpchunks.command.chunk.members")) {
                if (args[0].equalsIgnoreCase("members")) {
                    if (args[1].equalsIgnoreCase("add")){
                        for (OfflinePlayer players : sender.getServer().getOfflinePlayers()) {
                            commands.add(players.getName());
                        }
                    }
                    if (args[1].equalsIgnoreCase("remove")) {
                        for (String uuidString : playerConfig.get((OfflinePlayer) sender).getStringList("members")) {
                            commands.add(sender.getServer().getOfflinePlayer(UUID.fromString(uuidString)).getName());
                        }
                    }
                }
            }
        }
        return commands;
    }
}