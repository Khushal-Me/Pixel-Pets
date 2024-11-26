import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * The {@code PetView} class provides the graphical user interface (GUI) for interacting 
 * with a virtual pet in the Pixel Pets game. It displays the pet's current status 
 * and allows users to perform various actions to care for the pet.
 *
 * Features:
 * - Displays pet attributes such as health, hunger, mood, social status, and sleep.
 * - Allows users to select a personality for the pet.
 * - Provides controls for feeding, playing, and putting the pet to sleep.
 * - Includes inventory management for items and their usage.
 * - Integrates with the {@code PetController} for handling user interactions.
 * - Updates pet images dynamically based on its state.
 *
 * Usage:
 * Instantiate this class to create the pet interaction view. Ensure that a 
 * {@code PetController} is set to manage interactions and update the pet's status.
 *
 * Dependencies:
 * - {@code PetController}: Handles actions and updates the view based on user interactions.
 * - {@code PersonalityStrategy}: Provides pet behavior based on selected personality.
 * - {@code PetSelectionDialog}: Facilitates pet selection during initialization.
 *
 * Part of the Pixel Pets game and follows the Model-View-Controller (MVC) design pattern.
 */
public class PetView extends JFrame {

  private final JLabel healthLabel;
  private final JLabel hungerLabel;
  private final JLabel socialLabel;
  private final JLabel sleepLabel;
  private final JLabel moodLabel;
  private final JLabel personalityLabel;
  private final JLabel lastInteractedLabel;
  private final JButton feedButton;
  private final JButton playButton;
  private final JButton sleepButton;
  private final JButton confirmButton;
  private final JButton getPreferredActionButton;
  private final JButton performPreferredActionButton;
  private final JComboBox<String> personalityComboBox;
  private final JTextArea messageArea;
  private PetController controller;
  private boolean hungerAlertShown = false;
  private boolean sleepAlertShown = false;
  private boolean socialAlertShown = false;
  private final boolean isDialogOpen = false;
  private final JLabel petImageLabel;
  private final JButton backButton; 
  private final DefaultListModel<String> inventoryListModel;
  private final JList<String> inventoryList;
  private final JButton useItemButton;
  private final JButton vetButton;
  private JButton saveButton;


  /**
   * Constructor for the view.
   */
  public PetView() {
    setTitle("Pixel Pets");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 600);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    personalityLabel = new JLabel("Choose Personality:");
    personalityComboBox = new JComboBox<>(new String[] {"Happy", "Grumpy", "Normal"});
    confirmButton = new JButton("Confirm");

    panel.add(personalityLabel);
    panel.add(personalityComboBox);
    panel.add(confirmButton);

    // Other attributes initially hidden
    healthLabel = new JLabel("Health:");
    hungerLabel = new JLabel("Hunger:");
    socialLabel = new JLabel("Social:");
    sleepLabel = new JLabel("Sleep:");
    moodLabel = new JLabel("Mood:");
    lastInteractedLabel = new JLabel("Last Interaction: ");
    feedButton = new JButton("Feed");
    playButton = new JButton("Play");
    sleepButton = new JButton("Sleep");
    getPreferredActionButton = new JButton("Get Preferred Action");
    performPreferredActionButton = new JButton("Perform Preferred Action");
    backButton = new JButton("Back to Main Menu");
    saveButton = new JButton("Save Game");
    add(saveButton, BorderLayout.SOUTH);

   // Inventory Panel
   inventoryListModel = new DefaultListModel<>();
   inventoryList = new JList<>(inventoryListModel);
   JScrollPane inventoryScrollPane = new JScrollPane(inventoryList);
   inventoryScrollPane.setPreferredSize(new Dimension(200, 400));

   useItemButton = new JButton("Use Item");

   JPanel inventoryPanel = new JPanel();
   inventoryPanel.setLayout(new BorderLayout());
   inventoryPanel.add(new JLabel("Inventory"), BorderLayout.NORTH);
   inventoryPanel.add(inventoryScrollPane, BorderLayout.CENTER);
   inventoryPanel.add(useItemButton, BorderLayout.SOUTH);
   inventoryPanel.setPreferredSize(new Dimension(300, 500)); // Adjust width and height as needed
   inventoryList.setPreferredSize(new Dimension(300, 500));


   vetButton = new JButton("Take Pet to Vet");

    // Add vetButton to buttonsPanel
    // Add inventory panel to main panel
    panel.add(healthLabel);
    panel.add(hungerLabel);
    panel.add(socialLabel);
    panel.add(sleepLabel);
    panel.add(moodLabel);
    panel.add(personalityLabel);
    panel.add(lastInteractedLabel);

