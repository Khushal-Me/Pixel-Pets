import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MainMenuTest {

    private MainMenu mainMenu;
    private MusicPlayer mockMusicPlayer;
    private Settings mockSettings;

    @BeforeEach
    public void setUp() {
        mockMusicPlayer = mock(MusicPlayer.class);
        mockSettings = mock(Settings.class);
        mainMenu = MainMenu.getInstance();
    }

    @Test
    public void testSingletonInstance() {
        MainMenu instance1 = MainMenu.getInstance();
        MainMenu instance2 = MainMenu.getInstance();
        assertSame(instance1, instance2, "MainMenu should be a singleton");
    }

    @Test
    public void testMusicPlaysOnStart() {
        verify(mockMusicPlayer, times(1)).playMusic("src/res/Alive.wav");
    }

    @Test
    public void testStartNewGame() {
        JButton startGameButton = getButtonByText(mainMenu, "Start New Game");
        assertNotNull(startGameButton, "Start New Game button should exist");

        startGameButton.doClick();
        verify(mockMusicPlayer, times(1)).changeMusic("src/res/Alive.wav");
        assertFalse(mainMenu.isVisible(), "Main menu should be hidden when starting a new game");
    }

    @Test
    public void testLoadGame() {
        JButton loadGameButton = getButtonByText(mainMenu, "Load Game");
        assertNotNull(loadGameButton, "Load Game button should exist");

        when(JOptionPane.showInputDialog(mainMenu, "Enter load slot (1, 2, or 3):")).thenReturn("1");
        when(Pet.load("1")).thenReturn(new Pet());

        loadGameButton.doClick();
        verify(mockMusicPlayer, times(1)).changeMusic("src/res/Alive.wav");
        assertFalse(mainMenu.isVisible(), "Main menu should be hidden when loading a game");
    }

    @Test
    public void testOpenInstructionsPage() {
        JButton instructionsButton = getButtonByText(mainMenu, "Instructions");
        assertNotNull(instructionsButton, "Instructions button should exist");

        instructionsButton.doClick();
        assertFalse(mainMenu.isVisible(), "Main menu should be hidden when opening instructions page");
    }

    @Test
    public void testOpenParentalControls() {
        JButton parentalControlsButton = getButtonByText(mainMenu, "Parental Controls");
        assertNotNull(parentalControlsButton, "Parental Controls button should exist");

        parentalControlsButton.doClick();
        verify(mockSettings, times(1)).openParentalControls();
    }

    @Test
    public void testQuitGame() {
        JButton quitButton = getButtonByText(mainMenu, "Quit Game");
        assertNotNull(quitButton, "Quit Game button should exist");

        quitButton.doClick();
        verify(mockMusicPlayer, times(1)).stopMusic();
    }

    @Test
    public void testVolumeSlider() {
        JSlider volumeSlider = mainMenu.getVolumeSlider();
        assertNotNull(volumeSlider, "Volume slider should exist");

        volumeSlider.setValue(50);
        verify(mockMusicPlayer, times(1)).setVolume(0.5f);
    }

    private JButton getButtonByText(MainMenu mainMenu, String text) {
        for (Component component : mainMenu.getContentPane().getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(text)) {
                    return button;
                }
            }
        }
        return null;
    }
}