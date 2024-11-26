/**
 * The Food class represents a type of item that can be used to feed a pet.
 * It extends the Item class and overrides the use method to feed the pet.
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
