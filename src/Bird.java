import java.io.Serializable;


/**
 * The Bird class implements the PersonalityStrategy interface and provides
 * behavior adjustment for a pet based on its social level. It also implements
 * Serializable for object serialization.
 * 
 * <p>
 * This class contains a single method, adjustBehavior, which modifies the
 * preferred action of the pet based on its social attribute.
 * </p>
 * 
 * <p>
 * If the pet's social level is below 40, the preferred action is set to PLAY.
 * Otherwise, the preferred action is set to SLEEP.
 * </p>
 * 
 * @see PersonalityStrategy
 * @see Serializable
 */
public class Bird implements PersonalityStrategy, Serializable {
  private static final long serialVersionUID = 1L;

  @Override
  public void adjustBehavior(Pet pet) {
    if (pet.getSocial() < 40) {
      pet.setPreferredAction(Action.PLAY);
    } else {
      pet.setPreferredAction(Action.SLEEP);
    }
  }
}
