/**
 * The PetModel interface represents a virtual pet with various attributes and actions.
 * It defines the core functionalities that a pet should have, including basic needs
 * management (feeding, playing, sleeping), status monitoring, and personality traits.
 *
 * The pet has several attributes:
 * - Hunger level
 * - Social level
 * - Sleep level
 * - Health level
 *
 * Each action (feed, play, sleep) affects these attributes in specific ways:
 * - Feeding decreases hunger and increases health
 * - Playing increases social interaction and health
 * - Sleeping decreases sleep need and increases health
 *
 * The pet also has:
 * - A personality that influences its behavior
 * - A mood that changes based on its states
 * - Preferred actions based on its current needs
 * - Health status monitoring
 * - Death condition checking
 * - Time tracking for interactions
 * 
 * @author Khushal
 * @version 1.0
 * 
 * @see PersonalityStrategy
 * @see Mood
 * @see Action
 * @see Pet
 * @see PetView
 * @see PetController
 * @see MainMenu
 * @see PetGame
 * @see PetModel
 * @see PetView
 *
 */
public interface PetModel {

  /**
   * Feed the pet, the hunger level will decrease by 10 and the health level will increase by 5.
   */
  public void feed();

  /**
   * Play with the pet, the social level will increase by 10 and the health level will increase by
   * 5.
   */
  public void play();

  /**
   * Put the pet to sleep, the sleep level will decrease by 10 and the health level will increase by
   * 5.
   */
  public void sleep();

  /**
     * Exercise the pet, increasing hunger and decreasing sleepiness but improving health.
     */
    public void exercise();

  /**
   * Get the current hunger level of the pet.
   * @return the current hunger level of the pet.
   */
  public int getHunger();


  /**
   * Get the current score of the pet.
   * @return the current score of the pet.
   */
  public int getscore();

  /**
   * Get the current social level of the pet.
   * @return the current social level of the pet.
   */
  public int getSocial();

  /**
   * Get the current sleep level of the pet.
   * @return the current sleep level of the pet.
   */
  public int getSleep();

  /**
   * Get the current health level of the pet.
   * @return the current health level of the pet.
   */
  public int getHealth();

  /**
   * Get the personality of the pet.
   * @return the personality of the pet.
   */
  public PersonalityStrategy getPersonality();

  /**
   * Display the attributes: hunger, clean, sleep, social, of the pet.
   * @return the attributes of the pet.
   */
  public String displayStates();

  /**
   * Get the mood of the pet.
   * @return the mood of the pet.
   */
  public Mood getMood();

  /**
   * Get the preferred action of the pet.
   * @return the preferred action of the pet.
   */
  public Action getPreferredAction();

  /**
   * Perform the preferred action of the pet.
   */
  public void performPreferredAction();

  /**
   * Check if the pet is dead.
   * @return the death status of the pet.
   */
  public boolean checkDeath();


  /**
   * Get the last interacted time of the pet.
   * @return the last interacted time of the pet.
   */
  long getLastInteractedTime();
}