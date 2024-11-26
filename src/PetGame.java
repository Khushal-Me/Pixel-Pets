/**
 * Main class that initializes and runs the Pet Game application.
 * This class follows the MVC (Model-View-Controller) pattern by creating
 * and connecting the Pet (model), PetView (view), and PetController (controller)
 * components.
 * 
 * The application starts with creating instances of the model, view, and menu,
 * then sets up the controller to manage interactions between these components.
 * The main menu is initially hidden, while the main view is displayed to the user.
 */
public class PetGame {

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    Pet model = new Pet();
    PetView view = new PetView();
    MainMenu mainMenu = new MainMenu();
    mainMenu.setVisible(false); // We don't need to show it immediately

    PetController controller = new PetController(model, view, mainMenu, true);

    view.setController(controller);
    view.setVisible(true);
  }
}