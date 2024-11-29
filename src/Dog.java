import java.io.Serializable;

/**
 * The Dog class implements the PersonalityStrategy interface and is Serializable.
 * It adjusts the behavior of a pet based on its health.
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
 * @param pet the pet whose behavior is to be adjusted
 * @see PersonalityStrategy
 * @see Serializable
 * @see Pet
 * @see Action
 */
public class Dog implements PersonalityStrategy, Serializable {
  private static final long serialVersionUID = 1L;
  /**
   * The method that adjusts the behavior of the pet.
   *
   * @param pet the pet whose behavior is to be adjusted
   */
  @Override
  public void adjustBehavior(Pet pet) {
    if (pet.getHealth() < 70) {
      pet.setPreferredAction(Action.FEED);
    } else {
      pet.setPreferredAction(Action.PLAY);
    }
  }
}
