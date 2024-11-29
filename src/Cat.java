import java.io.Serializable;

/**
 * The Cat class implements the PersonalityStrategy interface and is Serializable.
 * It adjusts the behavior of a pet based on its personality traits.
 * 
 * <p>This class is part of the group07 project and is located at:
 * 
 * <p>It contains the following:
 * <ul>
 *   <li>A serialVersionUID for serialization purposes.</li>
 *   <li>An implementation of the adjustBehavior method from the PersonalityStrategy interface.</li>
 * </ul>
 * 
 * <p>Usage example:
 * <pre>
 * {@code
 * Cat cat = new Cat();
 * Pet pet = new Pet();
 * cat.adjustBehavior(pet);
 * }
 * </pre>
 * 
 * @author Khushal
 * @version 1.0
 * @param pet the pet whose behavior is to be adjusted
 * @see PersonalityStrategy
 * @see Serializable
 */
public class Cat implements PersonalityStrategy, Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * This method adjusts the behavior of the pet based on its personality.
   *
   * @param pet the pet whose behavior is being adjusted
   */
  @Override
  public void adjustBehavior(Pet pet) {
    if (pet.getHunger() > 50) {
      pet.setPreferredAction(Action.FEED);
    } else {
      pet.setPreferredAction(Action.SLEEP);
    }
  }
}