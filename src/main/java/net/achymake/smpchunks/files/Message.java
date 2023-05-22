package net.achymake.smpchunks.files;

import net.achymake.smpchunks.SMPChunks;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Message {
    private final SMPChunks smpChunks;
    public Message (SMPChunks smpChunks) {
        this.smpChunks = smpChunks;
    }
    public void send(CommandSender sender, String message) {
        sender.sendMessage(color(message));
    }
    public void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(color(message)));
    }
    public void sendLog(String message) {
        smpChunks.getServer().getConsoleSender().sendMessage("[" + smpChunks.getName() + "] " + message);
    }
    public String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public SMPChunks getSmpChunks() {
        return smpChunks;
    }
}
