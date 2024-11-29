/**
 * The Juice class represents a juice consumable that extends the Food class.
 * This class provides functionality for pets to consume juice as a beverage.
 * 
 * @author Ramje
 * @see Food
 * @see Pet
 * @param pet The pet that will consume the juice
 */
public class Juice extends Food {
    public Juice() {
        super("Juice");
    }

    @Override
    public void use(Pet pet) {
        pet.feedJuice();
    }
}
