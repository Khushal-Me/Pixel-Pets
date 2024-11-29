import java.io.Serializable;

/**
 * Abstract base class representing an item that can be used on a pet.
 * This class implements {@link Serializable} to allow for object persistence.
 * 
 * <p>The {@link Item} class enforces the implementation of the {@link #use(Pet)} method 
 * through its abstract nature, while providing common functionality such as storing and 
 * retrieving the item's name.</p>
 * 
 * @author Ramje
 * @version 1.0
 */
public abstract class Item implements Serializable {
    private final String name;

    /**
     * Constructs an {@link Item} with the specified name.
     * 
     * @param name the name of the item
     */
    public Item(String name) {
        this.name = name;
    }

    /**
     * Abstract method to be implemented by subclasses. Defines the behavior of the item 
     * when used on a pet.
     * 
     * @param pet the pet to use the item on
     */
    public abstract void use(Pet pet);

    /**
     * Returns the name of the item.
     * 
     * @return the name of the item
     */
    public String getName() {
        return name;
    }
}
