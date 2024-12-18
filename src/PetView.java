import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.border.LineBorder;
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
 * 
 * @author Chris, Jonathan, Ramje, Khushal
 * @version 1.0
 * 
 * @see JFrame
 * 
 */
public class PetView extends JFrame {

  //private final javax.swing.Timer scoreTimer;
  private final JProgressBar healthBar;
  private final JProgressBar hungerBar;
  private final JProgressBar socialBar;
  private final JProgressBar sleepBar;
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
  //private final boolean isDialogOpen = false;
  private final JLabel petImageLabel;
  private final JButton backButton; 
  private final DefaultListModel<String> inventoryListModel;
  private final JList<String> inventoryList;
  private final JButton useItemButton;
  private final JButton vetButton;
  private final JButton saveButton;
  private final JLabel scoreLabel;
  private final JButton exerciseButton;
  private JSlider volumeSlider;


  /**
   * Constructor for the view.
   */
  public PetView() {
    setTitle("Pixel Pets");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel();
    panel.setLayout(null);

    personalityLabel = new JLabel("Choose Personality:");
    personalityComboBox = new JComboBox<>(new String[] {"Happy", "Grumpy", "Normal"});
    confirmButton = new JButton("Confirm");
    scoreLabel = new JLabel("Score: 0");

    panel.add(scoreLabel);
    scoreLabel.setFont(new Font("Arial", Font.BOLD, 28));
    panel.add(personalityLabel);
    panel.add(personalityComboBox);
    panel.add(confirmButton);

    healthBar = new JProgressBar(0, 100);
    healthBar.setStringPainted(true);
    healthBar.setBounds(125, 62, 150, 25); 

    hungerBar = new JProgressBar(0, 100);
    hungerBar.setStringPainted(true);
    hungerBar.setBounds(125, 87, 150, 25); 

    socialBar = new JProgressBar(0, 100);
    socialBar.setStringPainted(true);
    socialBar.setBounds(125, 112, 150, 25); 

    sleepBar = new JProgressBar(0, 100);
    sleepBar.setStringPainted(true);
    sleepBar.setBounds(125, 137, 150, 25);
    

    panel.add(healthBar);
    panel.add(hungerBar);
    panel.add(socialBar);
    panel.add(sleepBar);
  
    moodLabel = new JLabel("Mood:");
    lastInteractedLabel = new JLabel("Last Interaction: ");

    // Button: Feed
    feedButton = new JButton("Feed");
    feedButton.setFont(new Font("Arial", Font.PLAIN, 20));
    feedButton.setBorder(new LineBorder(Color.BLACK, 3));
    feedButton.setBackground(Color.WHITE);
    feedButton.setOpaque(true);
    feedButton.setFocusPainted(false);
    feedButton.setHorizontalAlignment(SwingConstants.CENTER);
    feedButton.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(Color.BLACK, 3),
        BorderFactory.createEmptyBorder(1, 2, 1, 2)
    ));

    // Button: Play
    playButton = new JButton("Play");
    playButton.setFont(new Font("Arial", Font.PLAIN, 20));
    playButton.setBorder(new LineBorder(Color.BLACK, 3));
    playButton.setBackground(Color.WHITE);
    playButton.setOpaque(true);
    playButton.setFocusPainted(false);
    playButton.setHorizontalAlignment(SwingConstants.CENTER);
    playButton.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(Color.BLACK, 3),
        BorderFactory.createEmptyBorder(1, 2, 1, 2)
    ));

    // Button: Sleep
    sleepButton = new JButton("Sleep");
    sleepButton.setFont(new Font("Arial", Font.PLAIN, 20));
    sleepButton.setBorder(new LineBorder(Color.BLACK, 3));
    sleepButton.setBackground(Color.WHITE);
    sleepButton.setOpaque(true);
    sleepButton.setFocusPainted(false);
    sleepButton.setHorizontalAlignment(SwingConstants.CENTER);
    sleepButton.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(Color.BLACK, 3),
        BorderFactory.createEmptyBorder(1, 2, 1, 2)
    ));

    // Button: Get Preferred Action
    getPreferredActionButton = new JButton("Get Preferred Action");
    getPreferredActionButton.setFont(new Font("Arial", Font.PLAIN, 20));
    getPreferredActionButton.setBorder(new LineBorder(Color.BLACK, 3));
    getPreferredActionButton.setBackground(Color.WHITE);
    getPreferredActionButton.setOpaque(true);
    getPreferredActionButton.setFocusPainted(false);
    getPreferredActionButton.setHorizontalAlignment(SwingConstants.CENTER);
    getPreferredActionButton.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(Color.BLACK, 3),
        BorderFactory.createEmptyBorder(1, 2, 1, 2)
    ));

    // Button: Perform Preferred Action
    performPreferredActionButton = new JButton("Do Preferred Action");
    performPreferredActionButton.setFont(new Font("Arial", Font.PLAIN, 20));
    performPreferredActionButton.setBorder(new LineBorder(Color.BLACK, 3));
    performPreferredActionButton.setBackground(Color.WHITE);
    performPreferredActionButton.setOpaque(true);
    performPreferredActionButton.setFocusPainted(false);
    performPreferredActionButton.setHorizontalAlignment(SwingConstants.CENTER);
    performPreferredActionButton.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(Color.BLACK, 3),
        BorderFactory.createEmptyBorder(1, 2, 1, 2)
    ));

    // Button: Back to Main Menu
    backButton = new JButton("Back to Main Menu");
    backButton.setFont(new Font("Arial", Font.PLAIN, 20));
    backButton.setBorder(new LineBorder(Color.BLACK, 3));
    backButton.setBackground(Color.WHITE);
    backButton.setOpaque(true);
    backButton.setFocusPainted(false);
    backButton.setHorizontalAlignment(SwingConstants.CENTER);
    backButton.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(Color.BLACK, 3),
        BorderFactory.createEmptyBorder(1, 2, 1, 2)
    ));

    // Button: Save Game
    saveButton = new JButton("Save Game");
    saveButton.setFont(new Font("Arial", Font.PLAIN, 20));
    saveButton.setBorder(new LineBorder(Color.BLACK, 3));
    saveButton.setBackground(Color.WHITE);
    saveButton.setOpaque(true);
    saveButton.setFocusPainted(false);
    saveButton.setHorizontalAlignment(SwingConstants.CENTER);
    saveButton.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(Color.BLACK, 3),
        BorderFactory.createEmptyBorder(1, 2, 1, 2)
    ));

    // Button: Exercise
    exerciseButton = new JButton("Exercise");
    exerciseButton.setFont(new Font("Arial", Font.PLAIN, 20));
    exerciseButton.setBorder(new LineBorder(Color.BLACK, 3));
    exerciseButton.setBackground(Color.WHITE);
    exerciseButton.setOpaque(true);
    exerciseButton.setFocusPainted(false);
    exerciseButton.setHorizontalAlignment(SwingConstants.CENTER);
    exerciseButton.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(Color.BLACK, 3),
        BorderFactory.createEmptyBorder(1, 2, 1, 2)
    ));

    // Button: Take Pet to Vet
    vetButton = new JButton("Take Pet to Vet");
    vetButton.setFont(new Font("Arial", Font.PLAIN, 20));
    vetButton.setBorder(new LineBorder(Color.BLACK, 3));
    vetButton.setBackground(Color.WHITE);
    vetButton.setOpaque(true);
    vetButton.setFocusPainted(false);
    vetButton.setHorizontalAlignment(SwingConstants.CENTER);
    vetButton.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(Color.BLACK, 3),
        BorderFactory.createEmptyBorder(1, 2, 1, 2)
    ));


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

    // Add a message area to display messages
    messageArea = new JTextArea(20, 30);
    messageArea.setEditable(false); // Make the messageArea read-only
    messageArea.setLineWrap(true);
    messageArea.setWrapStyleWord(true);
    JScrollPane scrollPane = new JScrollPane(messageArea);


    petImageLabel = new JLabel();
    petImageLabel.setHorizontalAlignment(JLabel.CENTER);
    // Add the petImageLabel to the panel at the desired position
    panel.add(petImageLabel);

    // In the constructor
    volumeSlider = new JSlider(0, 100, 100); // Min 0, Max 100, Initial 100
    volumeSlider.setMajorTickSpacing(25);
    volumeSlider.setPaintTicks(true);
    volumeSlider.setPaintLabels(true);
    panel.add(new JLabel("Volume"));
    panel.add(volumeSlider);

    volumeSlider.addChangeListener(e -> {
      int value = volumeSlider.getValue();
      float volume = value / 100f; // Convert to 0.0 - 1.0
      MusicPlayer.getInstance().setVolume(volume);
    });
    
    // Position the "Back to Main Menu" button at the top left
  backButton.setBounds(20, 10, 200, 40); // x, y, width, height
  panel.add(backButton);

  // Position the "Save Game" button at the top right
  saveButton.setBounds(615, 10, 150, 40); // x, y, width, height
  panel.add(saveButton);

  moodLabel.setBounds(370, 17, 150, 25);
  personalityLabel.setBounds(370, 5, 150, 25);
  lastInteractedLabel.setBounds(50, 250, 150, 25);

  // Position the score label compactly
  scoreLabel.setBounds(350, 35, 150, 25); // Adjust y position for compactness
  panel.add(scoreLabel);

  // Position the inventory panel
  inventoryPanel.setBounds(465, 260, 240, 140); // Adjust for compactness
  panel.add(inventoryPanel);


  volumeSlider.setBounds(0, 520, 150, 50); // Adjust slider width and position
  panel.add(volumeSlider);

  // Position the scrollable message area compactly
  scrollPane.setBounds(465, 400, 240,90 ); // Reduce width and height
  panel.add(scrollPane);

  // Position the pet image label
  petImageLabel.setBounds(50, 175, 300, 300); // Adjust for compact layout
  panel.add(petImageLabel);

  // Position the buttons on the right side
  feedButton.setBounds(540, 90, 90, 30); // Compact buttons start lower
  playButton.setBounds(540, 125, 90, 30);
  sleepButton.setBounds(540, 160, 90, 30);
  exerciseButton.setBounds(540, 195, 90, 30);
  vetButton.setBounds(600, 500, 180, 40);
  getPreferredActionButton.setBounds(96, 480, 200, 40);
  performPreferredActionButton.setBounds(380, 500, 200, 40);

  panel.add(moodLabel);
  panel.add(personalityLabel);
  panel.add(exerciseButton);
  panel.add(feedButton);
  panel.add(playButton);
  panel.add(sleepButton);
  panel.add(vetButton);
  panel.add(backButton);
  panel.add(getPreferredActionButton);
  panel.add(performPreferredActionButton);
  panel.add(saveButton);
  panel.add(scrollPane);
  panel.add(inventoryPanel);
  add(panel);
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


  /**
   * This method updates the pet image based on its state.
   *
   * @param petName the name of the pet
   * @param isDead whether the pet is dead
   * @param isSleeping whether the pet is sleeping
   */
