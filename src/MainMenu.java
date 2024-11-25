import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class MainMenu extends JFrame {

    private static MainMenu instance; // Singleton instance of MainMenu
    private final MusicPlayer musicPlayer;
    private boolean isMusicPlaying = false;  // Track if music is playing
    private final Settings settings;

    // Private constructor to prevent multiple instances
    public MainMenu() {
        // Frame setup
        setTitle("PixelPets");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLayout(null); // Use absolute positioning
        setLocationRelativeTo(null);

        settings = new Settings();

        // Initialize music player
        musicPlayer = MusicPlayer.getInstance();

        // Ensure music plays only once when the main menu is first opened
        if (!isMusicPlaying) {
            String musicFilePath = "src/res/Alive.wav"; // Replace with actual file path
            musicPlayer.playMusic(musicFilePath);
            isMusicPlaying = true;  // Music starts playing once
        }

        // Title label
        JLabel titleLabel = new JLabel("PixelPets");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(250, 50, 300, 60); // Position for title
        add(titleLabel);

        // Buttons
        JButton startGameButton = createStyledButton("Start New Game", 250, 150, 300, 50);
        JButton loadGameButton = createStyledButton("Load Game", 250, 220, 300, 50);
        JButton instructionsButton = createStyledButton("Instructions", 250, 290, 300, 50);
        JButton parentalControlsButton = createStyledButton("Parental Controls", 250, 360, 300, 50);
        JButton quitButton = createStyledButton("Quit Game", 600, 20, 150, 40);

        // Add buttons to the frame
        add(startGameButton);
        add(loadGameButton);
        add(instructionsButton);
        add(parentalControlsButton);
        add(quitButton);

        // Action listeners
        startGameButton.addActionListener(e -> startNewGame());
        loadGameButton.addActionListener(e -> loadGame());
        instructionsButton.addActionListener(e -> openInstructionsPage());
        parentalControlsButton.addActionListener(e -> openParentalControls());
        quitButton.addActionListener(e -> System.exit(0)); // Quit the application

        // Minimize the main menu when a new game starts
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Ensure music stops when quitting
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
        button.setBorder(new LineBorder(Color.BLACK, 3)); // Bolder border
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
        MusicPlayer.getInstance().changeMusic("res/AdhesiveWombat - Night Shade  NO COPYRIGHT 8-bit Music.wav");
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
        String slot = JOptionPane.showInputDialog(this, "Enter load slot (1, 2, or 3):");
        if (slot != null && !slot.isEmpty()) {
            Pet petModel = Pet.load(slot);
            if (petModel != null) {
                petModel.startAutoSave(slot, 5, TimeUnit.MINUTES);
                PetView gameView = new PetView();
                PetController controller = new PetController(petModel, gameView, this, false); // Pass false for loading a game
    
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
            } else {
                JOptionPane.showMessageDialog(this, "Failed to load game from slot " + slot, "Load Error", JOptionPane.ERROR_MESSAGE);
            }
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