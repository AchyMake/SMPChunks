package net.achymake.smpchunks.version;

import net.achymake.smpchunks.SMPChunks;
import net.achymake.smpchunks.files.Message;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {
    private final SMPChunks smpChunks;
    private final int resourceId;
    public UpdateChecker(SMPChunks smpChunks, int resourceId) {
        this.smpChunks = smpChunks;
        this.resourceId = resourceId;
    }
    private final Message message = SMPChunks.getMessage();
    public void getVersion(Consumer<String> consumer) {
        smpChunks.getServer().getScheduler().runTaskAsynchronously(smpChunks, () -> {
            try {
                InputStream inputStream = (new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId)).openStream();
                Scanner scanner = new Scanner(inputStream);
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                    scanner.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                message.sendLog(e.getMessage());
            }
        });
    }
    public void getUpdate() {
        if (smpChunks.getConfig().getBoolean("notify-update.enable")) {
            (new UpdateChecker(smpChunks, resourceId)).getVersion((latest) -> {
                if (smpChunks.getDescription().getVersion().equals(latest)) {
                    message.sendLog("You are using the latest version");
                } else {
                    message.sendLog("New Update: " + latest);
                    message.sendLog("Current Version: " + smpChunks.getDescription().getVersion());
                }
            });
        }
    }
    public void sendMessage(Player player) {
        if (smpChunks.getConfig().getBoolean("notify-update.enable")) {
            (new UpdateChecker(smpChunks, resourceId)).getVersion((latest) -> {
                if (!smpChunks.getDescription().getVersion().equalsIgnoreCase(latest)) {
                    message.send(player, "&6" + smpChunks.getName() + " Update:&f "+ latest);
                    message.send(player, "&6Current Version: &f" + smpChunks.getDescription().getVersion());
                }
            });
        }
    }
}