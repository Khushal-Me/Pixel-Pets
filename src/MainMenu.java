import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Represents the main menu of the PixelPets game application.
 * This class implements a Singleton pattern to ensure only one instance of the main menu exists.
 * The menu provides options to start a new game, load existing games, view instructions,
 * access parental controls, and quit the application.
 * 
 * Features:
 * - Background music management through MusicPlayer
 * - Game save/load functionality 
 * - Parental controls and play time restrictions
 * - Custom styled UI components
 * 
 * The main menu serves as the central navigation hub for the game, managing transitions
 * between different game states and maintaining persistent settings.
 *
 * @see MusicPlayer
 * @see Settings
 * @see Pet
 * @see PetView
 * @see PetController
 */
public class MainMenu extends JFrame {

    private static MainMenu instance; // Singleton instance of MainMenu
    private final MusicPlayer musicPlayer;
    private boolean isMusicPlaying = false;  // Track if music is playing
    private final Settings settings;
    private JSlider volumeSlider;


    private static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            // Soft pastel gradient from light blue to light cyan
            GradientPaint gradient = new GradientPaint(
                0, 0, new Color(173, 216, 230), // Light Blue at top
                0, getHeight(), new Color(224, 255, 255) // Very Light Cyan at bottom
            );
            
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }


    // Private constructor to prevent multiple instances
    public MainMenu() {
        // Frame setup
        setTitle("PixelPets");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        // Replace default content pane with gradient panel
        GradientPanel gradientPanel = new GradientPanel();
        gradientPanel.setLayout(null); // Use absolute positioning
        setContentPane(gradientPanel);

        settings = new Settings();

        // Initialize music player
        musicPlayer = MusicPlayer.getInstance();

        // Ensure music plays only once when the main menu is first opened
        if (!isMusicPlaying) {
            String musicFilePath = "src/res/Alive.wav";
            musicPlayer.playMusic(musicFilePath);
            isMusicPlaying = true;
        }

        // Title label with improved styling
        JLabel titleLabel = new JLabel("PixelPets");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 56));
        titleLabel.setForeground(new Color(0, 51, 102)); // Deep blue color
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(200, 50, 400, 80);
        gradientPanel.add(titleLabel);

        // Buttons with refined styling
        JButton startGameButton = createStyledButton("Start New Game", 250, 150, 300, 50);
        JButton loadGameButton = createStyledButton("Load Game", 250, 220, 300, 50);
        JButton instructionsButton = createStyledButton("Instructions", 250, 290, 300, 50);
        JButton parentalControlsButton = createStyledButton("Parental Controls", 250, 360, 300, 50);
        JButton quitButton = createStyledButton("Quit Game", 600, 20, 150, 40);

        // Add buttons to the gradient panel
        gradientPanel.add(startGameButton);
        gradientPanel.add(loadGameButton);
        gradientPanel.add(instructionsButton);
        gradientPanel.add(parentalControlsButton);
        gradientPanel.add(quitButton);

        // Action listeners (same as before)
        startGameButton.addActionListener(e -> startNewGame());
        loadGameButton.addActionListener(e -> loadGame());
        instructionsButton.addActionListener(e -> openInstructionsPage());
        parentalControlsButton.addActionListener(e -> openParentalControls());
        quitButton.addActionListener(e -> System.exit(0));

        // Volume slider with improved styling
        volumeSlider = new JSlider(0, 100, 100);
        volumeSlider.setBounds(250, 430, 300, 50);
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setOpaque(false); // Make slider transparent over gradient
        gradientPanel.add(volumeSlider);

        volumeSlider.addChangeListener(e -> {
            int value = volumeSlider.getValue();
            float volume = value / 100f;
            musicPlayer.setVolume(volume);
        });

        // Window listener (same as before)
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                musicPlayer.stopMusic();
            }
        });
    }

    // Singleton pattern to ensure only one instance of MainMenu is created
    public static MainMenu getInstance() {
        if (instance == null) {
            instance = new MainMenu();
        }
        return instance;
    }

    private JButton createStyledButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setBorder(new LineBorder(Color.BLUE, 3)); // Bolder border
        button.setBackground(Color.WHITE); // White background
        button.setOpaque(true); // Ensures background color is applied
        button.setFocusPainted(false); // Removes focus outline
        button.setFocusable(false); // Ensures no blue outline when focused
        button.setBounds(x, y, width, height); // Set size and position
        button.setHorizontalAlignment(SwingConstants.CENTER); // Center-align text

        // Add rollover effect
        button.addChangeListener(e -> {
            if (button.getModel().isRollover()) {
                button.setBackground(new Color(220, 220, 220)); // Light gray on hover
            } else {
                button.setBackground(Color.WHITE); // White when not hovered
            }
        });

        return button;
    }

    private void startNewGame() {
        // Change the music to normal gameplay music
        MusicPlayer.getInstance().changeMusic("src/res/Alive.wav");
        // Create model, view, and controller
        Pet petModel = new Pet();
        PetView gameView = new PetView();
        PetController controller = new PetController(petModel, gameView, this, true); // Pass true for a new game
    
        // Hide the main menu
        setVisible(false);
    
        // Add listener to re-show the main menu when the game window closes
        gameView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(true);
                gameView.dispose();
            }
        });
    
        // Show the game window
        gameView.setVisible(true);
    }


    private void openInstructionsPage() {
        setVisible(false); // Hide the main menu
        InstructionsPage instructionsPage = new InstructionsPage();
        instructionsPage.setVisible(true); // Show instructions page
        instructionsPage.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                setVisible(true); // Re-show the main menu when instructions page closes
            }
        });
    }


    private void loadGame() {
        String slot;
        while (true) {
            slot = JOptionPane.showInputDialog(this, "Enter load slot (1, 2, or 3):");
            if (slot == null) {
                // User clicked cancel or closed the dialog
                return;
            }
            if (!slot.matches("[123]")) {
                JOptionPane.showMessageDialog(this, "Please enter 1, 2, or 3.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            } else {
                break;
            }
        }
    
        Pet petModel = Pet.load(slot);
        if (petModel != null) {
            // Change the music to normal gameplay music
            MusicPlayer.getInstance().changeMusic("src/res/Alive.wav");
            petModel.startAutoSave(slot, 5, TimeUnit.MINUTES);
            PetView gameView = new PetView();
            // Hide the main menu
            setVisible(false);
    
            // Add listener to re-show the main menu when the game window closes
            gameView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    setVisible(true);
                    gameView.dispose();
                }
            });
    
            // Show the game window
            gameView.setVisible(true);
            
            
            // Display success message
            JOptionPane.showMessageDialog(gameView, "Game loaded successfully.");
            PetController controller = new PetController(petModel, gameView, this, false); // Pass false for loading a game
        } else {
            JOptionPane.showMessageDialog(this, "Save file doesn't exist.", "Load Error", JOptionPane.ERROR_MESSAGE);
        }
            
    }


    private void openParentalControls() {
        ParentalControlsDialog parentalControlsDialog = new ParentalControlsDialog(this);
        parentalControlsDialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                System.err.println("Failed to set system look and feel: " + e.getMessage());
            }

            // Launch the main menu
            MainMenu mainMenu = MainMenu.getInstance(); // Get the single instance of MainMenu
            mainMenu.setVisible(true);
        });
    }
    public int getPlayTimeRestriction() {
        return settings.getPlayTimeRestriction();
    }
    
    public void setPlayTimeRestriction(int restriction) {
        settings.setPlayTimeRestriction(restriction);
    }
    
    public int getTotalPlayTime() {
        return settings.getTotalPlayTime();
    }
    
    public void incrementTotalPlayTime(int minutes) {
        int newTotal = getTotalPlayTime() + minutes;
        settings.setTotalPlayTime(newTotal);
    }
}