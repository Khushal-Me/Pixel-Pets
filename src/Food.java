// Food.java
public class Food extends Item {
    public Food(String name) {
        super(name);
    }

    @Override
    public void use(Pet pet) {
        pet.feed();
    }
}
