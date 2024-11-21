import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class PetSelectionDialog extends JDialog {
    private String selectedPet = null; // Holds the selected pet's name
    private JButton selectedButton = null; // Keeps track of the currently selected button

    public PetSelectionDialog(JFrame parent) {
        super(parent, "Select Your Pet", true);
        setLayout(new BorderLayout());

        // Top panel with "Return to Main Menu" and "Quit Game" buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(800, 80));
        topPanel.setBackground(Color.WHITE);

        // "Return to Main Menu" button
        JButton returnButton = createTopButton("Return to Main Menu", 220, 40);
        returnButton.addActionListener(e -> {
            deselectPet(); // Deselect any selected pet
            dispose(); // Close the dialog and return to main menu
            MainMenu.getInstance().setVisible(true); // Go back to the main menu
        });

        // "Quit Game" button
        JButton quitButton = createTopButton("Quit Game", 150, 40);
        quitButton.addActionListener(e -> System.exit(0)); // Exit the application

        // Add buttons to the top panel
        JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        leftButtonPanel.setBackground(Color.WHITE);
        leftButtonPanel.add(returnButton);

        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        rightButtonPanel.setBackground(Color.WHITE);
        rightButtonPanel.add(quitButton);

        topPanel.add(leftButtonPanel, BorderLayout.WEST);
        topPanel.add(rightButtonPanel, BorderLayout.EAST);

        // Title label
        JLabel titleLabel = new JLabel("Choose Your Pet");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        // Pet selection panel
        JPanel petPanel = new JPanel();
        petPanel.setLayout(new GridLayout(1, 3, 10, 10)); // 1 row, 3 columns

        // Create buttons with images
        JButton dogButton = createPetButton("Dog", "res/dog.jpeg");
        JButton catButton = createPetButton("Cat", "res/cat.jpeg");
        JButton birdButton = createPetButton("Bird", "res/bird.jpeg");

        // Add pet buttons to the panel
        petPanel.add(dogButton);
        petPanel.add(catButton);
        petPanel.add(birdButton);

        // "Select Pet" button
        JButton selectPetButton = createStyledButton("Select Pet", 150, 40);
        selectPetButton.addActionListener(e -> {
            if (selectedPet != null) {
                // Proceed to start the game
                JOptionPane.showMessageDialog(this, "Starting game with: " + selectedPet);
                dispose(); // Close the dialog immediately

                // Start the game with the selected pet
                startGame(selectedPet);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a pet first.", "No Pet Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Bottom panel for the "Select Pet" button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(selectPetButton);

        // Add components to the dialog
        add(topPanel, BorderLayout.NORTH);
        add(titleLabel, BorderLayout.CENTER);
        add(petPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Dialog settings
        setSize(800, 600);
        setLocationRelativeTo(parent);
    }

    private JButton createPetButton(String petName, String imagePath) {
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image scaledImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);

        JButton button = new JButton(icon);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setBorder(new LineBorder(Color.BLACK, 3)); // Styled border
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setOpaque(true);

        button.addActionListener(e -> {
            if (selectedButton != null) {
                selectedButton.setBackground(Color.WHITE); // Reset previous selection
            }
            selectedPet = petName;
            selectedButton = button;
            button.setBackground(new Color(220, 220, 220)); // Highlight the selected pet
        });

        return button;
    }

    private JButton createTopButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setBorder(new LineBorder(Color.BLACK, 3));
        button.setBackground(Color.WHITE);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(width, height));

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

    private JButton createStyledButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setBorder(new LineBorder(Color.BLACK, 3)); // Styled border
        button.setBackground(Color.WHITE);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(width, height));

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

    public String getSelectedPet() {
        return selectedPet;
    }

    private void deselectPet() {
        selectedPet = null;  // Clear the selection
        if (selectedButton != null) {
            selectedButton.setBackground(Color.WHITE);  // Reset the selected button's background color
            selectedButton = null;  // Clear the reference to the button
        }
    }

    private void startGame(String selectedPet) {
        // Proceed to start the game with the selected pet
        Pet petModel = new Pet();
        petModel.setPetName(selectedPet); // Set the pet's name

        PetView gameView = new PetView();
        PetController controller = new PetController(petModel, gameView, (MainMenu) getParent()); // Pass pet model and view to the controller

        // Show the game view
        gameView.setVisible(true);

        gameView.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Re-open the main menu when the game window closes
                MainMenu.getInstance().setVisible(true);
                gameView.dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PetSelectionDialog dialog = new PetSelectionDialog(null);
            dialog.setVisible(true); // Make sure the dialog is shown and the game starts properly
        });
    }
}