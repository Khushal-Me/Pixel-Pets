import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Represents a virtual pet with various attributes and behaviors.
 * This class implements the PetModel interface and Serializable for save/load functionality.
 * The pet has basic attributes like hunger, social interaction, sleep, and health,
 * which affect its overall mood and behavior.
 * 
 * The pet can:
 * - Be fed, played with, and put to sleep
 * - Have different personalities (Dog, Cat, Bird)
 * - Generate and collect items in its inventory
 * - Be saved to and loaded from files
 * - Auto-save its state periodically
 * - Die if health reaches zero and be revived
 * 
 * The class manages several background tasks through ExecutorServices:
 * - Attribute updates over time
 * - Automatic item generation
 * - Auto-save functionality
 * - Sleep/wake cycle management
 * 
 * All attributes are bounded between 0 and 100, and the pet's health
 * decreases when its needs (hunger, social, sleep) are not met.
 * 
 * @see PetModel
 * @see Serializable
 * @see PersonalityStrategy
 * @see Inventory
 * @see TimeSimulator
 */
public class Pet implements PetModel, Serializable {
  private static final long serialVersionUID = 1L;

  private static final String SAVE_FILE_PREFIX = "saves/pet_save_";

  private transient ScheduledExecutorService autosaveService;
  private int hunger;
  private int social;
  private int sleep;
  private int health;
  private Inventory inventory;
  // Mark transient fields that cannot be serialized
  private transient TimeSimulator timeSimulator;
  private transient ScheduledExecutorService executorService1;
  private transient ScheduledExecutorService executorService2;
  private transient ScheduledExecutorService autoSaveExecutor;
  private Mood mood;
  private PersonalityStrategy personality;
  private Action preferredAction;
  private long lastInteractedTime;
  private int checkCount = 0;  // Counter to track the number of checks
  private boolean isSleeping;
  private boolean personalitySet = false;
  private String message;
  private boolean isDead = false;
  private boolean allowTaskExecution = false;
  private transient ScheduledExecutorService itemGeneratorService;



  /**
   * Constructor for the Pet class.
   */
  public Pet() {
    hunger = 20;
    social = 80;
    sleep = 20;
    health = 100;
    mood = Mood.HAPPY;
    inventory = new Inventory();

    timeSimulator = new TimeSimulator(this);
    timeSimulator.startAttributeUpdates();
    lastInteractedTime = System.currentTimeMillis();
    executorService1 = Executors.newScheduledThreadPool(1);
    startItemGenerator();

  }

  /**
   * Start the timer.
   */
  public void startTimer() {
    executorService1.scheduleAtFixedRate(() -> {
      if (allowTaskExecution) {
        long currentTime = System.currentTimeMillis();
        long timeSinceLastInteraction = (currentTime - lastInteractedTime) / 1000;

        if (timeSinceLastInteraction > 1) {
          timeSimulator.startAttributeUpdates();
        }
        update();
      }
    }, 3, 5, TimeUnit.SECONDS);

    if (health <= 0) {
      executorService1.shutdown();
    }
  }

  public void setAllowTaskExecution() {
    allowTaskExecution = true;
  }

  /**
   * Set the hunger level of the pet.
   *
   * @param hunger the hunger level of the pet.
   */
  public void setHunger(int hunger) {
    if (hunger < 0) {
      hunger = 0;
      System.out.println("The minimum hunger level is 0.");
    }
    if (hunger > 100) {
      hunger = 100;
      System.out.println("The maximum hunger level is 100.");
    }
    this.hunger = hunger;
  }


  /**
   * Set the social level of the pet.
   *
   * @param social the social level of the pet.
   */
  public void setSocial(int social) {
    if (social < 0) {
      social = 0;
      System.out.println("The minimum social level is 0.");
    }
    if (social > 100) {
      social = 100;
      System.out.println("The maximum social level is 100.");
    }
    this.social = social;
  }

