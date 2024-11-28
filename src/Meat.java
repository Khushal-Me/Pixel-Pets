public class Meat extends Food {
    public Meat() {
        super("Meat");
    }

    @Override
    public void use(Pet pet) {
        pet.feedMeat();
    }
}
