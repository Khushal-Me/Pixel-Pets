// MusicPlayer.java
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class MusicPlayer {
    private Clip clip;
    private static MusicPlayer instance;
    
    private MusicPlayer() {
        // Private constructor to prevent instantiation
    }

    public static MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }

    public void playMusic(String filePath) {
        try {
            // Open an audio input stream.
            File musicPath = new File(filePath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);

                // Get a sound clip resource.
                clip = AudioSystem.getClip();

                // Open audio clip and load samples from the audio input stream.
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music indefinitely

            } else {
                System.out.println("Cannot find the music file.");
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}