  /**
   * Set the sleep level of the pet.
   *
   * @param sleep the sleep level of the pet.
   */
  public void setSleep(int sleep) {
    if (sleep < 0) {
      sleep = 0;
      System.out.println("The minimum sleep level is 0.");
    }
    if (sleep > 100) {
      sleep = 100;
      System.out.println("The maximum sleep level is 100.");
    }
    this.sleep = sleep;
  }

  /**
   * Set the health level of the pet.
   *
   * @param health the health level of the pet.
   */
  public void setHealth(int health) {
    if (health < 0) {
      health = 0;
      System.out.println("The minimum health level is 0.");
    }
    if (health > 100) {
      health = 100;
      System.out.println("The maximum health level is 100.");
    }
    this.health = health;
  }

  /**
   * Feed th pet, the hunger level will decrease by 10 and the health level will increase by 5.
   */
  @Override
  public void feed() {
    if (isSleeping) {
      message = "Pet is sleeping. Please wait until it wakes up.";
      lastInteractedTime = System.currentTimeMillis();
      return;
    }
    message = "You fed your pet!";
    hunger -= 10;
    if (hunger < 0) {
      hunger = 0;
    }
    health += 2;
    lastInteractedTime = System.currentTimeMillis();
    checkBounds();
  }

  /**
   * Play with the pet, the social level will increase by 10 and the health level will increase by
   * 5.
   */
  @Override
  public void play() {
    if (isSleeping) {
      message = "Pet is sleeping. Please wait until it wakes up.";
      lastInteractedTime = System.currentTimeMillis();
      return;
    }
    message = "You played with your pet!";
    social += 10;
    if (social < 0) {
      social = 0;
    }
    health += 2;
    lastInteractedTime = System.currentTimeMillis();
    checkBounds();
  }

  /**
   * Put the pet to sleep, the sleep level will decrease by 10 and the health level will increase by
   * 5.
   */
  @Override
  public void sleep() throws IllegalStateException {
    executorService2 = Executors.newScheduledThreadPool(1);
    if (!isSleeping) {
      isSleeping = true;
      message = "You put your pet to sleep!";
      executorService2.schedule(this::wakeUp, 10, TimeUnit.SECONDS);
    } else {
      throw new IllegalStateException("Pet is already sleeping");
    }
  }

  /**
   * Wake the pet up.
   */
  private void wakeUp() {
    isSleeping = false;
    message = "Your pet woke up!";
    lastInteractedTime = System.currentTimeMillis();
    sleep -= 10;
    if (sleep > 100) {
      sleep = 100;
    }
    health += 5;
    checkBounds();
  }

  /**
   * Get the current hunger level of the pet.
   */
  @Override
  public int getHunger() {
    checkBounds();
    return hunger;
  }

  /**
   * Get the current social level of the pet.
   */
  @Override
  public int getSocial() {
    checkBounds();
    return social;
  }

  /**
   * Get the current sleep level of the pet.
   */
  @Override
  public int getSleep() {
    checkBounds();
    return sleep;
  }

  /**
   * Get the current health level of the pet.
   */
  @Override
  public int getHealth() {
    checkBounds();
    return health;
  }

  /**
   * Get the personality of the pet.
   */
  @Override
  public PersonalityStrategy getPersonality() {
    return personality;
  }

  /**
   * Get the last time the pet was interacted with.
   */
  @Override
  public long getLastInteractedTime() {
    return lastInteractedTime;
  }

  /**
   * Get the message.
   *
   * @return the message.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Display the attributes: hunger, sleep, social, of the pet.
   */
  @Override
  public String displayStates() {
    checkBounds();
    String[] states = new String[] {
        "Hunger: " + hunger,
        "Social: " + social,
        "Sleep: " + sleep,
        "Health: " + health,
        "Mood: " + mood,
        "Personality: " + personality.getClass().getSimpleName()
    };
    return String.join("\n", states);
  }

