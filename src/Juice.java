public class Juice extends Food {
    public Juice() {
        super("Juice");
    }

    @Override
    public void use(Pet pet) {
        pet.feedJuice();
    }
}
