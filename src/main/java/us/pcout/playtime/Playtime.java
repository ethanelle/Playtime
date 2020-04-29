package us.pcout.playtime;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Playtime extends JavaPlugin {
    private final String storagePath = getDataFolder() + "/userdata.json";

    /**
     * checkStorage() ensures plugin folder and JSON file exist
     * register the event listener
     * register the command executor
     */
    @Override
    public void onEnable() {
        checkStorage();
        getServer().getPluginManager().registerEvents(new PlaytimeListener(), this);
        this.getCommand("playtime").setExecutor(new PlaytimeExecutor());
    }

    /**
     * In the event the server is shutdown with players online, save their stats in the JSON file before detaching
     */
    @Override
    public void onDisable() {
        getServer().getOnlinePlayers().forEach(this::savePlayer);
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

    @SuppressWarnings("unchecked")
    public void savePlayer(Player player) {
        JSONObject target = new JSONObject();
        target.put("uuid", player.getUniqueId().toString());
        target.put("lastname", player.getDisplayName());
        target.put("time", player.getStatistic(Statistic.PLAY_ONE_MINUTE));
        writePlayer(target);
    }

    @SuppressWarnings("unchecked")
    private void writePlayer(JSONObject target) {
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader reader = new FileReader(storagePath);
            JSONArray players = (JSONArray) jsonParser.parse(reader);
            for (int i = 0; i < players.size(); i++) {
                JSONObject player = (JSONObject) players.get(i);
                if (player.get("uuid") == target.get("uuid")) {
                    players.remove(i);
                    break;
                }
            }
            players.add(target);

            FileWriter writer = new FileWriter(storagePath);
            writer.write(target.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