  /**
   * Assign a personality to the pet.
   */
  public void assignBehavior() {
    try (Scanner scanner = new Scanner(System.in)) {
      System.out.println("Choose the pet's personality:");
      System.out.println("1. Dog");
      System.out.println("2. Cat");
      System.out.println("3. Normal");
      System.out.print("Enter your choice:\n");

      int choice = scanner.nextInt();

      switch (choice) {
        case 1 -> {
          setPersonality(new Dog());
          System.out.println("Happy personality set.");
        }
        case 2 -> {
          setPersonality(new Cat());
          System.out.println("Grumpy personality set.");
        }
        case 3 -> {
          setPersonality(new Bird());
          System.out.println("Normal personality set.");
        }
        default -> {
          System.out.println("Invalid choice. Setting default personality.");
          setPersonality(new Bird());
        }
      }
    }
  }

  /**
   * Increase the hunger level of the pet.
   */
  public void increaseHunger() {
    hunger += 5;
    if (hunger > 100) {
      hunger = 100;
    }
  }

  /**
   * Increase the sleep level of the pet.
   */
  public void increaseSleep() {
    sleep += 15;
    if (sleep > 100) {
      sleep = 100;
    }
  }

  /**
   * Decrease the social level of the pet.
   */
  public void decreaseSocial() {
    social -= 2;
    if (social < 0) {
      social = 0;
    }
  }

  /**
   * Check if the pet's attributes are within the bounds.
   */
  public void checkBounds() {
    hunger = Math.min(Math.max(hunger, 0), 100);
    social = Math.min(Math.max(social, 0), 100);
    sleep = Math.min(Math.max(sleep, 0), 100);
    health = Math.min(Math.max(health, 0), 100);
  }

  /**
   * Get the mood of the pet.
   */
  @Override
  public Mood getMood() {
    if (hunger > 60 | social < 40 | sleep > 60) {
      mood = Mood.SAD;
    } else {
      mood = Mood.HAPPY;
    }
    return mood;
  }

  /**
   * Perform the preferred action.
   */
  @Override
  public void performPreferredAction() {
    if (preferredAction != null) {
      if (preferredAction == Action.SLEEP && isSleeping) {
        message = "Pet is already sleeping.";
        return;
      }
      switch (preferredAction) {
        case FEED -> feed();
        case PLAY -> play();
        case SLEEP -> sleep();
        default -> checkBounds();
      }
      applyBonus();
    }
  }

  private void applyBonus() {
    int bonus = 5;
    if (preferredAction != null) {
      switch (preferredAction) {
        case PLAY -> {
          social += bonus;
          health += bonus / 3; // Adjust health based on the bonus
          checkBounds();
          message = "You played with your pet!\nYou received a bonus for playing!\n";
        }
        // Add similar cases for other actions if needed
        case SLEEP -> {
          sleep -= bonus;
          health += bonus / 3; // Adjust health based on the bonus
          checkBounds();
          message = "Your put your pet to sleep! You received a bonus for sleeping!\n";
        }
        case FEED -> {
          hunger -= bonus;
          health += bonus / 3; // Adjust health based on the bonus
          checkBounds();
          message = "You fed your pet! You received a bonus for feeding!\n";
        }
        default -> checkBounds();
      }
    }
  }

  /**
   * Set the preferred action.
   *
   * @param action the preferred action.
   */
  public void setPreferredAction(Action action) {
    this.preferredAction = action;
  }

  /**
   * Get the preferred action.
   */
  @Override
  public Action getPreferredAction() {
    return preferredAction;
  }

  /**
   * Check if the pet is dead.
   */
  @Override
  public boolean checkDeath() {
    isDead = (health <= 0);
    return isDead;
  }

