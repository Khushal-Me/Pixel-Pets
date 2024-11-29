import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PetViewTest {

    private PetView petView;
    private PetController mockController;

    @BeforeEach
    public void setUp() {
        petView = new PetView();
        mockController = mock(PetController.class);
        petView.setController(mockController);
    }

    @Test
    public void testUpdateHealth() {
        petView.updateHealth(80);
        assertEquals(80, petView.healthBar.getValue());
        assertEquals("Health: 80", petView.healthBar.getString());
    }

    @Test
    public void testUpdateHunger() {
        petView.updateHunger(50);
        assertEquals(50, petView.hungerBar.getValue());
        assertEquals("Hunger: 50", petView.hungerBar.getString());
    }

    @Test
    public void testUpdateSocial() {
        petView.updateSocial(70);
        assertEquals(70, petView.socialBar.getValue());
        assertEquals("Social: 70", petView.socialBar.getString());
    }

    @Test
    public void testUpdateSleep() {
        petView.updateSleep(90);
        assertEquals(90, petView.sleepBar.getValue());
        assertEquals("Sleep: 90", petView.sleepBar.getString());
    }

    @Test
    public void testUpdateMood() {
        petView.updateMood("Happy");
        assertEquals("Mood: Happy", petView.moodLabel.getText());
    }

    @Test
    public void testUpdatePersonality() {
        petView.updatePersonality("Dog");
        assertEquals("Pixel Pet: Dog", petView.personalityLabel.getText());
    }

    @Test
    public void testToggleAttributesVisibility() {
        petView.toggleAttributesVisibility(true);
        assertTrue(petView.moodLabel.isVisible());
        assertTrue(petView.personalityLabel.isVisible());

        petView.toggleAttributesVisibility(false);
        assertFalse(petView.moodLabel.isVisible());
        assertFalse(petView.personalityLabel.isVisible());
    }

    @Test
    public void testToggleButtonsVisibility() {
        petView.toggleButtonsVisibility(true);
        assertTrue(petView.feedButton.isVisible());
        assertTrue(petView.playButton.isVisible());
        assertTrue(petView.sleepButton.isVisible());
        assertTrue(petView.exerciseButton.isVisible());
        assertTrue(petView.getPreferredActionButton.isVisible());
        assertTrue(petView.performPreferredActionButton.isVisible());

        petView.toggleButtonsVisibility(false);
        assertFalse(petView.feedButton.isVisible());
        assertFalse(petView.playButton.isVisible());
        assertFalse(petView.sleepButton.isVisible());
        assertFalse(petView.exerciseButton.isVisible());
        assertFalse(petView.getPreferredActionButton.isVisible());
        assertFalse(petView.performPreferredActionButton.isVisible());
    }

    @Test
    public void testAddFeedListener() {
        ActionListener listener = mock(ActionListener.class);
        petView.addFeedListener(listener);
        for (ActionListener al : petView.feedButton.getActionListeners()) {
            al.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
        }
        verify(listener, times(1)).actionPerformed(any(ActionEvent.class));
    }

    @Test
    public void testAddPlayListener() {
        ActionListener listener = mock(ActionListener.class);
        petView.addPlayListener(listener);
        for (ActionListener al : petView.playButton.getActionListeners()) {
            al.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
        }
        verify(listener, times(1)).actionPerformed(any(ActionEvent.class));
    }

    @Test
    public void testAddSleepListener() {
        ActionListener listener = mock(ActionListener.class);
        petView.addSleepListener(listener);
        for (ActionListener al : petView.sleepButton.getActionListeners()) {
            al.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
        }
        verify(listener, times(1)).actionPerformed(any(ActionEvent.class));
    }

    @Test
    public void testAddUseItemListener() {
        ActionListener listener = mock(ActionListener.class);
        petView.addUseItemListener(listener);
        for (ActionListener al : petView.useItemButton.getActionListeners()) {
            al.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
        }
        verify(listener, times(1)).actionPerformed(any(ActionEvent.class));
    }

    @Test
    public void testAddVetButtonListener() {
        ActionListener listener = mock(ActionListener.class);
        petView.addVetButtonListener(listener);
        for (ActionListener al : petView.vetButton.getActionListeners()) {
            al.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
        }
        verify(listener, times(1)).actionPerformed(any(ActionEvent.class));
    }

    @Test
    public void testUpdateInventory() {
        List<Item> items = List.of(new Item("Bone"), new Item("Ball"));
        petView.updateInventory(items);
        assertEquals(2, petView.inventoryListModel.size());
        assertEquals("Bone", petView.inventoryListModel.getElementAt(0));
        assertEquals("Ball", petView.inventoryListModel.getElementAt(1));
    }

    @Test
    public void testGetSelectedItem() {
        List<Item> items = List.of(new Item("Bone"), new Item("Ball"));
        petView.updateInventory(items);
        petView.inventoryList.setSelectedIndex(1);
        Item selectedItem = petView.getSelectedItem(items);
        assertNotNull(selectedItem);
        assertEquals("Ball", selectedItem.getName());
    }

    @Test
    public void testSetActionButtonsEnabled() {
        petView.setActionButtonsEnabled(false);
        assertFalse(petView.feedButton.isEnabled());
        assertFalse(petView.playButton.isEnabled());
        assertFalse(petView.sleepButton.isEnabled());
        assertFalse(petView.getPreferredActionButton.isEnabled());
        assertFalse(petView.performPreferredActionButton.isEnabled());

        petView.setActionButtonsEnabled(true);
        assertTrue(petView.feedButton.isEnabled());
        assertTrue(petView.playButton.isEnabled());
        assertTrue(petView.sleepButton.isEnabled());
        assertTrue(petView.getPreferredActionButton.isEnabled());
        assertTrue(petView.performPreferredActionButton.isEnabled());
    }
}