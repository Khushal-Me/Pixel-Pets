public class PetState extends GameState {
    private Pet pet;
    private long lastUpdateTime;

    public PetState(Pet pet) {
        this.pet = pet;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        long currentTime = System.currentTimeMillis();
        long millisElapsed = currentTime - lastUpdateTime;
        pet.update(millisElapsed);
        lastUpdateTime = currentTime;
    }

    // Getter for the pet, in case we need to access it
    public Pet getPet() {
        return pet;
    }
}
