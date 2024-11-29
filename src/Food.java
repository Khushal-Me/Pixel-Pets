/**
 * Represents a food item that can be consumed by a pet.
 * 
 * <p>
 * This class extends the {@link Item} class and provides functionality 
 * to feed a pet by calling its {@code feed} method.
 * </p>
 * 
 * @author Khushal
 * @version 1.0
 * @see Item
 * @see Pet
 */
public class Food extends Item {

    /**
     * Constructs a new {@code Food} item with the specified name.
     *
     * @param name the name of the food item
     */
    public Food(String name) {
        super(name);
    }

    /**
     * Feeds the given pet by invoking its {@code feed} method.
     *
     * @param pet the pet that will receive the food
     * @throws IllegalArgumentException if {@code pet} is {@code null}
     */
    @Override
    public void use(Pet pet) {
        if (pet == null) {
            throw new IllegalArgumentException("Pet cannot be null.");
        }
        pet.feed();
    }
}
