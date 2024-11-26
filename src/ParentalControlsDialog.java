import java.awt.*;
import javax.swing.*;

/**
 * The {@code ParentalControlsDialog} class represents a dialog window that allows parents
 * to set playtime restrictions for the game and view the total playtime.
 * It prompts the user for a parental password before granting access to the settings.
 */
 
/**
 * Creates a new {@code ParentalControlsDialog}.
 *
 * @param mainMenu the main menu frame from which this dialog is launched
 */
 
/**
 * Saves the playtime restriction settings entered by the user.
 * Displays a success message upon successful save, or an error message if the input is invalid.
 */
public class ParentalControlsDialog extends JDialog {
    private JTextField playTimeField;
    private JButton saveButton;
    private JLabel playTimeLabel;
    private JLabel totalPlayTimeLabel;
    private final MainMenu mainMenu;

    public ParentalControlsDialog(MainMenu mainMenu) {
        super(mainMenu, "Parental Controls", true);
        this.mainMenu = mainMenu;

        setLayout(new GridLayout(5, 1, 10, 10));
        setSize(400, 300);
        setLocationRelativeTo(mainMenu);

        // Password prompt
        String password = JOptionPane.showInputDialog(this, "Enter parental password:", "Parental Controls", JOptionPane.WARNING_MESSAGE);
        if (password == null || !password.equals("CS2212A")) {
            JOptionPane.showMessageDialog(this, "Incorrect password. Access denied.", "Access Denied", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // Playtime restriction input
        playTimeLabel = new JLabel("Set playtime restriction (in minutes):");
        playTimeField = new JTextField();
        playTimeField.setText(String.valueOf(mainMenu.getPlayTimeRestriction()));

        // Display total playtime
        totalPlayTimeLabel = new JLabel("Total playtime: " + mainMenu.getTotalPlayTime() + " minutes");

        saveButton = new JButton("Save Settings");

        add(totalPlayTimeLabel);
        add(playTimeLabel);
        add(playTimeField);
        add(saveButton);

        saveButton.addActionListener(e -> saveSettings());

        setVisible(true);
    }

    private void saveSettings() {
        try {
            int restriction = Integer.parseInt(playTimeField.getText());
            mainMenu.setPlayTimeRestriction(restriction);
            JOptionPane.showMessageDialog(this, "Settings saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for playtime restriction.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}
