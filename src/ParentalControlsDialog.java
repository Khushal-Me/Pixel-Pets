import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;


/**
 * A dialog window that provides parental control functionalities for the virtual pet game.
 * This dialog requires password authentication and allows parents to manage various game settings and statistics.
 *
 * Features include:
 * - Setting playtime restrictions
 * - Viewing and resetting player statistics
 * - Reviving deceased pets
 * - Navigation controls to return to main menu or quit game
 *
 * The dialog implements security through a password check ("CS2212A") before allowing access to parental controls.
 * All changes made through this dialog directly affect the main game state through the MainMenu reference.
 *
 * @author Jonathan, Ramje
 * @version 1.0
 * @see MainMenu
 * @see JDialog
 */
public class ParentalControlsDialog extends JDialog {
    private JTextField playTimeField;
    private JButton saveButton;
    private JButton revivePetButton;
    private JButton resetStatsButton;
    private JLabel playTimeLabel;
    private JLabel totalPlayTimeLabel;
    private JLabel statisticsTitleLabel;
    private JLabel setPlaytimeTitleLabel;
    private final MainMenu mainMenu;

    /**
     * A dialog window that provides parental control features for the game.
     * This dialog requires a password for access and includes functionality for:
     * - Viewing player statistics
     * - Setting playtime restrictions
     * - Resetting player statistics
     * - Reviving pets
     * - Navigation controls to return to main menu or quit the game
     *
     * The dialog is modal, meaning it blocks input to other windows while open.
     *
     * @param mainMenu The main menu instance that created this dialog
     */
    public ParentalControlsDialog(MainMenu mainMenu) {
        super(mainMenu, "Parental Controls", true);
        this.mainMenu = mainMenu;

        // Set up dialog
        setLayout(new BorderLayout());
        setSize(800, 600);
        setLocationRelativeTo(mainMenu);

        // Password prompt
        String password = JOptionPane.showInputDialog(this, "Enter parental password:", "Parental Controls", JOptionPane.WARNING_MESSAGE);
        if (password == null || !password.equals("CS2212A")) {
            JOptionPane.showMessageDialog(this, "Incorrect password. Access denied.", "Access Denied", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // Top panel with navigation buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(800, 80));
        topPanel.setBackground(Color.WHITE);

        JButton returnButton = createTopButton("Return to Main Menu");
        returnButton.addActionListener(e -> {
            dispose(); // Fully close the dialog
            SwingUtilities.invokeLater(() -> {
                mainMenu.setVisible(true); // Ensure MainMenu is shown
                mainMenu.toFront(); // Bring MainMenu to the front
                mainMenu.requestFocusInWindow(); // Ensure MainMenu gets focus
            });
        });

        JButton quitButton = createTopButton("Quit Game");
        quitButton.addActionListener(e -> System.exit(0)); // Exit the application

        JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        leftButtonPanel.setBackground(Color.WHITE);
        leftButtonPanel.add(returnButton);

        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        rightButtonPanel.setBackground(Color.WHITE);
        rightButtonPanel.add(quitButton);

        topPanel.add(leftButtonPanel, BorderLayout.WEST);
        topPanel.add(rightButtonPanel, BorderLayout.EAST);

        // Main content panel
        JPanel contentPanel = new JPanel(null); // Use null layout for precise positioning
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // "View Player Statistics" section
        statisticsTitleLabel = new JLabel("View Player Statistics");
        statisticsTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statisticsTitleLabel.setBounds(50, 100, 300, 30); // Position

        totalPlayTimeLabel = new JLabel("Total Playtime: " + mainMenu.getTotalPlayTime() + " minutes");
        totalPlayTimeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        totalPlayTimeLabel.setBounds(50, 150, 300, 30); // Position

        // "Set Playtime Restriction" section
        setPlaytimeTitleLabel = new JLabel("Set Playtime Statistics");
        setPlaytimeTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        setPlaytimeTitleLabel.setBounds(450, 100, 300, 30); // Position

        playTimeLabel = new JLabel("Set Playtime Restriction (in minutes):");
        playTimeField = new JTextField(5);
        playTimeLabel.setBounds(450, 150, 250, 30); // Position
        playTimeField.setBounds(700, 150, 50, 30); // Position

        // "Save Settings" button
        saveButton = createStyledButton("Save Settings");
        saveButton.setBounds(515, 200, 120, 35); // Resize and position under "Set Playtime Restriction"
        saveButton.addActionListener(e -> saveSettings());

       
        // "Reset Statistics" button
        resetStatsButton = createStyledButton("Reset Statistics");
        resetStatsButton.setBounds(50, 400, 150, 40); // Move up from the bottom
        resetStatsButton.addActionListener(e -> resetPlayerStatistics());

        // "Revive Pet" button
        revivePetButton = createStyledButton("Revive a Pet");
        revivePetButton.setBounds(580, 400, 150, 40); // Positioned in the bottom-right corner
        revivePetButton.addActionListener(e -> revivePet());

        // Add components to contentPanel
        contentPanel.add(statisticsTitleLabel);
        contentPanel.add(totalPlayTimeLabel);
        contentPanel.add(setPlaytimeTitleLabel);
        contentPanel.add(playTimeLabel);
        contentPanel.add(playTimeField);
        contentPanel.add(saveButton);
        contentPanel.add(resetStatsButton);
        contentPanel.add(revivePetButton);

        // Add panels to the dialog
        add(topPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Creates and configures a styled button with consistent appearance and hover effects.
     *
     * @param text The text to display on the button
     * @return A configured JButton with:
     *         - Arial font, size 20
     *         - Black border (3px)
     *         - White background
     *         - Fixed size of 220x40 pixels
     *         - Hover effect (light gray background when mouse enters)
     */
    private JButton createTopButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setBorder(new LineBorder(Color.BLACK, 3));
        button.setBackground(Color.WHITE);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(220, 40));

        // Rollover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(220, 220, 220)); // Light gray on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(Color.WHITE); // Reset to white when not hovered
            }
        });

