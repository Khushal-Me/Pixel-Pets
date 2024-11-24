/**
 * The Class PetGame.
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