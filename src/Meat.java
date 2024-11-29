/**
 * The Meat class represents a type of food that can be given to pets.
 * It extends the {@link Food} class and provides specific behavior for feeding meat to pets.
 * This class overrides the {@link Food#use(Pet)} method to feed meat to a given pet, 
 * invoking the pet's behavior associated with eating meat.
 * 
 * <p>Instances of the Meat class are used when feeding a pet meat-based food, 
 * altering the pet's state or attributes according to the pet's feeding logic.</p>
 * 
 * @author Ramje
 * @version 1.0
 * @see Food
 * @see Pet
 */
public class Meat extends Food {
    
    /**
     * Constructs a Meat object with the default name "Meat".
     */
    public Meat() {
        super("Meat");
    }

    /**
     * Feeds the provided pet meat.
     * This method calls the {@code feedMeat} method on the given pet to apply the specific effect 
     * of feeding meat to the pet.
     *
     * @param pet The pet that is being fed the meat. This cannot be {@code null}.
     * @see Pet#feedMeat()
     */
    @Override
    public void use(Pet pet) {
        pet.feedMeat();
    }
}
