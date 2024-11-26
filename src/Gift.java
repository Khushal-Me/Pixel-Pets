/**
 * Represents a gift item that can be given to a pet.
 */
 
/**
 * Constructs a new `Gift` with the specified name.
 *
 * @param name the name of the gift
 */
 
/**
 * Uses the gift on the specified pet, causing the pet to receive the gift.
 *
 * @param pet the pet that will receive the gift
 */
public class Gift extends Item {
    public Gift(String name) {
        super(name);
    }

    @Override
    public void use(Pet pet) {
        pet.receiveGift();
    }
}
