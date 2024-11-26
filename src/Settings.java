import java.io.*;
import java.util.Properties;

/**
 * A class that manages game settings through a properties file.
 * This class handles the storage and retrieval of various game settings including
 * play time restrictions and total play time tracking.
 * Settings are persisted in a 'settings.properties' file.
 *
 * The class provides functionality to:
 * - Load settings from a file
 * - Save settings to a file
 * - Get and set play time restrictions
 * - Get and set total play time
 *
 * If the settings file doesn't exist, default values will be used:
 * - Play time restriction: Integer.MAX_VALUE (unlimited)
 * - Total play time: 0
 *
 */
public class Settings {
    private static final String SETTINGS_FILE = "settings.properties";
    private static final String PLAY_TIME_RESTRICTION_KEY = "playTimeRestriction";
    private static final String TOTAL_PLAY_TIME_KEY = "totalPlayTime";
    private final Properties properties;

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

    /**
     * Saves the current settings to the settings file.
     * This method writes all properties to the predefined settings file location.
     * If an I/O error occurs during the saving process, the stack trace will be printed.
     * 
     * @throws IOException if an error occurs while writing to the settings file
     */
    private void saveSettings() {
        try (OutputStream output = new FileOutputStream(SETTINGS_FILE)) {
            properties.store(output, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
