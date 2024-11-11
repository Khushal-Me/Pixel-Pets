import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameController {
    private static GameController instance;
    private GameState currentState;
    private final List<GameEventListener> eventListeners = new ArrayList<>();
    private Timer gameTimer;
    private boolean isRunning;
    private static final long TICK_RATE = 100; // 100ms tick rate
    private static final Logger logger = Logger.getLogger(GameController.class.getName());

    private GameController() {
        gameTimer = new Timer("GameTimer", true);
        isRunning = false;
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void addEventListener(GameEventListener listener) {
        eventListeners.add(listener);
    }

    public void removeEventListener(GameEventListener listener) {
        eventListeners.remove(listener);
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void changeState(GameState newState) {
        GameState oldState = currentState;
        currentState = newState;
        notifyEventListeners("State changed from: " + 
            (oldState != null ? oldState.getClass().getSimpleName() : "null") +
            " to: " + newState.getClass().getSimpleName());
    }

    private void notifyEventListeners(String event) {
        for (GameEventListener listener : eventListeners) {
            listener.onEvent(event);
        }
    }

    public void startGameLoop() {
        if (!isRunning) {
            isRunning = true;
            gameTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    gameTick();
                }
            }, 0, TICK_RATE);
            notifyEventListeners("Game loop started");
        }
    }

    public void stopGameLoop() {
        if (isRunning) {
            isRunning = false;
            gameTimer.cancel();
            gameTimer = new Timer("GameTimer", true);
            notifyEventListeners("Game loop stopped");
        }
    }

    private void gameTick() {
        if (currentState != null) {
            try {
                currentState.execute();
                notifyEventListeners("Tick: " + System.currentTimeMillis());
            } catch (Exception e) {
                String errorMessage = "Error in game tick: " + e.getMessage();
                logger.log(Level.SEVERE, errorMessage, e);
                notifyEventListeners(errorMessage);
            }
        }
    }

    // Cleanup method for proper resource management
    public void cleanup() {
        stopGameLoop();
        eventListeners.clear();
        instance = null;
    }
}