    panel.add(feedButton);
    panel.add(playButton);
    panel.add(sleepButton);
    panel.add(vetButton);
    panel.add(backButton);
    panel.add(getPreferredActionButton);
    panel.add(performPreferredActionButton);
    panel.add(saveButton);

    panel.add(inventoryPanel);

    // Initially hide other attributes and buttons
    toggleAttributesVisibility(false);
    toggleButtonsVisibility(false);
    togglePersonalitySelection(true);

    // Add a message area to display messages
    messageArea = new JTextArea(20, 30);
    messageArea.setEditable(false); // Make the messageArea read-only
    messageArea.setLineWrap(true);
    messageArea.setWrapStyleWord(true);
    JScrollPane scrollPane = new JScrollPane(messageArea);
    panel.add(scrollPane);

    add(panel);

    petImageLabel = new JLabel();
    petImageLabel.setHorizontalAlignment(JLabel.CENTER);
    // Add the petImageLabel to the panel at the desired position
    panel.add(petImageLabel);
  }

/**
 * This method displays the personality selection dialog.
 */
public void displayPetSelectionDialog() {
  PetSelectionDialog dialog = new PetSelectionDialog(this);
  dialog.setVisible(true);

  String selectedPet = dialog.getSelectedPet();
  if (selectedPet == null) {
      System.exit(0); // User closed the dialog without selection
  } else {
      notifyControllerOfPersonalityChoice(selectedPet);
      confirmPersonalitySelection();
  }
}

public void updatePetImage(String petName, boolean isDead) {
  String imagePath;
  if (isDead) {
      imagePath = "/res/" + petName.toLowerCase() + "_dead.jpg";
  } else {
      imagePath = "/res/" + petName.toLowerCase() + ".jpeg";
  }
  java.net.URL imgURL = getClass().getResource(imagePath);
  if (imgURL != null) {
      ImageIcon originalIcon = new ImageIcon(imgURL);

      // Scale the image to desired size
      int desiredWidth = 300; // Adjust as needed
      int desiredHeight = 300; // Adjust as needed

      Image scaledImage = originalIcon.getImage().getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
      ImageIcon scaledIcon = new ImageIcon(scaledImage);

      petImageLabel.setIcon(scaledIcon);
  } else {
      System.err.println("Couldn't find file: " + imagePath);
  }
}

  /**
   * This method sets the controller for the view.
   *
   * @param controller the controller to be set
   */
public void setController(PetController controller) {
        this.controller = controller;
  }

public void addBackButtonListener(ActionListener listener) {
    backButton.addActionListener(listener);
}

public void addSaveListener(ActionListener saveListener) {
    saveButton.addActionListener(saveListener);
}



  /**
   * This method notifies the controller of the personality choice.
   *
   * @param choice the index of the personality
   */
public void notifyControllerOfPersonalityChoice(String petName) {
    PersonalityStrategy selectedPersonality = mapNameToPersonality(petName);
    controller.handleSelectedPersonality(selectedPersonality, petName);
}

  /**
   * This method maps the index of the personality to the personality.
   *
   * @param choice the index of the personality
   * @return the personality
   */
