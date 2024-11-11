import java.util.logging.Level;
import java.util.logging.Logger;

public class GameControllerTest {
    public static void main(String[] args) {
        GameController controller = GameController.getInstance();
        
        // Add an event listener
        GameEventListener listener = event -> System.out.println("[EVENT] " + event);
        controller.addEventListener(listener);
        
        // Test state changes and start game loop
        controller.changeState(new MainMenuState());
        controller.startGameLoop();

        // Let the loop run for a few ticks
        try {
            Thread.sleep(2000);
            
            // Change state while running
            controller.changeState(new PlayState());
            Thread.sleep(2000);
            
            // Remove the event listener and change state
            controller.removeEventListener(listener);
            System.out.println("Listener removed. No more [EVENT] logs should appear.");
            controller.changeState(new MainMenuState());  // This change should not trigger any event logs

            // Stop game loop and cleanup
            controller.stopGameLoop();
            controller.cleanup();
            
        } catch (InterruptedException e) {
            Logger.getLogger(GameControllerTest.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
