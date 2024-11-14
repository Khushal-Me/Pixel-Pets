import java.util.Random;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class Pet {
    // Core Stats (0-100)
    private int hunger;
    private int happiness;
    private int health;
    private int energy;
    
    // Basic Info
    private final String name;
    private PersonalityType personality;
    private Mood currentMood;
    private Status status;
    private String currentSpritePath;
    private String currentAnimationPath;
    
    // Stat modification rates (per minute)
    private static final double BASE_HUNGER_DECAY = 0.5;  // -0.5% per minute
    private static final double BASE_HAPPINESS_DECAY = 0.3;
    private static final double BASE_HEALTH_DECAY = 0.1;
    private static final double BASE_ENERGY_DECAY = 0.4;
    
    // Personality traits affect stat changes
    public enum PersonalityType {
        PLAYFUL(1.2, 0.8, 1.0, 1.2),    // Modifier for: happiness, hunger, health, energy
        LAZY(0.8, 0.6, 0.9, 0.7),
        ENERGETIC(1.1, 1.2, 1.1, 1.0),
        FRIENDLY(1.2, 1.0, 1.0, 1.0),
        SHY(0.8, 0.8, 1.0, 0.9);
        
        private final double happinessModifier;
        private final double hungerModifier;
        private final double healthModifier;
        private final double energyModifier;
        
        PersonalityType(double happiness, double hunger, double health, double energy) {
            this.happinessModifier = happiness;
            this.hungerModifier = hunger;
            this.healthModifier = health;
            this.energyModifier = energy;
        }
    }
    
    public enum Mood {
        ECSTATIC, HAPPY, CONTENT, NEUTRAL, SAD, ANGRY, SICK, 
        STARVING, EXHAUSTED, SLEEPY, ENERGETIC, PLAYFUL
    }
    
    public enum Status {
        AWAKE, SLEEPING, PLAYING, EATING, SICK
    }
    
    // Constructor
    public Pet(String name) {
        this.name = name;
        this.personality = generateRandomPersonality();
        
        // Initialize stats at happy medium
        this.hunger = 70;  // 100 = full, 0 = starving
        this.happiness = 70;
        this.health = 100;
        this.energy = 100;
        
        this.status = Status.AWAKE;
        updateMood();
        
        // Default sprite and animation paths
        this.currentSpritePath = "sprites/default/" + personality.toString().toLowerCase() + "_normal.png";
        this.currentAnimationPath = "animations/default/" + personality.toString().toLowerCase() + "_idle.gif";
    }
    
    private PersonalityType generateRandomPersonality() {
        PersonalityType[] types = PersonalityType.values();
        return types[new Random().nextInt(types.length)];
    }
    
    // Core update method - call this regularly (e.g., every minute)
    public void update(long millisElapsed) {
        double minutesElapsed = millisElapsed / 60000.0;
        if (minutesElapsed > 0) {
            updateStats(minutesElapsed);
            updateMood();
            updateSprites();
        }
    }
    
    private void updateStats(double minutesElapsed) {
        if (status != Status.SLEEPING) {
            // Apply time-based decay
            modifyHunger(-BASE_HUNGER_DECAY * minutesElapsed * personality.hungerModifier);
            modifyHappiness(-BASE_HAPPINESS_DECAY * minutesElapsed * personality.happinessModifier);
            modifyHealth(-BASE_HEALTH_DECAY * minutesElapsed * personality.healthModifier);
            modifyEnergy(-BASE_ENERGY_DECAY * minutesElapsed * personality.energyModifier);
        } else {
            // Sleeping logic
            modifyEnergy(1.0 * minutesElapsed);  // Restore energy while sleeping
            modifyHunger(-BASE_HUNGER_DECAY * 0.5 * minutesElapsed);  // Get hungry slower while sleeping
        }
    }
    
    // Stat modification methods with bounds checking
    private void modifyHunger(double amount) {
        hunger = boundStat(hunger + amount);
    }
    
    private void modifyHappiness(double amount) {
        happiness = boundStat(happiness + amount);
    }
    
    private void modifyHealth(double amount) {
        health = boundStat(health + amount);
    }
    
    private void modifyEnergy(double amount) {
        energy = boundStat(energy + amount);
    }
    
    private int boundStat(double value) {
        return (int) Math.min(100, Math.max(0, value));
    }
    
    // Mood calculation based on stats
    private void updateMood() {
        Mood oldMood = currentMood;

        if (health < 30) {
            currentMood = Mood.SICK;
        } else if (hunger < 20) {
            currentMood = Mood.STARVING;
        } else if (energy < 20) {
            currentMood = Mood.EXHAUSTED;
        } else if (energy < 40) {
            currentMood = Mood.SLEEPY;
        } else if (happiness < 30) {
            currentMood = Mood.SAD;
        } else if (happiness < 50) {
            currentMood = Mood.NEUTRAL;
        } else if (happiness < 70) {
            currentMood = Mood.CONTENT;
        } else if (happiness < 90) {
            currentMood = Mood.HAPPY;
        } else {
            currentMood = Mood.ECSTATIC;
        }
        
        // Special moods based on combinations
        if (energy > 80 && happiness > 70) {
            currentMood = Mood.PLAYFUL;
        }

        if (currentMood != oldMood) {
            notifyEventListeners("Mood changed from " + oldMood + " to " + currentMood);
        }
    }
    
    // Sprite and animation management
    private void updateSprites() {
        String baseSpritePath = "sprites/" + personality.toString().toLowerCase() + "/";
        String baseAnimPath = "animations/" + personality.toString().toLowerCase() + "/";
        
        // Update paths based on mood and status
        switch (status) {
            case SLEEPING:
                currentSpritePath = baseSpritePath + "sleeping.png";
                currentAnimationPath = baseAnimPath + "sleeping.gif";
                break;
            case PLAYING:
                currentSpritePath = baseSpritePath + "playing.png";
                currentAnimationPath = baseAnimPath + "playing.gif";
                break;
            case EATING:
                currentSpritePath = baseSpritePath + "eating.png";
                currentAnimationPath = baseAnimPath + "eating.gif";
                break;
            default:
                // Based on mood
                currentSpritePath = baseSpritePath + currentMood.toString().toLowerCase() + ".png";
                currentAnimationPath = baseAnimPath + currentMood.toString().toLowerCase() + ".gif";
        }
    }
    
    // Action methods
    public void feed(FoodItem food) {
        if (status == Status.SLEEPING) return;
        
        status = Status.EATING;
        modifyHunger(food.getNutritionValue() * personality.hungerModifier);
        modifyHealth(food.getHealthValue() * personality.healthModifier);
        modifyHappiness(5 * personality.happinessModifier);  // Pets are happy when fed
        
        updateMood();
        updateSprites();
        status = Status.AWAKE;
    }
    
    public void play(PlayActivity activity) {
        if (status == Status.SLEEPING || energy < 20) return;
        
        status = Status.PLAYING;
        modifyHappiness(activity.getFunValue() * personality.happinessModifier);
        modifyEnergy(-activity.getEnergyDrain() * personality.energyModifier);
        modifyHunger(-activity.getHungerDrain() * personality.hungerModifier);
        
        updateMood();
        updateSprites();
        status = Status.AWAKE;
    }
    
    public void sleep() {
        if (status == Status.SLEEPING) return;
        status = Status.SLEEPING;
        updateSprites();
    }
    
    public void wakeUp() {
        if (status != Status.SLEEPING) return;
        status = Status.AWAKE;
        updateSprites();
    }
    
    public void takeToVet() {
        modifyHealth(50);  // Significant health boost
        modifyHappiness(-20);  // Pets usually don't like the vet
        updateMood();
        updateSprites();
    }
    
    // Getters
    public int getHunger() { return hunger; }
    public int getHappiness() { return happiness; }
    public int getHealth() { return health; }
    public int getEnergy() { return energy; }
    public Mood getCurrentMood() { return currentMood; }
    public Status getStatus() { return status; }
    public String getName() { return name; }
    public PersonalityType getPersonality() { return personality; }
    public String getCurrentSpritePath() { return currentSpritePath; }
    public String getCurrentAnimationPath() { return currentAnimationPath; }
    
    // Calculate overall needs priority (higher number = more urgent)
    public EnumMap<NeedType, Integer> calculateNeeds() {
        EnumMap<NeedType, Integer> needs = new EnumMap<>(NeedType.class);
        
        needs.put(NeedType.HUNGER, 100 - hunger);
        needs.put(NeedType.HAPPINESS, 100 - happiness);
        needs.put(NeedType.HEALTH, 100 - health);
        needs.put(NeedType.ENERGY, 100 - energy);
        
        return needs;
    }
    
    public enum NeedType {
        HUNGER, HAPPINESS, HEALTH, ENERGY
    }
    
    // Event listeners for pet events
    private List<GameEventListener> eventListeners = new ArrayList<>();

    public void addEventListener(GameEventListener listener) {
        eventListeners.add(listener);
    }

    public void removeEventListener(GameEventListener listener) {
        eventListeners.remove(listener);
    }

    private void notifyEventListeners(String event) {
        for (GameEventListener listener : eventListeners) {
            listener.onEvent(event);
        }
    }
}
