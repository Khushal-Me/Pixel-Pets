// Item.java
import java.io.Serializable;

/**
 * Abstract base class representing an item that can be used on a pet.
 * This class implements Serializable to allow for object persistence.
 * 
 * The Item class enforces implementation of a use method through its abstract nature,
 * while providing common functionality like storing and retrieving the item's name.
 * 
 * @author Ramje
 * @version 1.0
 * @param name The name of the item
 * @param pet The pet to use the item on
 */
public abstract class Item implements Serializable {
    private final String name;

    public Item(String name) {
        this.name = name;
    }

    public abstract void use(Pet pet);

    public String getName() {
        return name;
    }
}
