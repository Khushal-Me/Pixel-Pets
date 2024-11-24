
import java.io.Serializable;

/**
 * This class represents a normal personality for a pet.
 */
public class Bird implements PersonalityStrategy, Serializable {
  private static final long serialVersionUID = 1L;
  /**
   * This method adjusts the behavior of the pet based on its personality.
   *
   * @param pet the pet whose behavior is being adjusted
   */
  @Override
  public void adjustBehavior(Pet pet) {
    if (pet.getSocial() < 40) {
      pet.setPreferredAction(Action.PLAY);
    } else {
      pet.setPreferredAction(Action.SLEEP);
    }
  }
}
