// Gift.java
public class Gift extends Item {
    public Gift(String name) {
        super(name);
    }

    @Override
    public void use(Pet pet) {
        pet.receiveGift();
    }
}
