// Inventory.java
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an inventory system that manages a collection of items.
 * This class implements Serializable to support object persistence.
 * 
 * <p>The Inventory class maintains a list of items and provides basic operations
 * to add and remove items from the inventory.</p>
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

    public Inventory() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }
}
