package us.pcout.playtime;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Playtime extends JavaPlugin {
    private final String storagePath = getDataFolder() + "/userdata.json";

    @Override
    public void onEnable() {
        checkStorage();
        getServer().getPluginManager().registerEvents(new PlaytimeListener(), this);
        this.getCommand("playtime").setExecutor(new PlaytimeExecutor());
    }

    @Override
    public void onDisable() {
        // save any online players to file.

    }

    private void checkStorage() {
        File pluginDirectory = getDataFolder();
        if (!pluginDirectory.exists()) {
            pluginDirectory.mkdirs();
        }

        File userdataFile = new File(storagePath);
        if (!userdataFile.exists()) {
            try {
                FileWriter writer = new FileWriter(userdataFile.getAbsoluteFile());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
