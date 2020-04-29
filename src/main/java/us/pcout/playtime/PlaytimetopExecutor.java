package us.pcout.playtime;

import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlaytimetopExecutor implements CommandExecutor {
    private final Playtime playtime;

    public PlaytimetopExecutor(Playtime plugin) {
        playtime = plugin;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("playtimetop")) {
            PlaytimePlayer[] top10 = playtime.getTopTen();

            top10 = checkOnlinePlayers(top10);

            commandSender.sendMessage(playtime.prefix + " Top 10 players by time spent online:");
            for (int i = 0; i < top10.length; i++) {
                if (top10[i].time == 0) {
                    break;
                }
                commandSender.sendMessage(ChatColor.GREEN + Integer.toString(i + 1) + ". " + ChatColor.WHITE + top10[i].name + ": " + buildMessage(top10[i].time));
            }
        }
        return true;
    }

    private PlaytimePlayer[] checkOnlinePlayers(PlaytimePlayer[] top10) {
        for (Player p : playtime.getServer().getOnlinePlayers()) {
            if (p.getStatistic(Statistic.PLAY_ONE_MINUTE) > top10[9].time) {
                PlaytimePlayer newPlayer = new PlaytimePlayer(p.getDisplayName(), p.getUniqueId().toString(), p.getStatistic(Statistic.PLAY_ONE_MINUTE));
                for (int i = 0; i < 10; i++) {
                    if (top10[i].time < newPlayer.time) {
                        if (top10[i].uuid.equals(newPlayer.uuid)) {
                            top10[i] = newPlayer;
                            break;
                        } else {
                            PlaytimePlayer temp = top10[i];
                            top10[i] = newPlayer;
                            newPlayer = temp;
                        }
                    }
                }
            }
        }
        return top10;
    }

    private String buildMessage(int time) {
        time /= 1200;
        int days = time / 1440;
        time %= 1440;
        int hours = time / 60;
        time %= 60;
        int minutes = time;

        String msg = "";
        if (days > 0) {
            msg += days + " days ";
        }
        if (hours > 0) {
            msg += hours + " hours ";
        }
        msg += minutes + " minutes.";
        return msg;
    }
}
