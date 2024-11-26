import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * The InstructionsPage class represents a JFrame window that displays the instructions for the PixelPets game.
 * It includes buttons to return to the main menu or quit the game, and displays detailed game instructions.
 */
 
/**
 * Constructs a new InstructionsPage window, initializing the frame, panels, buttons, and their action listeners.
 */
 
/**
 * Creates a styled JButton with the specified text, width, and height.
 * The button has custom font, border, background color, and a rollover effect.
 *
 * @param text   the text to display on the button
 * @param width  the preferred width of the button
 * @param height the preferred height of the button
 * @return a styled JButton with the specified properties
 */
 
/**
 * The main method to run the InstructionsPage independently.
 * It invokes the creation of the InstructionsPage using the Event Dispatch Thread.
 *
 * @param args command-line arguments (not used)
 */
public class InstructionsPage extends JFrame {

    public InstructionsPage() {
        // Frame setup
        setTitle("Instructions - PixelPets");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Only close this window
        setSize(800, 600);
        setResizable(false);
        setLayout(new BorderLayout()); // Use BorderLayout for structure
        setLocationRelativeTo(null);

        // Top panel for buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(800, 80));
        topPanel.setBackground(Color.WHITE);

        // Buttons for top panel
        JButton returnButton = createStyledButton("Return to Main Menu", 220, 40); // Increased width to fit text
        JButton quitButton = createStyledButton("Quit Game", 150, 40);

        // Left-aligned "Return to Main Menu"
        JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        leftButtonPanel.setBackground(Color.WHITE);
        leftButtonPanel.add(returnButton);

        // Right-aligned "Quit Game"
        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        rightButtonPanel.setBackground(Color.WHITE);
        rightButtonPanel.add(quitButton);

        // Add left and right button panels to top panel
        topPanel.add(leftButtonPanel, BorderLayout.WEST);
        topPanel.add(rightButtonPanel, BorderLayout.EAST);

        // Center panel for instructions
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        JLabel instructionsLabel = new JLabel("<html><div style='text-align: left; margin-left: 15px;'>" +
        "<h1 style='text-align: center;'>Welcome to Pixel Pets!</h1>" +
        "<h2 style='text-align: center;'>Game Instructions:</h2>" +
        "<ol>" +
        "<li><b>Choose your pet:</b> Dog, Cat, Bird.</li>" +
        "<li><b>Take care of your pet:</b>" +
        "<ul>" +
        "<li>&nbsp;&nbsp;&nbsp;Feed when hungry.</li>" +  // Using &nbsp; for spaces
        "<li>&nbsp;&nbsp;&nbsp;Play when lonely.</li>" +
        "<li>&nbsp;&nbsp;&nbsp;Let it sleep when tired.</li>" +
        "</ul>" +
        "</li>" +
        "<li><b>Monitor your pet's stats:</b>" +
        "<ul>" +
        "<li>&nbsp;&nbsp;&nbsp;Health</li>" +
        "<li>&nbsp;&nbsp;&nbsp;Hunger</li>" +
        "<li>&nbsp;&nbsp;&nbsp;Social</li>" +
        "<li>&nbsp;&nbsp;&nbsp;Sleep</li>" +
        "<li>&nbsp;&nbsp;&nbsp;Mood</li>" +
        "</ul>" +
        "</li>" +
        "<li>&nbsp;&nbsp;&nbsp;Pay attention to alerts and warnings.</li>" +
        "<li>&nbsp;&nbsp;&nbsp;Different pets have unique characteristics!</li>" +
        "</ol>" +
        "<p>&nbsp;&nbsp;&nbsp;Keep your pet healthy and happy to prevent their inevitable death!</p>" +
        "</div></html>");



        instructionsLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        centerPanel.add(instructionsLabel);

        // Add components to the frame
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // Action listeners for buttons
        returnButton.addActionListener(e -> {
            dispose(); // Close the instructions page
            MainMenu.getInstance().setVisible(true); // Return to the main menu
        });

        quitButton.addActionListener(e -> System.exit(0)); // Quit the application
    }

    private JButton createStyledButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 20)); // Match font size with MainMenu
        button.setBorder(new LineBorder(Color.BLACK, 3)); // Bolder border
        button.setBackground(Color.WHITE); // White background
        button.setOpaque(true); // Ensures background color is applied
        button.setFocusPainted(false); // Removes focus outline
        button.setFocusable(false); // Disables focus to prevent blue outline
        button.setPreferredSize(new Dimension(width, height)); // Set fixed size

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InstructionsPage instructionsPage = new InstructionsPage();
            instructionsPage.setVisible(true);
        });
    }
}