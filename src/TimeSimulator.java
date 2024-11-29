import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A class that simulates the passage of time and updates pet attributes periodically.
 * This class implements Serializable to allow for object persistence.
 * It manages a scheduled executor service that periodically updates the pet's attributes
 * such as hunger, sleep, and social needs.
 *
 * The simulator can be started, stopped, and will automatically reinitialize after
 * deserialization through custom serialization handling.
 *
 * <p>The updates occur at fixed intervals of one minute and include:
 * <ul>
 *   <li>Increasing hunger level
 *   <li>Increasing sleep needs
 *   <li>Decreasing social interaction
 *   <li>Checking attribute bounds
 *   <li>Adjusting behavior based on personality
 * 
 * @author Ramje, Khushal
 * @version 1.0
 * 
 * @see Pet
 * @see Serializable
 */
public class TimeSimulator implements Serializable {

  private static final long serialVersionUID = 1L;
  private transient  ScheduledExecutorService executorService;
  private final Pet pet;

  /**
   * Constructor for the TimeSimulator class.
   *
   * @param pet the pet to be updated.
   */
  public TimeSimulator(Pet pet) {
    this.pet = pet;
    executorService = Executors.newScheduledThreadPool(1);
  }

  private void initializeExecutorService() {
    executorService = Executors.newScheduledThreadPool(1);
  }

  /**
   * Start the attribute updates.
   */
  public void startAttributeUpdates() {
    if (executorService == null || executorService.isShutdown()) {
      initializeExecutorService();
    }
    executorService.scheduleAtFixedRate(this::updateAttributes, 0, 1, TimeUnit.MINUTES);
  }

  /**
   * Update the pet's attributes.
   */
  private void updateAttributes() {
    pet.increasescore();
    pet.increaseHunger();
    pet.increaseSleep();
    pet.decreaseSocial();
    pet.checkBounds();
    if (pet.getPersonality() != null) {
      pet.getPersonality().adjustBehavior(pet);
    }
  }

  /**
   * Stop the attribute updates.
   */
  public void stopAttributeUpdates() {
    if (executorService != null && !executorService.isShutdown()) {
      executorService.shutdownNow();
    }
  }
  private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
  ois.defaultReadObject(); // Perform default deserialization

  // Reinitialize transient fields
  initializeExecutorService();
  startAttributeUpdates();
}

}