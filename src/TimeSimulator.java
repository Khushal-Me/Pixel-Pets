import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class is responsible for updating the pet's attributes every minute.
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