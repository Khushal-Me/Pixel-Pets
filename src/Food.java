/**
 * <p> Description: </p>
 * Represents a food item that can be consumed by a pet.
 * This class extends the Item class and implements functionality
 * for feeding pets.
 * 
 * @author  Khushal
 * @version 1.0
 * @param name the name of the food
 * @param pet the pet that will receive the food
 */
public class Food extends Item {
    public Food(String name) {
        super(name);
    }

    @Override
    public void use(Pet pet) {
        pet.feed();
    }
}