  /**
   * Check the health status of the pet.
   */
  @Override
  public String checkPetHealthStatus() {
      checkCount++;  // Increment the counter on each check
  
      // Delay the pop-up by returning an empty string for the first two checks
      if (checkCount <= 2) {
          return "";  // Skip the alert for the first two checks
      }
  
      // After 2 checks, show the health status
      if (health > 70) {
          return "Your pet is healthy!";
      } else if (health > 30) {
          return "Your pet is fine!";
      } else {
          return "Your pet is sick!";
      }
  }

  /**
   * Adjust the behavior of the pet.
   *
   * @param pet the pet.
   */
  public void adjustBehavior(Pet pet) {
    personality.adjustBehavior(pet);
  }

  /**
   * Set the personality of the pet.
   *
   * @param personality the personality of the pet.
   */
  public void setPersonality(PersonalityStrategy personality) {
    if (!personalitySet) {
      this.personality = personality;
      personalitySet = true;
    } else {
      System.out.println("Personality has already been set and cannot be changed.");
      // You can throw an exception or handle it as per your application's flow
    }
  }

  /**
   * Update the pet's attributes.
   */
  private void update() {
    long currentTime = System.currentTimeMillis();
    long timeSinceLastInteraction = (currentTime - lastInteractedTime) / 1000;

    if (timeSinceLastInteraction > 0.00001) {
      if (hunger > 70 || social < 30 || sleep > 70) {
        health -= 5;
      }
    }
  }

  /**
   * Reset the pet model.
   */
  public void reset() {
    hunger = 20;
    social = 80;
    sleep = 20;
    health = 100;
    mood = Mood.HAPPY;


    // Reset any other attributes or flags as needed
    isDead = false;
    isSleeping = false;
    personalitySet = false;
    personality = null;
    message = null; // Reset any message
    inventory = new Inventory();

    // Reset the last interacted time to the current time
    lastInteractedTime = System.currentTimeMillis();

    // Re-create or restart any necessary services or threads
    if (executorService1 != null) {
      executorService1.shutdownNow();
      executorService1 = Executors.newScheduledThreadPool(1);
    }

    // Reset the personality to null or a default one if applicable
    personality = null; // Or set it to a default personality

    if (autosaveService != null) {
      autosaveService.shutdownNow();
    }

  }

/*
 * auto save class
 */
public void startAutoSave(String slot, long interval, TimeUnit unit) {
  if (autoSaveExecutor != null) {
      autoSaveExecutor.shutdownNow();
  }
  autoSaveExecutor = Executors.newScheduledThreadPool(1);
  autoSaveExecutor.scheduleAtFixedRate(() -> save(slot), interval, interval, unit);
}

public void stopAutoSave() {
  if (autoSaveExecutor != null) {
      autoSaveExecutor.shutdownNow();
  }
}
 


  /**
     * Save the pet's state to a file.
  */
  public void save(String slot) {
    // Ensure the saves directory exists
    File savesDir = new File("saves");
    if (!savesDir.exists()) {
        savesDir.mkdirs();
    }

    // Construct the file path
    String filePath = SAVE_FILE_PREFIX + slot + ".dat";

    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
        out.writeObject(this);
        System.out.println("Game saved successfully.");
    } catch (IOException e) {
        System.err.println("Error saving game: " + e.getMessage());
    }
}

      /**
     * Load the pet's state from a file.
     *
     * @return the loaded Pet object
     */
    public static Pet load(String slot) {
      // Construct the file path
      String filePath = SAVE_FILE_PREFIX + slot + ".dat";
      File saveFile = new File(filePath);
  
      if (!saveFile.exists()) {
          // If the file doesn't exist, return null
          return null;
      }
  
      try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
          return (Pet) in.readObject();
      } catch (IOException | ClassNotFoundException e) {
          System.err.println("Error loading game: " + e.getMessage());
          return new Pet(); // Return a new Pet object if loading fails
      }
  }

  private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
    ois.defaultReadObject();
    // Reinitialize transient fields
    timeSimulator = new TimeSimulator(this);
    timeSimulator.startAttributeUpdates();
    executorService1 = Executors.newScheduledThreadPool(1);
    startTimer(); // Restart the timer
    startItemGenerator();
}
    
  public void stopTimers() {
      if (timeSimulator != null) {
          timeSimulator.stopAttributeUpdates();
    }
      if (executorService1 != null && !executorService1.isShutdown()) {
          executorService1.shutdownNow();
      }
  
      if (executorService2 != null && !executorService2.isShutdown()) {
          executorService2.shutdownNow();
      }
  
      if (autosaveService != null && !autosaveService.isShutdown()) {
          autosaveService.shutdownNow();
      }
      // Stop any other services or threads
      if (itemGeneratorService != null && !itemGeneratorService.isShutdown()) {
        itemGeneratorService.shutdownNow();
    }
  }
  private String petName;

  public String getPetName() {
    return petName;
}

  public void setPetName(String petName) {
    this.petName = petName;
}
public Inventory getInventory() {
  return inventory;
}

