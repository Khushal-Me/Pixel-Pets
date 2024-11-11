public abstract class GameState {
    public abstract void execute();
}

class MainMenuState extends GameState {
    @Override
    public void execute() {
        System.out.println("In Main Menu");
    }
}

class PlayState extends GameState {
    @Override
    public void execute() {
        System.out.println("Playing Game");
    }
}
