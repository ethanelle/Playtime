package us.pcout.playtime;

import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlaytimeExecutor implements CommandExecutor {
    private final Playtime playtime;

    public PlaytimeExecutor(Playtime plugin) {
        playtime = plugin;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("playtime") && args.length == 0) {
            if (!(commandSender instanceof Player)) {
                return false;
            } else {
                Player player = (Player) commandSender;
                if (player.hasPermission("playtime.playtime")) {
                    player.sendMessage("You have played " + buildMessage(player.getStatistic(Statistic.PLAY_ONE_MINUTE)));
                }
                return true;
            }
        } else if (command.getName().equalsIgnoreCase("playtime") && args.length == 1) {
            if (commandSender instanceof Player && !commandSender.hasPermission("playtime.playtime.others")) {
                return true;
            } else {
                Player target = playtime.getServer().getPlayer(args[0]);
                if (target == null) {
                    Integer targetTime = playtime.getPlayer(args[0]);
                    if (targetTime == null) {
                        commandSender.sendMessage("Could not find the requested player.");
                    } else {
                        commandSender.sendMessage(args[0] + "has played " + buildMessage(targetTime));
                    }
                }
            }
            return true;
        } else if (command.getName().equalsIgnoreCase("playtimetop")) {
            commandSender.sendMessage("Sorry, this command is not complete yet!");
            return true;
        }
        return false;
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
