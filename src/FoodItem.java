public class FoodItem extends Item {
    private int fullness;

    public FoodItem(String name, int quantity, int fullness) {
        super(name, quantity);
        this.fullness = fullness;
    }

    // Might need to change later depending on how we implement the pet
    public void use(Pet pet){
        if (quantity > 0)  
        {
            quantity--;
            pet.increaseFullness(fullness);
            System.out.println("Used " + name + " on " + pet.getName() + " and increased fullness by " + fullness);
        }
        else 
        {
            System.out.println("You don't have any " + name + " left to use!");
        }
    }

    public int getFullness() {
        return fullness;
    }
    
}
