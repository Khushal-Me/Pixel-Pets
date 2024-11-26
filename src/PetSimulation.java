import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * A simulation class that demonstrates the functionality of a virtual pet.
 * This class creates a pet instance and performs various interactions with it,
 * such as feeding, playing, and sleeping. The simulation results are written
 * to a specified output file.
 * 
 * The simulation performs the following operations:
 * - Creates a new pet
 * - Executes basic pet interactions (feed, play, sleep)
 * - Displays the pet's current state
 * - Sets and performs a preferred action
 * - Checks and records the pet's health status and mood
 * 
 * The simulation output is written to a file specified by the filePath variable.
 * If an IO error occurs during file writing, an error message is printed to
 * standard error.
 * 
 */
public class PetSimulation {

  /**
   * Main method for the PetSimulation class.
   *
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    String filePath = "res/output.txt";

    try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
      // Set the input stream to 1
      System.setIn(new ByteArrayInputStream("1".getBytes()));

      // Create an instance of your Pet class
      Pet myPet = new Pet();

      // Interact with the pet
      myPet.feed();
      myPet.play();
      myPet.sleep();
      myPet.feed();

      // Display the pet's current state
      String petState = myPet.displayStates();
      writer.println("Current Pet State:\n" + petState);

      // Perform preferred action
      myPet.setPreferredAction(Action.SLEEP);
      myPet.performPreferredAction();

      // Check pet's health status and mood
      String healthStatus = myPet.checkPetHealthStatus();
      writer.println("Pet Health Status: " + healthStatus);

      Mood petMood = myPet.getMood();
      writer.println("Pet Mood: " + petMood);

      System.out.println("Simulation output written to: " + filePath);
    } catch (IOException e) {
      System.err.println("An error occurred while writing the simulation output: " + e.getMessage());
    }
  }
}