import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import javax.swing.*;
import java.util.concurrent.ScheduledExecutorService;
import static org.mockito.Mockito.*;

public class PetControllerTest {

    private Pet model;
    private PetView view;
    private MainMenu mainMenu;
    private PetController controller;
    private ScheduledExecutorService executorService;

    @BeforeEach
    public void setUp() {
        model = mock(Pet.class);
        view = mock(PetView.class);
        mainMenu = mock(MainMenu.class);
        executorService = mock(ScheduledExecutorService.class);
        controller = new PetController(model, view, mainMenu, true);
    }

    @Test
    public void testHandleFeedAction() {
        controller.handleFeedAction();
        verify(model).feed();
        verify(view).updateScore(anyInt());
        verify(view).updateHealth(anyInt());
        verify(view).updateHunger(anyInt());
        verify(view).updateSocial(anyInt());
        verify(view).updateSleep(anyInt());
        verify(view).updateMood(anyString());
        verify(view).appendMessage(anyString());
    }

    @Test
    public void testHandlePlayAction() {
        controller.handlePlayAction();
        verify(model).play();
        verify(view).updateScore(anyInt());
        verify(view).updateHealth(anyInt());
        verify(view).updateHunger(anyInt());
        verify(view).updateSocial(anyInt());
        verify(view).updateSleep(anyInt());
        verify(view).updateMood(anyString());
        verify(view).appendMessage(anyString());
    }

    @Test
    public void testHandleSleepAction() {
        controller.handleSleepAction();
        verify(model).sleep();
        verify(view).updateScore(anyInt());
        verify(view).updateHealth(anyInt());
        verify(view).updateHunger(anyInt());
        verify(view).updateSocial(anyInt());
        verify(view).updateSleep(anyInt());
        verify(view).updateMood(anyString());
        verify(view).appendMessage(anyString());
    }

    @Test
    public void testHandleVetAction() {
        controller.handleVetAction();
        verify(model).increaseHealth(30);
        verify(view).updateScore(anyInt());
        verify(view).updateHealth(anyInt());
        verify(view).updateHunger(anyInt());
        verify(view).updateSocial(anyInt());
        verify(view).updateSleep(anyInt());
        verify(view).updateMood(anyString());
        verify(view).appendMessage("You took your pet to the vet. Health increased by 30.");
    }

    @Test
    public void testHandleUseItem() {
        Item item = mock(Item.class);
        when(view.getSelectedItem(anyList())).thenReturn(item);
        controller.handleUseItem();
        verify(item).use(model);
        verify(model.getInventory()).removeItem(item);
        verify(view).updateInventory(anyList());
        verify(view).updateScore(anyInt());
        verify(view).updateHealth(anyInt());
        verify(view).updateHunger(anyInt());
        verify(view).updateSocial(anyInt());
        verify(view).updateSleep(anyInt());
        verify(view).updateMood(anyString());
    }

    @Test
    public void testHandleRevivePet() {
        when(JOptionPane.showInputDialog(any(), anyString(), anyString(), anyInt())).thenReturn("CS2212A");
        controller.handleRevivePet();
        verify(model).revive();
        verify(view).setActionButtonsEnabled(true);
        verify(view).updatePetImage(anyString(), eq(false), eq(false));
        verify(view).appendMessage("Your pet has been revived!");
    }

    @Test
    public void testHandleSaveAction() {
        when(JOptionPane.showInputDialog(anyString())).thenReturn("1");
        controller.handleSaveAction();
        verify(model).save("1");
        verify(view).appendMessage("Game saved successfully.");
    }

    @Test
    public void testHandleBackToMainMenu() {
        when(JOptionPane.showInputDialog(anyString())).thenReturn("1");
        controller.handleBackToMainMenu();
        verify(model).save("1");
        verify(model).stopTimers();
        verify(executorService).shutdownNow();
        verify(view).dispose();
        verify(mainMenu).incrementTotalPlayTime(anyInt());
        verify(mainMenu).setVisible(true);
    }

    @Test
    public void testHandlePetDeath() {
        when(JOptionPane.showOptionDialog(any(), anyString(), anyString(), anyInt(), anyInt(), any(), any(), any())).thenReturn(JOptionPane.YES_OPTION);
        controller.handlePetDeath();
        verify(view).updatePetImage(anyString(), eq(true), eq(false));
        verify(view).setActionButtonsEnabled(false);
        verify(view).appendMessage("Game Over");
    }
}