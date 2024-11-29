/**
 * The {@link Juice} class represents a juice consumable that extends the {@link Food} class.
 * This class provides functionality for pets to consume juice as a beverage.
 * 
 * @author Ramje
 * @version 1.0
 * @see Food
 * @see Pet
 */
public class Juice extends Food {
    /**
     * Constructs a {@link Juice} instance with the name "Juice".
     */
    public Juice() {
        super("Juice");
    }

    /**
     * Uses the juice on the specified pet by feeding it juice.
     * 
     * @param pet the pet that will consume the juice
     */
    @Override
    public void use(Pet pet) {
        pet.feedJuice();
    }
}
