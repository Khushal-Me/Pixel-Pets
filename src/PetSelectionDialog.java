// PetSelectionDialog.java
import java.awt.*;
import javax.swing.*;


public class PetSelectionDialog extends JDialog {
    private String selectedPet;

    public PetSelectionDialog(JFrame parent) {
        super(parent, "Select Your Pet", true);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Choose Your Pet");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel petPanel = new JPanel();
        petPanel.setLayout(new FlowLayout());

        // Create buttons with images
        JButton dogButton = createPetButton("Dog", "res/dog.jpeg");
        JButton catButton = createPetButton("Cat", "res/cat.jpeg");
        JButton birdButton = createPetButton("Bird", "res/bird.jpeg");

        // Add buttons to the panel
        petPanel.add(dogButton);
        petPanel.add(catButton);
        petPanel.add(birdButton);

        add(titleLabel, BorderLayout.NORTH);
        add(petPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(parent);
    }

    private JButton createPetButton(String petName, String imagePath) {
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        // Scale the image
        Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);
        JButton button = new JButton(petName, icon);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        
        button.addActionListener(e -> {
            selectedPet = petName;
            dispose();
        });

        return button;
    }

    public String getSelectedPet() {
        return selectedPet;
    }
}
