public class Vegetables extends Food {
    public Vegetables() {
        super("Vegetables");
    }

    @Override
    public void use(Pet pet) {
        pet.feedVegetables();
    }
}
