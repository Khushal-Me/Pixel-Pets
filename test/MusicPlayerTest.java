import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import java.io.File;
import javax.sound.sampled.*;
import static org.junit.jupiter.api.Assertions.*;

public class MusicPlayerTest {

    private MusicPlayer musicPlayer;

    @BeforeEach
    public void setUp() {
        musicPlayer = MusicPlayer.getInstance();
    }

    @AfterEach
    public void tearDown() {
        musicPlayer.stopMusic();
    }

    @Test
    public void testPlayMusicWithValidFile() {
        String validFilePath = "path/to/valid/music/file.wav";
        File file = new File(validFilePath);
        if (!file.exists()) {
            fail("Test file does not exist: " + validFilePath);
        }
        musicPlayer.playMusic(validFilePath);
        assertTrue(musicPlayer.clip.isRunning(), "Music should be playing.");
    }

    @Test
    public void testPlayMusicWithInvalidFile() {
        String invalidFilePath = "path/to/invalid/music/file.wav";
        musicPlayer.playMusic(invalidFilePath);
        assertFalse(musicPlayer.clip.isRunning(), "Music should not be playing.");
    }

    @Test
    public void testPlayMusicWithUnsupportedFile() {
        String unsupportedFilePath = "path/to/unsupported/music/file.txt";
        musicPlayer.playMusic(unsupportedFilePath);
        assertFalse(musicPlayer.clip.isRunning(), "Music should not be playing.");
    }


}