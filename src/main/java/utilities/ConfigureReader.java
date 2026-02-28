package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigureReader {

    Properties prop;

    public ConfigureReader() {
        try {
            FileInputStream fis = new FileInputStream(
                    System.getProperty("user.dir") + "/src/test/resources/config.properties");
            prop = new Properties();
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return prop.getProperty("url");
    }

    public String getBrowser() {
        return prop.getProperty("browser");
    }

    public String getUsername() {
        return prop.getProperty("username");
    }

    public String getPassword() {
        return prop.getProperty("password");
    }
}