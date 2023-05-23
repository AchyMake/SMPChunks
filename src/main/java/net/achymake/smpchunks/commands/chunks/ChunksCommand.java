package net.achymake.smpchunks.commands.chunks;

import net.achymake.smpchunks.commands.chunks.sub.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ChunksCommand implements CommandExecutor, TabCompleter {
    private final ArrayList<ChunksSubCommand> chunksSubCommands = new ArrayList<>();

    public ChunksCommand() {
        chunksSubCommands.add(new Delete());
        chunksSubCommands.add(new Edit());
        chunksSubCommands.add(new Effect());
        chunksSubCommands.add(new Help());
        chunksSubCommands.add(new Info());
        chunksSubCommands.add(new Protect());
        chunksSubCommands.add(new Reload());
        chunksSubCommands.add(new SetOwner());
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            for (ChunksSubCommand commands : chunksSubCommands) {
                if (args[0].equals(commands.getName())) {
                    commands.perform(sender, args);
                }
            }
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("smpchunks.command.chunks.delete")) {
                commands.add("delete");
            }
            if (sender.hasPermission("smpchunks.command.chunks.edit")) {
                commands.add("edit");
            }
            if (sender.hasPermission("smpchunks.command.chunks.effect")) {
                commands.add("effect");
            }
            if (sender.hasPermission("smpchunks.command.chunks.help")) {
                commands.add("help");
            }
            if (sender.hasPermission("smpchunks.command.chunks.info")) {
                commands.add("info");
            }
            if (sender.hasPermission("smpchunks.command.chunks.protect")) {
                commands.add("protect");
            }
            if (sender.hasPermission("smpchunks.command.chunks.reload")) {
                commands.add("reload");
            }
            if (sender.hasPermission("smpchunks.command.chunks.setowner")) {
                commands.add("setowner");
            }
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("effect")) {
                if (sender.hasPermission("smpchunks.command.chunks.effect")) {
                    commands.add("claim");
                    commands.add("unclaim");
                }
            }
            if (args[0].equalsIgnoreCase("info")) {
                if (sender.hasPermission("smpchunks.command.chunks.info")) {
                    for (OfflinePlayer offlinePlayers : sender.getServer().getOfflinePlayers()) {
                        commands.add(offlinePlayers.getName());
                    }
                }
            }
            if (args[0].equalsIgnoreCase("setowner")) {
                if (sender.hasPermission("smpchunks.command.chunks.setowner")) {
                    for (OfflinePlayer offlinePlayers : sender.getServer().getOfflinePlayers()) {
                        commands.add(offlinePlayers.getName());
                    }
                }
            }
        }
        return commands;
    }
}