public void startItemGenerator() {
  if (itemGeneratorService == null || itemGeneratorService.isShutdown()) {
    itemGeneratorService = Executors.newSingleThreadScheduledExecutor();
    itemGeneratorService.scheduleAtFixedRate(() -> {
        addItemToInventory();
    }, 0, 30, TimeUnit.SECONDS); // Adjust the interval as needed
}
}

private void addItemToInventory() {
  Random random = new Random();
  int itemType = random.nextInt(2); // 0 for Food, 1 for Gift

  if (itemType == 0) {
      Food food = new Food("Food Item");
      inventory.addItem(food);
  } else {
      Gift gift = new Gift("Gift Item");
      inventory.addItem(gift);
  }
}

public void stopItemGenerator() {
  if (itemGeneratorService != null && !itemGeneratorService.isShutdown()) {
      itemGeneratorService.shutdownNow();
  }
}
// Call startItemGenerator() when the game starts

public void receiveGift() {
  // Implement the effect of receiving a gift
  // For example, increase social or mood attributes
  social = Math.min(social + 20, 100);
  mood = Mood.HAPPY;
}
public void increaseHealth(int amount) {
  health = Math.min(health + amount, 100);
}

public void updateFrom(Pet other) {
  this.hunger = other.hunger;
  this.social = other.social;
  this.sleep = other.sleep;
  this.health = other.health;
  this.inventory = other.inventory;
  this.mood = other.mood;
  this.personality = other.personality;
  this.preferredAction = other.preferredAction;
  this.lastInteractedTime = other.lastInteractedTime;
  this.isSleeping = other.isSleeping;
  this.message = other.message;
  this.isDead = other.isDead;
  this.allowTaskExecution = other.allowTaskExecution;
  // Add any other fields that need to be updated
}
// Add this method to reinitialize services after revival
public void reinitializeServices() {
  if (timeSimulator == null) {
    timeSimulator = new TimeSimulator(this);
    timeSimulator.startAttributeUpdates();
  }
  startItemGenerator(); // Restart item generator
}
public void revive() {
  if (isDead) {
      health = 100;
      hunger = 0; // Set hunger to minimum
      social = 100; // Set social to maximum
      sleep = 0;  // Set sleep to minimum
      mood = Mood.HAPPY;
      isDead = false;
      lastInteractedTime = System.currentTimeMillis();
      // Reinitialize any necessary transient fields
      startTimer();
      startItemGenerator();
      // Reset messages or other state variables
      message = "Your pet has been revived!";
      reinitializeServices();
  }
}
@Override
public void exercise() {
    if (isSleeping) {
        message = "Pet is sleeping. Please wait until it wakes up.";
        lastInteractedTime = System.currentTimeMillis();
        return;
    }
    message = "You exercised your pet!";
    hunger += 10; // Increase hunger
    sleep -= 10;  // decrease sleep (pet becomes sleepier)
    health += 10;  // Increase health
    lastInteractedTime = System.currentTimeMillis();
    checkBounds();
}

}
