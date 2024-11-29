import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PetModelTest {

    private PetModel petModel;

    @BeforeEach
    public void setUp() {
        petModel = mock(PetModel.class);
    }

    @Test
    public void testFeed() {
        when(petModel.getHunger()).thenReturn(50);
        when(petModel.getHealth()).thenReturn(50);

        petModel.feed();

        verify(petModel).feed();
        assertEquals(40, petModel.getHunger());
        assertEquals(55, petModel.getHealth());
    }

    @Test
    public void testPlay() {
        when(petModel.getSocial()).thenReturn(50);
        when(petModel.getHealth()).thenReturn(50);

        petModel.play();

        verify(petModel).play();
        assertEquals(60, petModel.getSocial());
        assertEquals(55, petModel.getHealth());
    }

    @Test
    public void testSleep() {
        when(petModel.getSleep()).thenReturn(50);
        when(petModel.getHealth()).thenReturn(50);

        petModel.sleep();

        verify(petModel).sleep();
        assertEquals(40, petModel.getSleep());
        assertEquals(55, petModel.getHealth());
    }

    @Test
    public void testExercise() {
        when(petModel.getHunger()).thenReturn(50);
        when(petModel.getSleep()).thenReturn(50);
        when(petModel.getHealth()).thenReturn(50);

        petModel.exercise();

        verify(petModel).exercise();
        assertEquals(60, petModel.getHunger());
        assertEquals(40, petModel.getSleep());
        assertEquals(55, petModel.getHealth());
    }

    @Test
    public void testGetHunger() {
        when(petModel.getHunger()).thenReturn(50);
        assertEquals(50, petModel.getHunger());
    }

    @Test
    public void testGetScore() {
        when(petModel.getscore()).thenReturn(100);
        assertEquals(100, petModel.getscore());
    }

    @Test
    public void testGetSocial() {
        when(petModel.getSocial()).thenReturn(50);
        assertEquals(50, petModel.getSocial());
    }

    @Test
    public void testGetSleep() {
        when(petModel.getSleep()).thenReturn(50);
        assertEquals(50, petModel.getSleep());
    }

    @Test
    public void testGetHealth() {
        when(petModel.getHealth()).thenReturn(50);
        assertEquals(50, petModel.getHealth());
    }

    @Test
    public void testGetPersonality() {
        PersonalityStrategy personality = mock(PersonalityStrategy.class);
        when(petModel.getPersonality()).thenReturn(personality);
        assertEquals(personality, petModel.getPersonality());
    }

    @Test
    public void testDisplayStates() {
        when(petModel.displayStates()).thenReturn("Hunger: 50, Social: 50, Sleep: 50, Health: 50");
        assertEquals("Hunger: 50, Social: 50, Sleep: 50, Health: 50", petModel.displayStates());
    }

    @Test
    public void testGetMood() {
        Mood mood = mock(Mood.class);
        when(petModel.getMood()).thenReturn(mood);
        assertEquals(mood, petModel.getMood());
    }

    @Test
    public void testGetPreferredAction() {
        Action action = mock(Action.class);
        when(petModel.getPreferredAction()).thenReturn(action);
        assertEquals(action, petModel.getPreferredAction());
    }

    @Test
    public void testPerformPreferredAction() {
        petModel.performPreferredAction();
        verify(petModel).performPreferredAction();
    }

    @Test
    public void testCheckDeath() {
        when(petModel.checkDeath()).thenReturn(true);
        assertTrue(petModel.checkDeath());
    }

    @Test
    public void testGetLastInteractedTime() {
        long currentTime = System.currentTimeMillis();
        when(petModel.getLastInteractedTime()).thenReturn(currentTime);
        assertEquals(currentTime, petModel.getLastInteractedTime());
    }
}