private PersonalityStrategy mapNameToPersonality(String petName) {
    PersonalityStrategy selectedPersonality;
    selectedPersonality = switch (petName) {
        case "Dog" -> {
            messageArea.append("The Dog pet was selected.\n");
            yield new Dog();
        }
        case "Cat" -> {
            messageArea.append("The Cat pet was selected.\n");
            yield new Cat();
        }
        case "Bird" -> {
            messageArea.append("The Bird pet was selected.\n");
            yield new Bird();
        }
        default -> {
            messageArea.append("The Bird pet was selected.\n");
            yield new Bird();
        }
    };

    return selectedPersonality;
}

  /**
   * This method displays the personality selection dialog.
   */
  /**
   * This method appends a message to the message area.
   *
   * @param message the message to be appended
   * @return null
   */
  public TimerTask appendMessage(String message) {
    messageArea.append(message + "\n"); // Append the message to the messageArea
    return null;
  }

  /**
   * This method updates the health status label.
   *
   * @param health the health status to be displayed
   */
  public void updateHealth(int health) {
    healthLabel.setText("Health: " + health);
  }

  /**
   * This method updates the hunger label.
   *
   * @param hunger the hunger status to be displayed
   */
  public void updateHunger(int hunger) {
    hungerLabel.setText("Hunger: " + hunger);
  }

  /**
   * This method updates the social label.
   *
   * @param social the social status to be displayed
   */
  public void updateSocial(int social) {
    socialLabel.setText("Social: " + social);
  }

  /**
   * This method updates the sleep label.
   *
   * @param sleep the sleep status to be displayed
   */
  public void updateSleep(int sleep) {
    sleepLabel.setText("Sleep: " + sleep);
  }

  /**
   * This method updates the mood label.
   *
   * @param string the mood to be displayed
   */
  public void updateMood(String string) {
    moodLabel.setText("Mood: " + string);
  }

  /**
   * This method updates the personality label.
   *
   * @param personality the personality to be displayed
   */
  public void updatePersonality(String personality) {
    personalityLabel.setText("Personality: " + personality);
  }

  /**
   * This method toggles the visibility of the attributes.
   *
   * @param visible whether the attributes should be visible
   */
  public void toggleAttributesVisibility(boolean visible) {
    healthLabel.setVisible(visible);
    hungerLabel.setVisible(visible);
    socialLabel.setVisible(visible);
    sleepLabel.setVisible(visible);
    moodLabel.setVisible(visible);
    personalityLabel.setVisible(visible);
  }

  /**
   * This method toggles the visibility of the personality selection components.
   *
   * @param visible whether the personality selection components should be visible
   */
  public void togglePersonalitySelection(boolean visible) {
    personalityLabel.setVisible(visible);
    personalityComboBox.setVisible(visible);
    confirmButton.setVisible(visible);
  }

  /**
   * This method toggles the visibility of the buttons.
   *
   * @param visible whether the buttons should be visible
   */
  public void toggleButtonsVisibility(boolean visible) {
    feedButton.setVisible(visible);
    playButton.setVisible(visible);
    sleepButton.setVisible(visible);
    getPreferredActionButton.setVisible(visible);
    performPreferredActionButton.setVisible(visible);
  }

  /**
   * This method adds a feed listener to the feed button.
   *
   * @param feedListener the listener to be added
   */
  public void addFeedListener(ActionListener feedListener) {
    feedButton.addActionListener(feedListener);
  }

  /**
   * This method adds a play listener to the play button.
   *
   * @param playListener the listener to be added
   */
  public void addPlayListener(ActionListener playListener) {
    playButton.addActionListener(playListener);
  }

  /**
   * This method adds a sleep listener to the sleep button.
   *
   * @param sleepListener the listener to be added
   */
  public void addSleepListener(ActionListener sleepListener) {
    sleepButton.addActionListener(sleepListener);
  }

  /**
   * This method updates the lastInteractedLabel.
   *
   * @param timeInMillis the time in milliseconds
   */
  public void updateLastInteractedTime(long timeInMillis) {
    // Convert the time to a readable format if needed
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String formattedTime = sdf.format(new Date(timeInMillis));

    // Update the lastInteractedLabel in the view
    lastInteractedLabel.setText("Last Interaction: " + formattedTime);
  }

  /**
   * This method disables the personality selection components.
   */
  public void confirmPersonalitySelection() {
    personalityComboBox.setEnabled(false);
    confirmButton.setEnabled(false);
    togglePersonalitySelection(false);
    toggleAttributesVisibility(true);
    toggleButtonsVisibility(true);
  }

  /**
   * This method toggles the availability of the buttons.
   *
   * @param enabled whether the buttons should be enabled
   */
  public void toggleButtonsAvailability(boolean enabled) {
    feedButton.setEnabled(enabled);
    playButton.setEnabled(enabled);
    sleepButton.setEnabled(enabled);
    getPreferredActionButton.setEnabled(enabled);
    performPreferredActionButton.setEnabled(enabled);
  }

  /**
   * This method displays the health status message.
   *
   * @param message the message to be displayed
   */
  public void displayHealthStatusMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Pet Health Status",
        JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * This method adds a preferred action listener to the get preferred action button.
   *
   * @param listener the listener to be added
   */
  public void addPreferredActionListener(ActionListener listener) {
    getPreferredActionButton.addActionListener(listener);
  }

  /**
   * This method adds a perform preferred action listener to the perform preferred action button.
   *
   * @param listener the listener to be added
   */
  public void addPerformPreferredActionListener(ActionListener listener) {
    performPreferredActionButton.addActionListener(listener);
  }

  /**
   * This method displays the preferred action dialog.
   */
  public void displayPreferredActionDialog() {
    Action preferredAction = controller.getPreferredActionFromModel();
    JOptionPane.showMessageDialog(this, "Preferred Action: " + preferredAction, "Preferred Action",
        JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * This method displays the hunger alert.
   */
  public void displayHungerAlert() {
    if (!hungerAlertShown) {
      Object[] options = {"Feed", "Dismiss"};
      int choice = JOptionPane.showOptionDialog(
          this, // Use 'this' to tie the dialog to the game window
          "Your pet is hungry!",
          "Hunger Alert",
          JOptionPane.YES_NO_OPTION,
          JOptionPane.WARNING_MESSAGE,
          null,
          options,
          options[0]
      );

      if (choice == JOptionPane.YES_OPTION) {
        controller.handleFeedAction(); // Call the method in the controller to feed the pet
      }
      hungerAlertShown = true;
    }
  }

  /**
   * This method displays the social alert.
   */
  public void displaySocialAlert() {
    if (!socialAlertShown) {
      Object[] options = {"Play", "Dismiss"};
      int choice = JOptionPane.showOptionDialog(
          this, // Use 'this' to tie the dialog to the game window
          "Your pet is lonely!",
          "Social Alert",
          JOptionPane.YES_NO_OPTION,
          JOptionPane.WARNING_MESSAGE,
          null,
          options,
          options[0]
      );

      if (choice == JOptionPane.YES_OPTION) {
        controller.handlePlayAction(); // Call the method in the controller to play with the pet
      }
      socialAlertShown = true;
    }
  }

  /**
   * This method displays the sleep alert.
   */
  public void displaySleepAlert() {
    if (!sleepAlertShown) {
      Object[] options = {"Sleep", "Dismiss"};
      int choice = JOptionPane.showOptionDialog(
          this, // Parent the dialog to the game window,
          "Your pet is sleepy!",
          "Sleep Alert",
          JOptionPane.YES_NO_OPTION,
          JOptionPane.WARNING_MESSAGE,
          null,
          options,
          options[0]
      );

      if (choice == JOptionPane.YES_OPTION) {
        controller.handleSleepAction(); // Call the method in the controller to put the pet to sleep
      }
      sleepAlertShown = true;
    }
  }

  /**
   * This method resets the hunger alert.
   */
  public void resetHungerAlert() {
    hungerAlertShown = false;
  }

  /**
   * This method resets the sleep alert.
   */
  public void resetSleepAlert() {
    sleepAlertShown = false;
  }

  /**
   * This method resets the social alert.
   */
  public void resetSocialAlert() {
    socialAlertShown = false;
  }

  /**
   * This method resets the view.
   */
  public void reset() {
    messageArea.setText("");

    toggleAttributesVisibility(false);
    toggleButtonsVisibility(false);

    personalityLabel.setVisible(false);
    personalityComboBox.setVisible(false);
    confirmButton.setVisible(false);

    personalityComboBox.setSelectedIndex(0);
    personalityComboBox.setEnabled(true);
    confirmButton.setEnabled(true);

    togglePersonalitySelection(true);

    resetHungerAlert();
    resetSleepAlert();
    resetSocialAlert();

    displayPetSelectionDialog();
  }

  /**
   * This method displays the preferred action change message.
   */
  public void displayPreferredActionChangeMessage() {
    JOptionPane.showMessageDialog(this, "The pet's preferred action is changing.", "Action Change",
        JOptionPane.INFORMATION_MESSAGE);
  }
  public void setActionButtonsEnabled(boolean enabled) {
    feedButton.setEnabled(enabled);
    playButton.setEnabled(enabled);
    sleepButton.setEnabled(enabled);
    getPreferredActionButton.setEnabled(enabled);
    performPreferredActionButton.setEnabled(enabled);
}

  public class AlertUtils {
    private static boolean isDialogOpen = false;

    public static void displayAlert(String message, String title, Runnable action, boolean flag) {
        if (!flag && !isDialogOpen) {
            isDialogOpen = true; // Set the flag to avoid duplicates

            Object[] options = {"Act", "Dismiss"};
            int choice = JOptionPane.showOptionDialog(
                null,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]
            );

            if (choice == JOptionPane.YES_OPTION) {
                action.run();
            }

            isDialogOpen = false; // Reset after dialog closes
        }
    }
}
public void updateInventory(List<Item> items) {
        inventoryListModel.clear();
        for (Item item : items) {
            inventoryListModel.addElement(item.getName());
        }
    }

    public void addUseItemListener(ActionListener listener) {
        useItemButton.addActionListener(listener);
    }

    public Item getSelectedItem(List<Item> items) {
        int selectedIndex = inventoryList.getSelectedIndex();
        if (selectedIndex != -1) {
            return items.get(selectedIndex);
        } else {
            return null;
        }
    }
    public void addVetButtonListener(ActionListener listener) {
      vetButton.addActionListener(listener);
  }

  public void updateViewWithModel(Pet model) {
    updateHealth(model.getHealth());
    updateHunger(model.getHunger());
    updateSocial(model.getSocial());
    updateSleep(model.getSleep());
    updateMood(model.getMood().toString());
    updatePersonality(model.getPersonality() != null ? model.getPersonality().getClass().getSimpleName() : "No Personality Set");
    updateLastInteractedTime(model.getLastInteractedTime());
    updateInventory(model.getInventory().getItems());
    updatePetImage(model.getPetName(), false); // Ensure the pet image is updated
    setActionButtonsEnabled(true);
    toggleAttributesVisibility(true);
    toggleButtonsVisibility(true);
    togglePersonalitySelection(false);
  }

}
