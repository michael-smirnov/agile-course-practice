package ru.unn.agile.Deque.viewmodel;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.Deque.model.Deque;
import ru.unn.agile.Deque.viewmodel.DequeViewModel.Action;

import java.util.List;

import static org.junit.Assert.*;

public class DequeViewModelTest {
    private DequeViewModel viewModel;

    public void setViewModel(final DequeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void initializeDequeViewModel() {
        IDequeLogger logger = new FakeDequeLogger();
        setViewModel(new DequeViewModel(logger));
    }

    @Test
    public void canCreateViewModelWithLogger() {
        assertNotNull(viewModel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void canNotCreateViewModelWithNull() {
        DequeViewModel viewModel = new DequeViewModel(null);
    }

    @Test
    public void byDefaultPushActionIsDisabled() {
        assertFalse(viewModel.isPushActionEnabled());
    }

    @Test
    public void byDefaultPopActionIsDisabled() {
        assertFalse(viewModel.isPopActionEnabled());
    }

    @Test
    public void byDefaultClearActionIsDisabled() {
        assertFalse(viewModel.isClearActionEnabled());
    }

    @Test
    public void byDefaultCheckActionIsDisabled() {
        assertFalse(viewModel.isContainsActionEnabled());
    }

    @Test
    public void byDefaultLogIsEmpty() {
        List<String> log = viewModel.getLog();

        assertTrue(log.isEmpty());
    }

    @Test
    public void whenEnteredNumberPushActionIsEnabled() {
        viewModel.setInputNumber("4");

        assertTrue(viewModel.isPushActionEnabled());
    }

    @Test
    public void whenDequeIsNotEmptyPopActionIsEnabled() {
        viewModel.setInputNumber("15");
        viewModel.setAction(Action.PushFront);
        viewModel.doAction();

        assertTrue(viewModel.isPopActionEnabled());
    }

    @Test
    public void whenClearedNumberPushActionIsDisabled() {
        viewModel.setInputNumber("23");
        viewModel.setInputNumber("");

        assertFalse(viewModel.isPushActionEnabled());
    }

    @Test
    public void whenEnteredNotNumberPushActionIsDisabled() {
        viewModel.setInputNumber("1a");

        assertFalse(viewModel.isPushActionEnabled());
    }

    @Test
    public void whenDequeIsEmptyPopActionIsDisabled() {
        viewModel.setInputNumber("4");
        viewModel.setAction(Action.PushFront);
        viewModel.doAction();
        viewModel.setAction(Action.PopFront);
        viewModel.doAction();

        assertFalse(viewModel.isPopActionEnabled());
    }

    @Test
    public void pushFrontAddsIntegersToDequeFront() {
        viewModel.setInputNumber("15");
        viewModel.setAction(Action.PushFront);
        viewModel.doAction();
        viewModel.setInputNumber("16");
        viewModel.doAction();

        Deque<Integer> testDeque = new Deque<>();
        testDeque.pushFront(15);
        testDeque.pushFront(16);

        assertArrayEquals(viewModel.getAction().toArray(), testDeque.toArray());
    }

    @Test
    public void pushBackAddsIntegersToDequeBack() {
        viewModel.setInputNumber("23");
        viewModel.setAction(Action.PushBack);
        viewModel.doAction();
        viewModel.setInputNumber("42");
        viewModel.doAction();

        Deque<Integer> testDeque = new Deque<>();
        testDeque.pushBack(23);
        testDeque.pushBack(42);

        assertArrayEquals(viewModel.getAction().toArray(), testDeque.toArray());
    }

    @Test
     public void popBackReturnsBackInteger() {
        viewModel.setInputNumber("4");
        viewModel.setAction(Action.PushBack);
        viewModel.doAction();
        viewModel.setInputNumber("8");
        viewModel.doAction();

        viewModel.setAction(Action.PopBack);
        viewModel.doAction();

        assertEquals("8", viewModel.getOutput());
    }

    @Test
    public void popFrontReturnsFrontInteger() {
        viewModel.setInputNumber("15");
        viewModel.setAction(Action.PushFront);
        viewModel.doAction();
        viewModel.setInputNumber("16");
        viewModel.doAction();

        viewModel.setAction(Action.PopFront);
        viewModel.doAction();

        assertEquals("16", viewModel.getOutput());
    }

    @Test
    public void clearActionClearsDeque() {
        viewModel.setInputNumber("4");
        viewModel.setAction(Action.PushFront);
        viewModel.doAction();

        viewModel.setAction(Action.Clear);
        viewModel.doAction();

        assertTrue(viewModel.getAction().isEmpty());
    }

    @Test
    public void whenDequeIsClearedClearActionIsDisabled() {
        viewModel.setAction(Action.Clear);
        viewModel.doAction();

        assertFalse(viewModel.isClearActionEnabled());
    }

    @Test
    public void whenDequeIsEmptyAfterPopClearActionIsDisabled() {
        viewModel.setInputNumber("15162342");
        viewModel.setAction(Action.PushFront);
        viewModel.doAction();

        viewModel.setAction(Action.PopFront);
        viewModel.doAction();

        assertFalse(viewModel.isClearActionEnabled());
    }

    @Test
    public void whenDequeIsClearedPopActionIsDisabled() {
        viewModel.setInputNumber("4");
        viewModel.setAction(Action.PushFront);
        viewModel.doAction();

        viewModel.setAction(Action.Clear);
        viewModel.doAction();

        assertFalse(viewModel.isPopActionEnabled());
    }

    @Test
    public void dequeContains4() {
        viewModel.setInputNumber("4");
        viewModel.setAction(Action.PushFront);
        viewModel.doAction();

        viewModel.setAction(Action.Contains);
        viewModel.doAction();

        assertEquals("true", viewModel.getOutput());
    }

    @Test
    public void dequeDoesNotContain8() {
        viewModel.setInputNumber("15");
        viewModel.setAction(Action.PushFront);
        viewModel.doAction();
        viewModel.setInputNumber("8");

        viewModel.setAction(Action.Contains);
        viewModel.doAction();

        assertEquals("false", viewModel.getOutput());
    }

    @Test
    public void whenDequeIsClearedCheckActionIsDisabled() {
        viewModel.setInputNumber("4");
        viewModel.setAction(Action.PushFront);
        viewModel.doAction();

        viewModel.setAction(Action.Clear);
        viewModel.doAction();

        assertFalse(viewModel.isContainsActionEnabled());
    }

    @Test
    public void whenDequeIsEmptyAfterPopCheckActionIsDisabled() {
        viewModel.setInputNumber("15162342");
        viewModel.setAction(Action.PushFront);
        viewModel.doAction();

        viewModel.setAction(Action.PopFront);
        viewModel.doAction();

        assertFalse(viewModel.isContainsActionEnabled());
    }

    @Test
    public void dequeSizeIs3() {
        viewModel.setInputNumber("4");
        viewModel.setAction(Action.PushFront);
        viewModel.doAction();
        viewModel.setInputNumber("8");
        viewModel.doAction();
        viewModel.setInputNumber("15");
        viewModel.doAction();

        assertEquals(3, viewModel.getAction().getSize());
    }

    @Test
    public void byDefaultDequeIsEmpty() {
        assertTrue(viewModel.getAction().isEmpty());
    }

    @Test
    public void byDefaultDoActionButtonIsDisabled() {
        assertFalse(viewModel.isDoActionButtonEnabled());
    }

    @Test
    public void whenPushBackIsSelectedActionIsPushBack() {
        viewModel.setAction(Action.PushBack);

        assertEquals(Action.PushBack, viewModel.getAction());
    }

    @Test
    public void whenPopFrontIsSelectedActionIsPopFront() {
        viewModel.setAction(Action.PopFront);

        assertEquals(Action.PopFront, viewModel.getAction());
    }

    @Test
    public void whenPopBackIsSelectedActionIsPopBack() {
        viewModel.setAction(Action.PopBack);

        assertEquals(Action.PopBack, viewModel.getAction());
    }

    @Test
    public void whenClearIsSelectedActionIsClear() {
        viewModel.setAction(Action.Clear);

        assertEquals(Action.Clear, viewModel.getAction());
    }

    @Test
    public void whenCheckIsSelectedActionIsCheck() {
        viewModel.setAction(Action.Contains);

        assertEquals(Action.Contains, viewModel.getAction());
    }

    @Test
    public void whenActionIndexIs1ActionIsPushBack() {
        viewModel.setAction(1);

        assertEquals(Action.PushBack, viewModel.getAction());
    }

    @Test
    public void whenActionIndexIs3ActionIsPopBack() {
        viewModel.setAction(3);

        assertEquals(Action.PopBack, viewModel.getAction());
    }

    @Test
    public void doesPushFrontActionAddSomethingInLog() {
        viewModel.setAction(Action.PushFront);
        viewModel.setInputNumber("8");

        viewModel.doAction();

        assertFalse(viewModel.getLog().isEmpty());
    }

    @Test
    public void pushFrontActionAddsCorrectMessageInLog() {
        viewModel.setAction(Action.PushFront);
        viewModel.setInputNumber("8");

        viewModel.doAction();
        String actualMessage = viewModel.getLog().get(viewModel.getLog().size() - 1);

        assertTrue(actualMessage.matches(".*" + DequeViewModel.LogMessages.ACTION_PERFORMED
                + viewModel.getAction().toString() + ".*"));
    }

    @Test
    public void settingActionAddsSomethingInLog() {
        viewModel.setAction(Action.PopBack);

        assertFalse(viewModel.getLog().isEmpty());
    }

    @Test
    public void settingActionAddsCorrectMessageInLog() {
        viewModel.setAction(Action.Clear);

        String actualMessage = viewModel.getLog().get(viewModel.getLog().size() - 1);

        assertTrue(actualMessage.matches(".*" + DequeViewModel.LogMessages.ACTION_CHANGED
                + viewModel.getAction().toString() + ".*"));
    }

    @Test
    public void settingNumberAddsCorrectMessageInLog() {
        String testNumber = "8";
        viewModel.setInputNumber(testNumber);
        viewModel.logUpdatedInput();

        String actualMessage = viewModel.getLog().get(viewModel.getLog().size() - 1);

        assertTrue(actualMessage.matches(".*" + DequeViewModel.LogMessages.NUMBER_ENTERED
                + testNumber + ".*"));
    }

    @Test
    public void settingNaNAddsCorrectMessageInLog() {
        String testNaN = "asd";
        viewModel.setInputNumber(testNaN);
        viewModel.logUpdatedInput();

        String actualMessage = viewModel.getLog().get(viewModel.getLog().size() - 1);

        assertTrue(actualMessage.matches(".*" + DequeViewModel.LogMessages.NAN_ENTERED
                + testNaN + ".*"));
    }
}
