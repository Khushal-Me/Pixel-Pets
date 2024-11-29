import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PetGameTest {

    @Test
    public void testMain() {
        // Since the main method initializes the application, we can test if the components are created and connected properly.
        Pet model = new Pet();
        PetView view = new PetView();
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(false);

        PetController controller = new PetController(model, view, mainMenu, true);

        view.setController(controller);
        view.setVisible(true);

        // Assertions to check if the components are not null and properly initialized
        assertNotNull(model, "Pet model should not be null");
        assertNotNull(view, "PetView should not be null");
        assertNotNull(mainMenu, "MainMenu should not be null");
        assertNotNull(controller, "PetController should not be null");

        // Assertions to check the visibility of the components
        assertFalse(mainMenu.isVisible(), "MainMenu should be initially hidden");
        assertTrue(view.isVisible(), "PetView should be visible");
    }
}