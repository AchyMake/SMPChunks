package net.achymake.smpchunks;

import net.achymake.smpchunks.commands.chunk.ChunkCommand;
import net.achymake.smpchunks.commands.chunks.ChunksCommand;
import net.achymake.smpchunks.files.ChunkStorage;
import net.achymake.smpchunks.files.Message;
import net.achymake.smpchunks.listeners.bed.PlayerBedEnter;
import net.achymake.smpchunks.listeners.block.BlockBreak;
import net.achymake.smpchunks.listeners.block.BlockFertilize;
import net.achymake.smpchunks.listeners.block.BlockPlace;
import net.achymake.smpchunks.listeners.bucket.PlayerBucketEmpty;
import net.achymake.smpchunks.listeners.bucket.PlayerBucketEntity;
import net.achymake.smpchunks.listeners.bucket.PlayerBucketFill;
import net.achymake.smpchunks.listeners.cauldron.CauldronLevelChange;
import net.achymake.smpchunks.listeners.connection.NotifyUpdate;
import net.achymake.smpchunks.listeners.damage.*;
import net.achymake.smpchunks.listeners.entity.*;
import net.achymake.smpchunks.listeners.interact.InteractBlock;
import net.achymake.smpchunks.listeners.interact.InteractEntity;
import net.achymake.smpchunks.listeners.interact.InteractPhysical;
import net.achymake.smpchunks.listeners.leash.PlayerLeashEntity;
import net.achymake.smpchunks.listeners.mount.EntityMount;
import net.achymake.smpchunks.listeners.shear.PlayerShearEntity;
import net.achymake.smpchunks.listeners.spawn.CreatureSpawn;
import net.achymake.smpchunks.version.UpdateChecker;
import net.achymake.smpcore.SMPCore;
import net.achymake.smpchunks.api.PlaceholderProvider;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class SMPChunks extends JavaPlugin {
    private static SMPChunks instance;
    private static Message message;
    private static ChunkStorage chunkStorage;
    private final File configFile = new File(getDataFolder(), "config.yml");
    @Override
    public void onEnable() {
        instance = this;
        message = new Message(this);
        if (getServer().getPluginManager().getPlugin("SMPCore") != null) {
            message.sendLog("Hooked to 'SMPCore'");
        } else {
            getServer().getPluginManager().disablePlugin(this);
            message.sendLog("You have to install 'SMPCore'");
        }
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderProvider().register();
            message.sendLog("hooked to PlaceholderAPI");
        } else {
            message.sendLog("You have to install 'PlaceholderAPI'");
            getServer().getPluginManager().disablePlugin(this);
        }
        chunkStorage = new ChunkStorage(this);
        reload();
        getCommand("chunk").setExecutor(new ChunkCommand());
        getCommand("chunks").setExecutor(new ChunksCommand());
        new PlayerBedEnter(this);
        new BlockBreak(this);
        new BlockFertilize(this);
        new BlockPlace(this);
        new PlayerBucketEmpty(this);
        new PlayerBucketEntity(this);
        new PlayerBucketFill(this);
        new CauldronLevelChange(this);
        new NotifyUpdate(this);
        new DamageEntity(this);
        new DamageEntityWithArrow(this);
        new DamageEntityWithSnowball(this);
        new DamageEntityWithSpectralArrow(this);
        new DamageEntityWithThrownPotion(this);
        new DamageEntityWithTrident(this);
        new EntityBlockForm(this);
        new EntityChangeBlock(this);
        new EntityEnterLoveMode(this);
        new EntityExplode(this);
        new EntityTarget(this);
        new MinecartTNTExplode(this);
        new PrimedTNTExplode(this);
        new InteractBlock(this);
        new InteractEntity(this);
        new InteractPhysical(this);
        new PlayerLeashEntity(this);
        new EntityMount(this);
        new PlayerShearEntity(this);
        new CreatureSpawn(this);
        message.sendLog("Enabled " + getName()+" " + getDescription().getVersion());
        new UpdateChecker(this,110010).getUpdate();
    }
    @Override
    public void onDisable() {
        if (!chunkStorage.getChunkEditors().isEmpty()) {
            chunkStorage.getChunkEditors().clear();
        }
        message.sendLog("Disabled " + getName() + " " + getDescription().getVersion());
    }
    public static ChunkStorage getChunkStorage() {
        return chunkStorage;
    }
    public static Message getMessage() {
        return message;
    }
    public void reload() {
        if (configFile.exists()) {
            try {
                getConfig().load(configFile);
                getConfig().options().copyDefaults(true);
                saveConfig();
            } catch (IOException | InvalidConfigurationException e) {
                message.sendLog(e.getMessage());
            }
        } else {
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
        for (OfflinePlayer offlinePlayer : getServer().getOfflinePlayers()) {
            File playerFiles = new File(SMPCore.getInstance().getDataFolder(), "userdata/" + offlinePlayer.getUniqueId() + ".yml");
            if (playerFiles.exists()) {
                FileConfiguration playerConfigs = YamlConfiguration.loadConfiguration(playerFiles);
                try {
                    playerConfigs.load(playerFiles);
                    playerConfigs.options().copyDefaults(true);
                    playerConfigs.save(playerFiles);
                } catch (IOException | InvalidConfigurationException e) {
                    message.sendLog(e.getMessage());
                }
            }
        }
    }
    public static SMPChunks getInstance() {
        return instance;
    }
}