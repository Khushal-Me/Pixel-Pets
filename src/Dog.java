import java.io.Serializable;

/**
 * The {@code Dog} class implements the {@link PersonalityStrategy} interface 
 * and supports object serialization by implementing {@link Serializable}.
 * 
 * <p>
 * This class adjusts the behavior of a pet based on its health. Specifically:
 * <ul>
 *   <li>If the health level is below 70, the preferred action is set to {@link Action#FEED}.</li>
 *   <li>Otherwise, the preferred action is set to {@link Action#PLAY}.</li>
 * </ul>
 * </p>
 * 
 * <p>Usage example:</p>
 * <pre>
 * {@code
 * Dog dog = new Dog();
 * Pet pet = new Pet();
 * dog.adjustBehavior(pet);
 * }
 * </pre>
 * 
 * @author Khushal
 * @version 1.0
 * @see PersonalityStrategy
 * @see Serializable
 * @see Pet
 * @see Action
 */
public class Dog implements PersonalityStrategy, Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * Adjusts the behavior of the given pet based on its health level.
   * 
   * <p>
   * This method sets the preferred action of the pet to {@link Action#FEED} 
   * if its health level is below 70. Otherwise, the preferred action is set 
   * to {@link Action#PLAY}.
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
    if (pet.getHealth() < 70) {
      pet.setPreferredAction(Action.FEED);
    } else {
      pet.setPreferredAction(Action.PLAY);
    }
  }
}
