package net.achymake.smpchunks.commands.chunk.sub;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.commands.chunk.ChunkSubCommand;
import net.achymake.smpchunks.files.ChunkStorage;
import net.achymake.smpcore.SMPCore;
import net.achymake.smpcore.files.Message;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class TNT extends ChunkSubCommand {
    private final SMPChunks smpChunks = SMPChunks.getInstance();
    private final ChunkStorage chunkStorage = smpChunks.getChunkStorage();
    private final SMPCore smpCore = SMPCore.getInstance();
    private final Message message = smpCore.getMessage();
    @Override
    public String getName() {
        return "tnt";
    }
    @Override
    public String getDescription() {
        return "toggle tnt";
    }
    @Override
    public String getSyntax() {
        return "/chunk tnt";
    }
    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("smpchunks.command.chunk.tnt")) {
            if (args.length == 1) {
                Chunk chunk = player.getLocation().getChunk();
                if (chunkStorage.isProtected(chunk)) {
                    message.send(player, "&cChunk already owned by&f Server");
                } else if (chunkStorage.isClaimed(chunk)) {
                    if (chunkStorage.isOwner(player, chunk)) {
                        if (chunkStorage.TNTAllowed(chunk)) {
                            chunkStorage.getData(chunk).remove(NamespacedKey.minecraft("tnt"));
                            message.send(player, "&6You disabled tnt for this chunk");
                        } else {
                            chunkStorage.getData(chunk).set(NamespacedKey.minecraft("tnt"), PersistentDataType.STRING, "true");
                            message.send(player, "&6You enabled tnt for this chunk");
                        }
                    } else {
                        if (player.hasPermission("smpchunks.command.chunks.edit")) {
                            if (chunkStorage.TNTAllowed(chunk)) {
                                chunkStorage.getData(chunk).remove(NamespacedKey.minecraft("tnt"));
                                message.send(player, "&6You disabled tnt for this chunk");
                            } else {
                                chunkStorage.getData(chunk).set(NamespacedKey.minecraft("tnt"), PersistentDataType.STRING, "true");
                                message.send(player, "&6You enabled tnt for this chunk");
                            }
                        } else {
                            message.send(player, "&cChunk already owned by&f " + chunkStorage.getOwner(chunk).getName());
                        }
                    }
                } else {
                    message.send(player, "&cChunk is already unclaimed");
                }
            }
        }
    }
}