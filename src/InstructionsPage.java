import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * The InstructionsPage class represents the instruction window for the PixelPets game.
 * It displays instructions and provides options to return to the main menu or quit the game.
 */
public class InstructionsPage extends JFrame {

    public InstructionsPage() {
        // Frame setup
        setTitle("Instructions for your Pixel Pets");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLayout(new BorderLayout(15, 15));
        setLocationRelativeTo(null);

        // Gradient background
        JPanel gradientPanel = new GradientPanel();
        gradientPanel.setLayout(new BorderLayout(15, 15));
        add(gradientPanel);

        // Top panel with buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setPreferredSize(new Dimension(800, 50)); // Reduced the height of the top panel

        // Styled buttons
        JButton returnButton = createStyledButton("Return to Main Menu", 220, 40);
        JButton quitButton = createStyledButton("Quit Game", 150, 40);

        // Button alignment
        JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        leftButtonPanel.setOpaque(false);
        leftButtonPanel.add(returnButton);

        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        rightButtonPanel.setOpaque(false);
        rightButtonPanel.add(quitButton);

        topPanel.add(leftButtonPanel, BorderLayout.WEST);
        topPanel.add(rightButtonPanel, BorderLayout.EAST);

        // Center panel with instructions
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        //centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0)); // Added top padding

        JLabel instructionsLabel = new JLabel("<html><div style='padding: 0 10px; max-width: 700px;'>" +
        "<h1 style='color: #2C3E50; text-align: center; margin-bottom: 15px;'>Welcome to Pixel Pets!</h1>" +
        "<div style='font-size: 16px; color: #34495E; line-height: 1.8;'>" +
        "<p style='text-align: center;'>Take care of your Pixel Pet by meeting their needs and keeping them happy.</p>" +
        "<h2 style='color: #2980B9; margin-bottom: 10px;'>How to Play</h2>" +
        "<ul style='list-style-type: disc; padding-left: 20px;'>" +
        "<li><strong>Choose Your Pet:</strong> Dog, Cat, or Bird.</li>" +
        "<li><strong>Care for Your Pet:</strong>" +
        "<ul style='list-style-type: circle; padding-left: 20px;'>" +
        "<li>Feed them when hungry.</li>" +
        "<li>Play with them when lonely.</li>" +
        "<li>Let them rest when tired.</li>" +
        "</ul></li>" +
        "<li><strong>Monitor Stats:</strong> Health, Hunger, Mood, Energy, and Social.</li>" +
        "</ul>" +
        "<h2 style='color: #2980B9; margin-bottom: 10px;'>Tips</h2>" +
        "<ul style='list-style-type: disc; padding-left: 20px;'>" +
        "<li>Balance their needs to keep them happy and healthy.</li>" +
        "<li>Neglecting your pet can lower their stats—act fast!</li>" +
        "</ul>" +
        "<p style='text-align: center; color: #E74C3C; font-weight: bold;'>Keep your pet thriving and enjoy your time together!</p>" +
        "</div></html>");


        instructionsLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        centerPanel.add(instructionsLabel);

        // Add panels to gradient background
        gradientPanel.add(topPanel, BorderLayout.NORTH);
        gradientPanel.add(centerPanel, BorderLayout.CENTER);

        // Button actions
        returnButton.addActionListener(e -> {
            dispose();
            MainMenu.getInstance().setVisible(true);
        });
        quitButton.addActionListener(e -> System.exit(0));
    }

    /**
     * Creates a styled button with custom design and hover effect.
     *
     * @param text   the button label
     * @param width  the button width
     * @param height the button height
     * @return a styled JButton
     */
    private JButton createStyledButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBorder(new LineBorder(new Color(100, 100, 255), 3));
        button.setBackground(Color.WHITE);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(width, height));

        // Hover effect
        button.addChangeListener(e -> {
            if (button.getModel().isRollover()) {
                button.setBackground(new Color(200, 200, 255));
            } else {
                button.setBackground(Color.WHITE);
            }
        });

        return button;
    }

    /**
     * Custom JPanel to render a gradient background.
     */
    private static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gradient = new GradientPaint(0, 0, new Color(173, 216, 230),
                    0, getHeight(), new Color(224, 255, 255));
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * Main method to launch the instructions page.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InstructionsPage instructionsPage = new InstructionsPage();
            instructionsPage.setVisible(true);
        });
    }
}