import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
/**
 * A singleton class that manages audio playback functionality for the application.
 * This class provides methods to play, stop, and control background music.
 * It implements the singleton pattern to ensure only one instance of the music player exists.
 * 
 * The class supports:
 * - Playing audio files with continuous looping
 * - Stopping currently playing music
 * - Changing between different music tracks
 * - Volume control
 * 
 * Usage example:
 * {@code
 * MusicPlayer player = MusicPlayer.getInstance();
 * player.playMusic("path/to/music.wav");
 * player.setVolume(0.5f);
 * }
 * 
 * @author Ramje, Khushal
 * @version 1.0
 */
public class MusicPlayer {

    private Clip clip;
    private static MusicPlayer instance;
    private FloatControl volumeControl;

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
        stopMusic(); // Stop any currently playing music
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
            // Get volume control
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.err.println("An error occurred while playing music: " + ex.getMessage());
        }
    }
    public void setVolume(float volume) {
        if (volumeControl != null) {
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float volumeDb = min + (max - min) * volume;
            volumeControl.setValue(volumeDb);
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
