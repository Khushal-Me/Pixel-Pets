import java.io.*;
import java.util.Properties;

public class Settings {
    private static final String SETTINGS_FILE = "settings.properties";
    private static final String PLAY_TIME_RESTRICTION_KEY = "playTimeRestriction";
    private static final String TOTAL_PLAY_TIME_KEY = "totalPlayTime";
    private Properties properties;

    public Settings() {
        properties = new Properties();
        loadSettings();
    }

    public int getPlayTimeRestriction() {
        return Integer.parseInt(properties.getProperty(PLAY_TIME_RESTRICTION_KEY, String.valueOf(Integer.MAX_VALUE)));
    }

    public void setPlayTimeRestriction(int restriction) {
        properties.setProperty(PLAY_TIME_RESTRICTION_KEY, String.valueOf(restriction));
        saveSettings();
    }

    public int getTotalPlayTime() {
        return Integer.parseInt(properties.getProperty(TOTAL_PLAY_TIME_KEY, "0"));
    }

    public void setTotalPlayTime(int totalPlayTime) {
        properties.setProperty(TOTAL_PLAY_TIME_KEY, String.valueOf(totalPlayTime));
        saveSettings();
    }

    private void loadSettings() {
        try (InputStream input = new FileInputStream(SETTINGS_FILE)) {
            properties.load(input);
        } catch (IOException ex) {
            // Settings file does not exist or cannot be read
            // Defaults will be used
        }
    }

    private void saveSettings() {
        try (OutputStream output = new FileOutputStream(SETTINGS_FILE)) {
            properties.store(output, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
