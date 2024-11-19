import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class represents a pet.
 */
public class Pet implements PetModel, Serializable {

  private static final long serialVersionUID = 1L;

  private static final String SAVE_FILE = "pet_save.dat";

  private ScheduledExecutorService autosaveService;

  private int hunger;
  private int social;
  private int sleep;
  private int health;

  // Mark transient fields that cannot be serialized
  private transient TimeSimulator timeSimulator;
  private transient ScheduledExecutorService executorService1;
  private transient ScheduledExecutorService executorService2;

  private Mood mood;
  private PersonalityStrategy personality;
  private Action preferredAction;

  private long lastInteractedTime;

  private int checkCount = 0;  // Counter to track the number of checks
  private boolean isSleeping;
  /*private final TimeSimulator timeSimulator;
  private ScheduledExecutorService executorService1;
  private ScheduledExecutorService executorService2; */
  private boolean personalitySet = false;

  private String message;

  private boolean isDead = false;
  private boolean allowTaskExecution = false;


  /**
   * Constructor for the Pet class.
   */
  public Pet() {
    hunger = 20;
    social = 80;
    sleep = 20;
    health = 100;
    mood = Mood.HAPPY;

    timeSimulator = new TimeSimulator(this);
    lastInteractedTime = System.currentTimeMillis();
    executorService1 = Executors.newScheduledThreadPool(1);
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
    sleep += 5;
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
  public void startAutosave() {
    autosaveService = Executors.newScheduledThreadPool(1);
    autosaveService.scheduleAtFixedRate(this::save, 10, 10, TimeUnit.MINUTES); // Autosave every 10 minutes
}


  /**
     * Save the pet's state to a file.
  */
  public void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            out.writeObject(this);
            System.out.println("Pet saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving pet: " + e.getMessage());
        }
  }

      /**
     * Load the pet's state from a file.
     *
     * @return the loaded Pet object
     */
  public static Pet load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            return (Pet) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading pet: " + e.getMessage());
            return new Pet(); // Return a new Pet object if loading fails
        }
  }

  private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        
        // Reinitialize transient fields
        timeSimulator = new TimeSimulator(this);
        executorService1 = Executors.newScheduledThreadPool(1);
        startTimer(); // Restart the timer
    }

}