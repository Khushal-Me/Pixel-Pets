/**
 * Represents a gift item that can be given to a pet.
 * This class extends the Item class and provides specific implementation
 * for using gifts with pets.
 * 
 * @author Ramje
 * @version 1.0
 * @param name the name of the gift
 * @param pet the pet that will receive the gift
 * @see Item
 * @see Pet
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
