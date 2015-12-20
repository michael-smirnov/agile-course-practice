package ru.unn.agile.Minesweeper.viewmodel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ViewModelTest extends ViewModel {

    private ViewModel minesweeper;
    @Before
    public void before() {
        minesweeper = new ViewModel();
    }

    @Test
    public void defaultTextSmileIsSmileText() {
        assertEquals(minesweeper.getSmileText(), minesweeper.getTextSmile());
    }

    @Test
    public void whenWinSmileTextIsWinnerText() {
        minesweeper.boardClear();
        minesweeper.openCell(0, 0);
        assertEquals(minesweeper.getWinnerText(), minesweeper.getTextSmile());
    }

    @Test
    public void whenLostSmileTextIsDeadText() {
        minesweeper.setMine(0, 0);
        minesweeper.openCell(0, 0);
        assertEquals(minesweeper.getDeadText(), minesweeper.getTextSmile());
    }

    @Test
    public void defaultCellTextIsCloseText() {
        assertEquals(minesweeper.getCloseText(), minesweeper.getCellText(0, 0));
    }

    @Test
    public void whenOnceMarkCellCellTextIsFlagText() {
        minesweeper.markCell(0, 0);
        assertEquals(minesweeper.getFlagText(), minesweeper.getCellText(0, 0));
    }

    @Test
    public void whenTwiceMarkedCellCellTextIsIssueText() {
        minesweeper.markCell(0, 0);
        minesweeper.markCell(0, 0);
        assertEquals(minesweeper.getIssueText(), minesweeper.getCellText(0, 0));
    }

    @Test
    public void whenThreesMarkedCellCellTextIsCloseText() {
        minesweeper.markCell(0, 0);
        minesweeper.markCell(0, 0);
        minesweeper.markCell(0, 0);
        assertEquals(minesweeper.getCloseText(), minesweeper.getCellText(0, 0));
    }

    @Test
    public void whenOpenedMineCellTextIsMineText() {
        minesweeper.setMine(0, 0);
        minesweeper.openCell(0, 0);
        assertEquals(minesweeper.getMineText(), minesweeper.getCellText(0, 0));
    }

    @Test
    public void whenOpenedFreeCellOnFreeBoardCellTextIsEmptyString() {
        minesweeper.boardClear();
        minesweeper.openCell(0, 0);
        assertEquals("", minesweeper.getCellText(0, 0));
    }

    @Test
    public void openedCellTextIs1When1MineIsAround() {
        minesweeper.boardClear();
        minesweeper.setMine(0, 0);
        minesweeper.openCell(1, 1);
        assertEquals("1", minesweeper.getCellText(1, 1));
    }

    @Test
    public void openedCellTextIs2When2MineIsAround() {
        minesweeper.boardClear();
        minesweeper.setMine(0, 0);
        minesweeper.setMine(0, 1);
        minesweeper.openCell(1, 1);
        assertEquals("2", minesweeper.getCellText(1, 1));
    }

    @Test
    public void openedCellTextIs3When3MineIsAround() {
        minesweeper.boardClear();
        minesweeper.setMine(0, 0);
        minesweeper.setMine(0, 1);
        minesweeper.setMine(0, 2);
        minesweeper.openCell(1, 1);
        assertEquals("3", minesweeper.getCellText(1, 1));
    }

    @Test
    public void openedCellTextIs8When8MineIsAround() {
        minesweeper.boardClear();
        minesweeper.setMine(0, 1);
        minesweeper.setMine(0, 0);
        minesweeper.setMine(1, 0);
        minesweeper.setMine(1, 2);
        minesweeper.setMine(2, 0);
        minesweeper.setMine(0, 2);
        minesweeper.setMine(2, 2);
        minesweeper.setMine(2, 1);
        minesweeper.openCell(1, 1);
        assertEquals("8", minesweeper.getCellText(1, 1));
    }
    @Test
    public void whenEmptyBoardMineCounterString0() {
        minesweeper.boardClear();
        assertEquals("0", minesweeper.getMineCounter());
    }

    @Test
    public void whenOneMineOnBoardMineCounterString1() {
        minesweeper.boardClear();
        minesweeper.setMine(0, 0);
        assertEquals("1", minesweeper.getMineCounter());
    }

    @Test
    public void whenNewGameSmileTextIsSmileText() {
        minesweeper.setMine(0, 0);
        minesweeper.openCell(0, 0);
        minesweeper.newGame();
        assertEquals(minesweeper.getSmileText(), minesweeper.getTextSmile());
    }

    @Test
    public void boardHeightMastBeGreaterZero() {
        assertTrue(minesweeper.getBoardHeight() > 0);
    }

    @Test
    public void boardWidthMastBeGreaterZero() {
        assertTrue(minesweeper.getBoardWidth() > 0);
    }
}
