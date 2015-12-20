package ru.unn.agile.Minesweeper.Model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModelTest {

    private Model minesweeper;

    @Before
    public void before() {
        minesweeper = new Model();
    }

    @Test
    public void whenEmptyBoardMineCounter0() {
        minesweeper.boardClear();
        assertEquals(0, minesweeper.getMineCounter());
    }

    @Test
    public void whenSetOnceMineOnEmptyBoardMineCounter1() {
        minesweeper.boardClear();
        minesweeper.setMine(0, 0);
        assertEquals(1, minesweeper.getMineCounter());
    }


    @Test
    public void defaultIsGameEndFalse() {
        assertFalse(minesweeper.isGameEnd());
    }

    @Test
    public void defaultIsLostFalse() {
        assertFalse(minesweeper.isLost());
    }

    @Test
    public void defaultIsCellFlagFalse() {
        assertFalse(minesweeper.isCellFlag(0, 0));
    }

    @Test
    public void defaultIsCellIssueFalse() {
        assertFalse(minesweeper.isCellIssue(0, 0));
    }

    @Test
    public void whenOnceMarkCellIsCellFlagIsTrue() {
        minesweeper.markCell(0, 0);
        assertTrue(minesweeper.isCellFlag(0, 0));
    }

    @Test
    public void whenTwiceMarkCellIsCellIssueIsTrue() {
        minesweeper.markCell(0, 0);
        minesweeper.markCell(0, 0);
        assertTrue(minesweeper.isCellIssue(0, 0));
    }

    @Test
    public void whenOpenMineIsGameEndIsTrue() {
        minesweeper.setMine(0, 0);
        minesweeper.openCell(0, 0);
        assertTrue(minesweeper.isGameEnd());
    }

    @Test
    public void whenOpenMineIsLostIsTrue() {
        minesweeper.setMine(0, 0);
        minesweeper.openCell(0, 0);
        assertTrue(minesweeper.isLost());
    }

    @Test
    public void whenOpenAllFreeCellsIsGameEndIsTrue() {
        minesweeper.boardClear();
        minesweeper.openCell(0, 0);
        assertTrue(minesweeper.isGameEnd());
    }

    @Test
    public void whenOpenAllFreeCellsIsLostIsFalse() {
        minesweeper.boardClear();
        minesweeper.openCell(0, 0);
        assertFalse(minesweeper.isLost());
    }

    @Test
    public void defaultIsCellCloseIsTrue() {
        assertTrue(minesweeper.isCellClose(0, 0));
    }

    @Test
    public void whenOpenCellIsCellCloseIsFalse() {
        minesweeper.openCell(0, 0);
        assertFalse(minesweeper.isCellClose(0, 0));
    }

    @Test
    public void whenSetMineIsCellMineIsTrue() {
        minesweeper.setMine(0, 0);
        assertTrue(minesweeper.isCellMine(0, 0));
    }

    @Test
    public void cellValueOnFreeBoard0() {
        minesweeper.boardClear();
        assertEquals(0, minesweeper.getCellValue(0, 0));
    }

    @Test
    public void whenOpenedFreeCellCloseTo1MineCellValueIs1() {
        minesweeper.boardClear();
        minesweeper.setMine(1, 1);
        assertEquals(1, minesweeper.getCellValue(0, 0));
    }

    @Test
    public void whenThreesMarkedCellIsCellIssueIsFalse() {
        minesweeper.markCell(0, 0);
        minesweeper.markCell(0, 0);
        minesweeper.markCell(0, 0);
        assertFalse(minesweeper.isCellIssue(0, 0));
    }

    @Test
    public void whenOnceMarkedCellPostEndGameIsCellFlagIsFalse() {
        minesweeper.setMine(0, 0);
        minesweeper.openCell(0, 0);
        minesweeper.markCell(0, 0);
        assertFalse(minesweeper.isCellFlag(0, 0));
    }

    @Test
    public void whenNewGameIsCellFlagIsFalse() {
        minesweeper.markCell(0, 0);
        minesweeper.newGame();
        assertFalse(minesweeper.isCellFlag(0, 0));
    }

    @Test
    public void getBoardHeight() {
        assertEquals(minesweeper.BOARD_HEIGHT, minesweeper.getBoardHeight());
    }

    @Test
    public void getBoardWidth() {
        Model model = new Model();
        assertEquals(minesweeper.BOARD_WIDTH, minesweeper.getBoardWidth());
    }

}