public void updatePetImage(String petName, boolean isDead, boolean isSleeping) {
  String imagePath;
  if (isDead) {
      imagePath = "/res/" + petName.toLowerCase() + "_dead.jpg";
  } else if (isSleeping) {
      imagePath = "/res/" + petName.toLowerCase() + "_sleep.jpg";
  } else {
      imagePath = "/res/" + petName.toLowerCase() + ".jpg";
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


  /**
   * This method adds a confirm button listener to the confirm button.
   *
   * @param listener the listener to be added
   */
public void addBackButtonListener(ActionListener listener) {
    backButton.addActionListener(listener);
}


  /**
   * This method adds a save button listener to the save button.
   *
   * @param saveListener the listener to be added
   */
public void addSaveListener(ActionListener saveListener) {
    saveButton.addActionListener(saveListener);
}


  /**
   * This method notifies the controller of the personality choice.
   *
   * @param petName the name of the pet
   */
public void notifyControllerOfPersonalityChoice(String petName) {
    PersonalityStrategy selectedPersonality = mapNameToPersonality(petName);
    controller.handleSelectedPersonality(selectedPersonality, petName);
}

  /**
   * This method maps the index of the personality to the personality.
   *
   * @param petName the index of the personality
   * @return the personality mapped to the index
   */
private PersonalityStrategy mapNameToPersonality(String petName) {
    PersonalityStrategy selectedPersonality;
    selectedPersonality = switch (petName) {
        case "Dog" -> {
        //    messageArea.append("The Dog pet was selected.\n");
            yield new Dog();
        }
        case "Cat" -> {
        //    messageArea.append("The Cat pet was selected.\n");
            yield new Cat();
        }
        case "Bird" -> {
        //    messageArea.append("The Bird pet was selected.\n");
            yield new Bird();
        }
        default -> {
        //    messageArea.append("The Bird pet was selected.\n");
            yield new Bird();
        }
    };

    return selectedPersonality;
}

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
    healthBar.setValue(health);
    healthBar.setString("Health: " + health);
    healthBar.setForeground(Color.gray); 
  }


  /**
   * This method updates the score label.
   *
   * @param score the score to be displayed
   */
  public void updateScore(int score) {
    scoreLabel.setText("Score: " + score);
  }

  /**
   * This method updates the hunger label.
   *
   * @param hunger the hunger status to be displayed
   */
  public void updateHunger(int hunger) {
    hungerBar.setValue(hunger);
    hungerBar.setString("Hunger: " + hunger);
    hungerBar.setForeground(Color.gray); 
  }

  /**
   * This method updates the social label.
   *
   * @param social the social status to be displayed
   */
  public void updateSocial(int social) {
    socialBar.setValue(social);
    socialBar.setString("Social: " + social);
    socialBar.setForeground(Color.gray);
  }

  /**
   * This method updates the sleep label.
   *
   * @param sleep the sleep status to be displayed
   */
  public void updateSleep(int sleep) {
    sleepBar.setValue(sleep);
    sleepBar.setString("Sleep: " + sleep);
    sleepBar.setForeground(Color.gray);
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
    personalityLabel.setText("Pixel Pet: " + personality);
  }

  /**
   * This method toggles the visibility of the attributes.
   *
   * @param visible whether the attributes should be visible
   */
  public void toggleAttributesVisibility(boolean visible) {
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
    exerciseButton.setVisible(visible);
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
    exerciseButton.setEnabled(enabled);
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

    //resetHungerAlert();
    //resetSleepAlert();
    //resetSocialAlert();

    displayPetSelectionDialog();
  }


  /**
   * This method adds an exercise listener to the exercise button.
   *
   * @param exerciseListener the listener to be added
   */
  public void setActionButtonsEnabled(boolean enabled) {
    feedButton.setEnabled(enabled);
    playButton.setEnabled(enabled);
    sleepButton.setEnabled(enabled);
    getPreferredActionButton.setEnabled(enabled);
    performPreferredActionButton.setEnabled(enabled);
}

  public class AlertUtils {
    private static boolean isDialogOpen = false;


    /**
     * This method displays an alert dialog with a message and title.
     *
     * @param message the message to be displayed
     * @param title the title of the dialog
     * @param action the action to be performed
     * @param flag the flag to avoid duplicates
     */
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


/**
 * This method displays the inventory dialog.
 * 
 * @param items
 */
    public void updateInventory(List<Item> items) {
        inventoryListModel.clear();
        for (Item item : items) {
            inventoryListModel.addElement(item.getName());
        }
    }


    /**
     * This method adds a use item listener to the use item button.
     *
     * @param listener the listener to be added
     */
    public void addUseItemListener(ActionListener listener) {
        useItemButton.addActionListener(listener);
    }


    /**
     * This method displays the get selected item.
     *
     * @param items the items to be displayed
     * @return null
     */
    public Item getSelectedItem(List<Item> items) {
        int selectedIndex = inventoryList.getSelectedIndex();
        if (selectedIndex != -1) {
            return items.get(selectedIndex);
        } else {
            return null;
        }
    }

    /**
     * This method displays the listen for vet button.
     *
     * @param listener
     */
    public void addVetButtonListener(ActionListener listener) {
      vetButton.addActionListener(listener);
  }


  /**
   * This method updates the view with the model.
   *
   * @param model
   */
  public void updateViewWithModel(Pet model) {
    updateScore(model.getscore());
    updateHealth(model.getHealth());
    updateHunger(model.getHunger());
    updateSocial(model.getSocial());
    updateSleep(model.getSleep());
    updateMood(model.getMood().toString());
    updatePersonality(model.getPersonality() != null ? model.getPersonality().getClass().getSimpleName() : "No Personality Set");
    updateLastInteractedTime(model.getLastInteractedTime());
    updateInventory(model.getInventory().getItems());
    updatePetImage(model.getPetName(), false, model.isSleeping()); // Ensure the pet image is updated
    setActionButtonsEnabled(true);
    toggleAttributesVisibility(true);
    toggleButtonsVisibility(true);
    togglePersonalitySelection(false);
  }
  public void addExerciseListener(ActionListener exerciseListener) {
    exerciseButton.addActionListener(exerciseListener);
}
}