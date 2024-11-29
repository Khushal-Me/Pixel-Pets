/**
 * Defines a strategy for adjusting pet personality behaviors.
 * <p>
 * This interface is part of the Strategy pattern and allows different behaviors 
 * to be encapsulated and applied to pets based on their personality.
 * Implementations of this interface will define how the pet's behavior is adjusted
 * depending on the strategy being used.
 * </p>
 * 
 * @author Khushal
 * @version 1.0
 */
public interface PersonalityStrategy {

  /**
   * Adjusts the behavior of the specified pet according to the personality strategy.
   *
   * @param pet the pet whose behavior is to be adjusted
   */
  void adjustBehavior(Pet pet);
}
