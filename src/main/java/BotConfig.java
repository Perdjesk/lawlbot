import java.util.List;

public class BotConfig {

    private String server;
    private List<String> channels;
    private int port;
    private String name;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
