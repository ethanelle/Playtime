package us.pcout.playtime;

public class PlaytimePlayer {
    public String name;
    public String uuid;
    public int time;

    public PlaytimePlayer() {
        time = 0;
    }

    public PlaytimePlayer(String n, String u, int t) {
        name = n;
        uuid = u;
        time = t;
    }
}
