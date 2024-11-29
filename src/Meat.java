/**
 * The Meat class represents a type of food that can be given to pets.
 * It extends the Food class and implements specific behavior for feeding meat to pets.
 * 
 * @author Ramje
 * @version 1.0
 * @see Food
 * @param pet The pet that is being fed the meat.
 */
public class Meat extends Food {
    public Meat() {
        super("Meat");
    }

    @Override
    public void use(Pet pet) {
        pet.feedMeat();
    }
}
