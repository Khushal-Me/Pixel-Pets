/**
 * Represents a Vegetables food item that can be fed to pets.
 * This class extends the Food class and provides specific implementation
 * for feeding vegetables to pets.
 * 
 * The Vegetables class initializes with a fixed name "Vegetables" and
 * implements the use method to feed vegetables to a pet.
 * 
 * @author Ramje
 * @version 1.0
 * 
 * @see Food
 */
public class Vegetables extends Food {
    public Vegetables() {
        super("Vegetables");
    }


    /**
     * Feeds the pet with vegetables.
     * 
     * @param pet The pet to feed vegetables.
     */
    @Override
    public void use(Pet pet) {
        pet.feedVegetables();
    }
}
