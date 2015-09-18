import java.io.File;
import java.io.IOException;

import org.pircbotx.exception.IrcException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;


public class DiceBotRunner {

    public static void main(String[] args) throws IOException, IrcException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        BotConfig config = mapper.readValue(new File("etc/dice-bot.yaml"), BotConfig.class);
        
        DiceBot bot = new DiceBot(config);
        bot.startBot();
    }

}
