package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigurationManager {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationManager.class);

    private static final String CONFIG_FILE = "config.properties";

    private static ConfigurationManager instance;

    private Properties properties;

    private ConfigurationManager() {
        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource(CONFIG_FILE)).getPath();
        path = preprocessPathString(path);
        try (FileInputStream inputStream = new FileInputStream(path)) {
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static String preprocessPathString(String path) {
        String updated = path;
        Pattern pattern = Pattern.compile("[%][A-F0-9]{2}");
        Matcher matcher = pattern.matcher(path);
        while (matcher.find()) {
            String replacing = path.substring(matcher.start(), matcher.end());
            int code = Integer.parseInt(replacing.substring(1), 16);
            char ch = (char) code;
            updated = updated.replace(replacing, String.valueOf(ch));
        }
        return updated;
    }

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    public String get(String key) {
        return properties.getProperty(key);
    }
}
