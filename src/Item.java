// Item.java
import java.io.Serializable;

public abstract class Item implements Serializable {
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public abstract void use(Pet pet);

    public String getName() {
        return name;
    }
}
