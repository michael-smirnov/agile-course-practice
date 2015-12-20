package ru.unn.agile.Minesweeper.Model;

public class Model {

    public static final int BOARD_HEIGHT = 32;
    public static final int BOARD_WIDTH = 32;

    private static final int DEFAULT_MINES = 100;

    private final BoardModel board = new BoardModel(BOARD_HEIGHT, BOARD_WIDTH);



    public Model() {
        board.clear();
        board.setMinesRandom(DEFAULT_MINES);
    }

    public int getMineCounter() {
        return  board.getMineCounter();
    }

    public void openCell(final int x, final int y) {
        board.openCell(y, x);
    }

    public void markCell(final int x, final int y) {
        board.markCell(y, x);
    }

    public void newGame() {
        board.clear();
        board.setMinesRandom(DEFAULT_MINES);
    }

    public void boardClear() {
        board.clear();
    }

    public void setMine(final int x, final int y) {
        board.setMine(y, x);
    }

    public boolean isCellFlag(final int x, final int y) {
        return board.getCell(y, x).isFlag();
    }

    public boolean isCellIssue(final int x, final int y) {
        return board.getCell(y, x).isIssue();
    }

    public boolean isCellClose(final int x, final int y) {
        return !board.getCell(y, x).isOpen();
    }

    public boolean isCellMine(final int x, final int y) {
        return board.getCell(y, x).isMine();
    }

    public int getCellValue(final int x, final int y) {
        return board.getCell(y, x).getValue();
    }

    public boolean isGameEnd() {
        return board.isEnd();
    }

    public boolean isLost() {
        return board.isLost();
    }

    public int getBoardWidth() {
        return BOARD_WIDTH;
    }

    public int getBoardHeight() {
        return BOARD_HEIGHT;
    }
}
