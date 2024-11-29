import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

// InventoryTest.java


public class InventoryTest {
    private Inventory inventory;
    private Item item1;
    private Item item2;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        item1 = new Item("Item1", 10.0);
        item2 = new Item("Item2", 20.0);
    }

    @Test
    public void testAddItem() {
        inventory.addItem(item1);
        List<Item> items = inventory.getItems();
        assertEquals(1, items.size());
        assertTrue(items.contains(item1));
    }

    @Test
    public void testRemoveItem() {
        inventory.addItem(item1);
        inventory.removeItem(item1);
        List<Item> items = inventory.getItems();
        assertEquals(0, items.size());
        assertFalse(items.contains(item1));
    }

    @Test
    public void testGetItems() {
        inventory.addItem(item1);
        inventory.addItem(item2);
        List<Item> items = inventory.getItems();
        assertEquals(2, items.size());
        assertTrue(items.contains(item1));
        assertTrue(items.contains(item2));
    }
}