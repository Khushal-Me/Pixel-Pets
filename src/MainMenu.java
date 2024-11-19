/*Main Menu and  */

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class MainMenu extends JFrame {
    private final JButton startGameButton;
    private final JButton loadGameButton;
    private final JButton instructionsButton;
    private final JButton exitButton;

    public MainMenu() {
        // Basic frame setup
        setTitle("Pixel Pets");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        
        // Create main panel with a vertical box layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Create title label
        JLabel titleLabel = new JLabel("Pixel Pets");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create buttons
        startGameButton = createStyledButton("Start New Game");
        loadGameButton = createStyledButton("Load Game");
        instructionsButton = createStyledButton("Tutorial");
        exitButton = createStyledButton("Exit");

        // Add components to panel with spacing
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainPanel.add(startGameButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(loadGameButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(instructionsButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(exitButton);

        // Add action listeners
        startGameButton.addActionListener(e -> startNewGame());
        //loadGameButton.addActionListener(e -> loadGame());
        instructionsButton.addActionListener(e -> showInstructions());
        exitButton.addActionListener(e -> System.exit(0));

        // Add panel to frame
        add(mainPanel);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        return button;
    }

    private void startNewGame() {
        // Create model, view, and controller
        Pet petModel = new Pet();
        PetView gameView = new PetView();
        PetController controller = new PetController(petModel, gameView);

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

    private void showInstructions() {
        JOptionPane.showMessageDialog(this,
            """
            Welcome to Pixel Pets!

            Game Instructions:
            1. Choose your pet (Dog, Cat, Bird)

            2. Take care of your pet by:
               - Feeding when hungry
               - Playing when lonely
               - Letting it sleep when tired

            3. Monitor your pet's stats:
               - Health
               - Hunger
               - Social
               - Sleep
               - Mood

            4. Pay attention to alerts and warnings
            5. Different Pets have unique characteristics!

            Keep your pet healthy and happy, if you want to prevent their inevitable death!
            """,
            "Game Instructions",
            JOptionPane.INFORMATION_MESSAGE);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                System.err.println("Failed to set system look and feel: " + e.getMessage());
            }
    
            // Load saved pet or create a new one
            Pet pet = Pet.load();
    
            // Start autosaving
            pet.startAutosave();
    
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
        });
    }
}
