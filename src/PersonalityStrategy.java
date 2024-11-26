/**
 * Interface for the personality strategy.
 */
/**
 * Defines a strategy for adjusting pet personality behaviors.
 * Implements the Strategy pattern to encapsulate different personality behaviors
 * that can be applied to pets.
 * 
 */
public interface PersonalityStrategy {
  void adjustBehavior(Pet pet);
}
