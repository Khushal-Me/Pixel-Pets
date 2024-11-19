/**
 * The class represents a cat.
 */
public class Cat implements PersonalityStrategy {

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
