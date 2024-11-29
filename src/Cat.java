import java.io.Serializable;

/**
 * The {@code Cat} class implements the {@link PersonalityStrategy} interface 
 * and supports object serialization by implementing {@link Serializable}.
 * 
 * <p>
 * This class adjusts the behavior of a pet based on its personality traits. 
 * Specifically, it sets the pet's preferred action depending on its hunger level:
 * <ul>
 *   <li>If the hunger level is greater than 50, the preferred action is set to {@link Action#FEED}.</li>
 *   <li>Otherwise, the preferred action is set to {@link Action#SLEEP}.</li>
 * </ul>
 * </p>
 * 
 * <p>Usage example:</p>
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
 * @see PersonalityStrategy
 * @see Serializable
 */
public class Cat implements PersonalityStrategy, Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * Adjusts the behavior of the given pet based on its hunger level.
   * 
   * <p>
   * This method sets the preferred action of the pet to {@link Action#FEED} 
   * if its hunger level is greater than 50. Otherwise, the preferred action 
   * is set to {@link Action#SLEEP}.
   * </p>
   * 
   * @param pet the pet whose behavior is being adjusted
   * @throws IllegalArgumentException if {@code pet} is {@code null}
   */
  @Override
  public void adjustBehavior(Pet pet) {
    if (pet == null) {
      throw new IllegalArgumentException("Pet cannot be null.");
    }
    if (pet.getHunger() > 50) {
      pet.setPreferredAction(Action.FEED);
    } else {
      pet.setPreferredAction(Action.SLEEP);
    }
  }
}