        return button;
    }

    /**
     * Creates a styled JButton with consistent visual properties and hover effects.
     * 
     * The button is styled with:
     * - Arial font, size 16
     * - Black border with 3px width
     * - White background
     * - Fixed size of 150x40 pixels
     * - Hover effect changing background to light gray
     *
     * @param text The text to display on the button
     * @return A styled JButton instance with the specified text and styling
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBorder(new LineBorder(Color.BLACK, 3));
        button.setBackground(Color.WHITE);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 40));

        // Rollover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(220, 220, 220)); // Light gray on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(Color.WHITE); // Reset to white when not hovered
            }
        });

        return button;
    }

    /**
     * Saves the parental control settings by updating the playtime restriction.
     * Attempts to parse the user input from the playtime field and set it as the new restriction.
     * Displays a success message if the settings are saved successfully.
     * If the input is not a valid number, shows an error message to the user.
     * 
     * @throws NumberFormatException if the text in playTimeField cannot be parsed as an integer
     */
    private void saveSettings() {
        try {
            int restriction = Integer.parseInt(playTimeField.getText());
            mainMenu.setPlayTimeRestriction(restriction);
            JOptionPane.showMessageDialog(this, "Settings saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for playtime restriction.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Resets all player statistics to their initial values.
     * This method:
     * - Sets the total play time back to 0 minutes
     * - Updates the display label to show 0 minutes
     * - Shows a confirmation message to the user
     */
    private void resetPlayerStatistics() {
     
        int currentPlayTime = mainMenu.getTotalPlayTime();
        mainMenu.incrementTotalPlayTime(-currentPlayTime); // Subtract the current playtime to set it to 0
    
        
        totalPlayTimeLabel.setText("Total Playtime: 0 minutes");
    
        
        JOptionPane.showMessageDialog(this, "Player statistics have been reset.", "Statistics Reset", JOptionPane.INFORMATION_MESSAGE);
    }
    

    /**
     * Revives a pet from a specified save slot.
     * This method prompts the user to enter a save slot number (1-3),
     * loads the pet from the selected slot, restores its vital statistics
     * to optimal levels, and saves the revived pet back to the same slot.
     * 
     * The revival process includes:
     * - Setting health to 100%
     * - Resetting hunger to 0
     * - Setting social level to 100%
     * - Resetting sleep need to 0
     * 
     * If an invalid slot number is entered or if the pet cannot be loaded,
     * appropriate error messages are displayed to the user.
     */
    private void revivePet() {
        String slot = JOptionPane.showInputDialog(
            this,
            "Enter the save slot to revive the pet (1, 2, or 3):",
            "Revive Pet",
            JOptionPane.PLAIN_MESSAGE
        );
    
        if (slot != null) {
            if (!slot.matches("[123]")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid slot number (1, 2, or 3).", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            }
    
            Pet pet = Pet.load(slot);
            if (pet != null) {
                // Revive the pet and update its stats
                pet.revive();
                pet.setHealth(100);
                pet.setHunger(0);
                pet.setSocial(100);
                pet.setSleep(0);
    
                // Save the updated pet back to the selected slot
                pet.save(slot);
    
                JOptionPane.showMessageDialog(this, "Pet revived successfully!", "Revive Pet", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to load pet from the selected slot.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}