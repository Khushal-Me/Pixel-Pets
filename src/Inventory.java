import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an inventory system that manages a collection of items.
 * This class implements {@link Serializable} to support object persistence,
 * allowing the inventory to be saved and loaded between sessions.
 * 
 * <p>The {@link Inventory} class maintains a list of {@link Item} objects 
 * and provides basic operations such as adding, removing, and retrieving 
 * items from the inventory.</p>
 * 
 * <p>Usage Example:</p>
 * <pre>
 * {@code
 * Inventory inventory = new Inventory();
 * Item item = new Item("Food");
 * inventory.addItem(item);
 * inventory.removeItem(item);
 * List<Item> items = inventory.getItems();
 * }
 * </pre>
 * 
 * @author Ramje
 * @version 1.0
 * @see Item
 * @see Serializable
 * @see List
 */
public class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<Item> items;

    /**
     * Constructs an empty inventory.
     * Initializes the list of items that the inventory will manage.
     */
    public Inventory() {
        items = new ArrayList<>();
    }

    /**
     * Adds an {@link Item} to the inventory.
     * 
     * @param item the item to be added to the inventory
     * @throws NullPointerException if the item is {@code null}
     */
    public void addItem(Item item) {
        if (item == null) {
            throw new NullPointerException("Item cannot be null");
        }
        items.add(item);
    }

    /**
     * Removes an {@link Item} from the inventory.
     * 
     * @param item the item to be removed from the inventory
     * @throws NullPointerException if the item is {@code null}
     */
    public void removeItem(Item item) {
        if (item == null) {
            throw new NullPointerException("Item cannot be null");
        }
        items.remove(item);
    }

    /**
     * Returns the list of all items in the inventory.
     * 
     * @return a list of items currently in the inventory
     */
    public List<Item> getItems() {
        return items;
    }
}
