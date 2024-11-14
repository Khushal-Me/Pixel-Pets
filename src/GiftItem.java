public class GiftItem extends Item {
    private int happinessBoost;

    public GiftItem(String name, int quantity, int happinessBoost) {
        super(name, quantity);
        this.happinessBoost = happinessBoost;
    }

    // Might need to change later depending on how we implement the pet
    @Override
    public void use(){
        if(quantity > 0)
        {
            quantity--;
            pet.increaseHappiness(happinessBoost);
            System.out.println("Used " + name + " on " + pet.getName() + " and increased happiness by " + happinessBoost);
        }

        else 
        {
            System.out.println("You don't have any " + name + " left to use!");
        }

    }
    
    
}
