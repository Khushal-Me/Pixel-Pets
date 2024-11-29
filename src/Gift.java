/**
 * Represents a gift item that can be given to a pet.
 * 
 * <p>
 * This class extends the {@link Item} class and provides a specific 
 * implementation for giving gifts to pets by invoking their 
 * {@code receiveGift} method.
 * </p>
 * 
 * @author Ramje
 * @version 1.0
 * @see Item
 * @see Pet
 */
public class Gift extends Item {

    /**
     * Constructs a new {@code Gift} item with the specified name.
     *
     * @param name the name of the gift item
     */
    public Gift(String name) {
        super(name);
    }

    /**
     * Gives the specified pet a gift by invoking its {@code receiveGift} method.
     *
     * @param pet the pet that will receive the gift
     * @throws IllegalArgumentException if {@code pet} is {@code null}
     */
    @Override
    public void use(Pet pet) {
        if (pet == null) {
            throw new IllegalArgumentException("Pet cannot be null.");
        }
        pet.receiveGift();
    }
}
