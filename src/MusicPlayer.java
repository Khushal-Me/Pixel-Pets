import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 * The {@code MusicPlayer} class provides functionality to play, stop, and change background music in the application.
 * It follows the singleton design pattern to ensure only one instance manages the music playback.
 */
public class MusicPlayer {

    private Clip clip;
    private static MusicPlayer instance;

    /**
     * Private constructor to prevent direct instantiation.
     * Use {@link #getInstance()} to obtain the singleton instance.
     */
    private MusicPlayer() {
        // Private constructor to prevent instantiation
    }

    /**
     * Returns the singleton instance of {@code MusicPlayer}.
     *
     * @return the single instance of {@code MusicPlayer}
     */
    public static MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }

    /**
     * Plays the music from the specified file path.
     * The music will loop continuously until {@link #stopMusic()} is called.
     *
     * @param filePath the path to the music file to be played
     */
    public void playMusic(String filePath) {
        System.out.println("Attempting to play music: " + filePath);
        try {
            // Open an audio input stream
            File musicPath = new File(filePath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);

                // Get a sound clip resource
                clip = AudioSystem.getClip();

                // Open audio clip and load samples from the audio input stream
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music indefinitely
            } else {
                System.out.println("Cannot find the music file.");
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.err.println("An error occurred while playing music: " + ex.getMessage());
        }
    }

    /**
     * Stops the currently playing music, if any.
     * The music will stop immediately, and resources will be released.
     */
    public void stopMusic() {
        System.out.println("Stopping music.");
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    /**
     * Changes the currently playing music to the track specified by the given file path.
     * This method stops any music that is currently playing before starting the new track.
     *
     * @param filePath the path to the new music file to be played
     */
    public void changeMusic(String filePath) {
        stopMusic();
        playMusic(filePath);
    }
}
