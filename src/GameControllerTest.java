import java.util.logging.Level;
import java.util.logging.Logger;

public class GameControllerTest {
    public static void main(String[] args) {
        GameController controller = GameController.getInstance();
        
        // Add event listener
        controller.addEventListener(event -> System.out.println("[EVENT] " + event));
        
        // Test state changes
        controller.changeState(new MainMenuState());
        
        // Start game loop
        controller.startGameLoop();
        
        // Let it run for a few seconds
        try {
            Thread.sleep(2000);
            
            // Change state while running
            controller.changeState(new PlayState());
            Thread.sleep(2000);
            
            // Stop game loop
            controller.stopGameLoop();
            
            // Cleanup
            controller.cleanup();
            
        } catch (InterruptedException e) {
            Logger.getLogger(GameControllerTest.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}