package ru.unn.agile.Minesweeper.viewmodel;

import ru.unn.agile.Minesweeper.Model.Model;

public class ViewModel {

    private final Model minesweeperModel = new Model();

    private enum CellText {
        flag("!"),
        close("#"),
        issue("?"),
        mine("*");

        private final String text;

        private CellText(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    private enum SmilesText {
        smile("Живой"),
        dead("Мертвый"),
        winner("Выиграл");

        private final String text;

        private SmilesText(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public ViewModel() {
        /* empty */
    }

    public void newGame() {
        minesweeperModel.newGame();
    }

    public void openCell(final int x, final int y) {
        minesweeperModel.openCell(x, y);
    }

    public void markCell(final int x, final int y) {
        minesweeperModel.markCell(x, y);
    }

    public String getCellText(final int x, final int y) {
        if (minesweeperModel.isCellClose(x, y)) {
            if (minesweeperModel.isCellFlag(x, y)) {
                return CellText.flag.toString();
            } else if (minesweeperModel.isCellIssue(x, y)) {
                return CellText.issue.toString();
            } else {
                return CellText.close.toString();
            }
        } else if (minesweeperModel.isCellMine(x, y)) {
            return CellText.mine.toString();
        } else {
            int value = minesweeperModel.getCellValue(x, y);
            if (value == 0) {
                return "";
            } else {
                return Integer.toString(minesweeperModel.getCellValue(x, y));
            }
        }
    }

    public String getTextSmile() {
        if (minesweeperModel.isGameEnd()) {
            if (minesweeperModel.isLost()) {
                return SmilesText.dead.toString();
            } else {
                return SmilesText.winner.toString();
            }
        }
        return SmilesText.smile.toString();
    }

    public void boardClear() {
        minesweeperModel.boardClear();
    }

    public void setMine(final int x, final int y) {
        minesweeperModel.setMine(x, y);
    }

    public String getMineCounter() {
        return Integer.toString(minesweeperModel.getMineCounter());
    }

    public int getBoardHeight() {
        return minesweeperModel.getBoardHeight();
    }

    public int getBoardWidth() {
        return minesweeperModel.getBoardWidth();
    }

    public String getFlagText() {
        return CellText.flag.toString();
    }

    public String getCloseText() {
        return CellText.close.toString();
    }

    public String getIssueText() {
        return CellText.issue.toString();
    }

    public String getMineText() {
        return CellText.mine.toString();
    }

    public String getSmileText() {
        return SmilesText.smile.toString();
    }

    public String getDeadText() {
        return SmilesText.dead.toString();
    }

    public String getWinnerText() {
        return SmilesText.winner.toString();
    }
}
