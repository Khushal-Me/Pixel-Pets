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
 * @author Jonathan, Ramje, Khushal
 * @version 1.0
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


    /**
     * A custom JPanel class that creates a gradient background.
     * The panel displays a vertical gradient from light blue at the top
     * to light cyan at the bottom, creating a soft, pastel appearance.
     * 
     * The gradient is redrawn every time the component is painted,
     * ensuring proper resizing behavior.
     * 
     * This class extends JPanel and overrides the paintComponent method
     * to implement the gradient painting functionality.
     */
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

        // Window listener
        addWindowListener(new WindowAdapter() {
            /**
             * Invoked when the user attempts to close the window.
             * Stops the background music when the window is closing.
             *
             * @param e the WindowEvent object containing details about the window closing event
             */
            @Override
            public void windowClosing(WindowEvent e) {
                musicPlayer.stopMusic();
            }
        });
    }

    /**
     * Returns the singleton instance of MainMenu.
     * If the instance doesn't exist, creates a new one using the private constructor.
     * This ensures only one instance of MainMenu exists throughout the application lifecycle.
     *
     * @return The singleton instance of MainMenu
     */
    public static MainMenu getInstance() {
        if (instance == null) {
            instance = new MainMenu();
        }
        return instance;
    }

    /**
     * Creates a custom styled JButton with predefined visual properties.
     * The button features a white background, blue border, and hover effects.
     *
     * @param text The text to display on the button
     * @param x The x-coordinate position of the button
     * @param y The y-coordinate position of the button
     * @param width The width of the button in pixels
     * @param height The height of the button in pixels
     * @return A styled JButton with the specified properties
     */
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

    /**
     * Initializes and starts a new game session.
     * This method:
     * - Changes the background music to gameplay music
     * - Creates new instances of the pet model, view and controller components
     * - Hides the main menu
     * - Sets up window listeners to handle game window closure
     * - Displays the game window
     * 
     * When the game window is closed, the main menu becomes visible again.
     */
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


    /**
     * Opens the instructions page window and manages its visibility.
     * When opened, hides the main menu window and displays the instructions page.
     * When the instructions page is closed, the main menu window becomes visible again.
     */

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


    /**
     * Handles the game loading functionality from a saved slot.
     * Prompts the user to select a save slot (1-3) and loads the corresponding saved game.
     * If successful, initializes the game view and controller with the loaded pet data.
     * Changes background music to gameplay music and sets up auto-save functionality.
     * If loading fails, displays an error message.
     * 
     * The method performs the following steps:
     * 1. Prompts for save slot selection
     * 2. Validates user input
     * 3. Attempts to load pet data
     * 4. Initializes game components if load successful
     * 5. Sets up window listeners for proper menu/game navigation
     * 
     * @throws IllegalStateException Implicitly if the music file cannot be loaded
     */

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


    /**
     * Opens the Parental Controls dialog window.
     * Creates a new instance of ParentalControlsDialog and makes it visible to the user.
     * This method allows access to parental control settings and restrictions.
     */

    private void openParentalControls() {
        ParentalControlsDialog parentalControlsDialog = new ParentalControlsDialog(this);
        parentalControlsDialog.setVisible(true);
    }

    /**
     * The main entry point of the application.
     * Initializes and displays the main menu of the game using Swing.
     * Uses the system's default look and feel for UI components.
     * Implements the Singleton pattern to ensure only one instance of MainMenu exists.
     *
     * @param args Command line arguments
     */

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