import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.pircbotx.Configuration;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.PircBotX;
import org.pircbotx.UtilSSLSocketFactory;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiceBot extends ListenerAdapter<PircBotX> {

    private static final Logger log = LoggerFactory.getLogger(DiceBot.class);
    private BotConfig config;
    private int nbDiceRoll;
    private PircBotX bot;
    private Integer nbLines;
    private File lawlsFile;

    public DiceBot(BotConfig config) {
        this.config = config;

        lawlsFile = new File("etc/lawls.txt");

        try {
            LineNumberReader lnr = new LineNumberReader(new FileReader(lawlsFile));
            lnr.skip(Long.MAX_VALUE);
            nbLines = lnr.getLineNumber();
            lnr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws InterruptedException {
        String msg = event.getMessage();
        if (msg.startsWith("!ping " + config.getName())) {
            event.respond("pong");
        }

        if (msg.startsWith("!status " + config.getName())) {
            event.respond("dice rolled (" + nbDiceRoll + ") ");
        }

        if (msg.startsWith("!lawl")) {
            try (Stream<String> lines = Files.lines(lawlsFile.toPath())) {
                String url = lines.skip(new Random().nextInt(nbLines)).findFirst().get();
                event.respond(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Pattern p = Pattern.compile("^!roll ([0-9]{1,2})d([0-9]{1,4})$");
        Matcher m = p.matcher(msg);
        if (m.find()) {
            nbDiceRoll++;
            Integer nbDice = Integer.valueOf(m.group(1));
            Integer diceMaxValue = Integer.valueOf(m.group(2));
            List<Integer> result = new Random().ints(nbDice, 1, diceMaxValue + 1).boxed().collect(Collectors.toList());
            event.respond(result.toString());
        }
    }

    @Override
    public void onConnect(ConnectEvent<PircBotX> event) {
        // event.getBot().sendIRC().identify("");
    }

    public void startBot() throws IOException, IrcException {
        // @formatter:off
		Builder<PircBotX> builder = new Configuration.Builder<PircBotX>().setName(config.getName())
				.setServerHostname(config.getServer())
				.setServerPort(config.getPort())
				.setSocketFactory(new UtilSSLSocketFactory().trustAllCertificates())
				.setRealName(config.getName())
				.setLogin(config.getName())
				.setAutoReconnect(true)
				.addListener(this);
		// @formatter:on

        config.getChannels().stream().forEach((channel) -> builder.addAutoJoinChannel(channel));

        bot = new PircBotX(builder.buildConfiguration());
        bot.startBot();
    }
}