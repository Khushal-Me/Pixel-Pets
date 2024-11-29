import java.io.Serializable;

/**
 * The {@code Bird} class implements the {@link PersonalityStrategy} interface
 * and provides behavior adjustment for a pet based on its social level. 
 * This class also implements {@link Serializable} to support object serialization.
 * 
 * <p>
 * The {@code adjustBehavior} method modifies the preferred action of the pet 
 * based on its social attribute:
 * <ul>
 *   <li>If the pet's social level is below 40, the preferred action is set to {@link Action#PLAY}.</li>
 *   <li>If the pet's social level is 40 or above, the preferred action is set to {@link Action#SLEEP}.</li>
 * </ul>
 * </p>
 * 
 * @author Khushal
 * @version 1.0
 * @see PersonalityStrategy
 * @see Serializable
 */
public class Bird implements PersonalityStrategy, Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * Adjusts the behavior of the given pet based on its social attribute.
   * 
   * <p>
   * This method sets the preferred action of the pet to {@link Action#PLAY} 
   * if its social level is below 40. Otherwise, the preferred action is set 
   * to {@link Action#SLEEP}.
   * </p>
   * 
   * @param pet the pet whose behavior is to be adjusted
   * @throws IllegalArgumentException if {@code pet} is {@code null}
   */
  @Override
  public void adjustBehavior(Pet pet) {
    if (pet == null) {
      throw new IllegalArgumentException("Pet cannot be null.");
    }
    if (pet.getSocial() < 40) {
      pet.setPreferredAction(Action.PLAY);
    } else {
      pet.setPreferredAction(Action.SLEEP);
    }
  }
}
