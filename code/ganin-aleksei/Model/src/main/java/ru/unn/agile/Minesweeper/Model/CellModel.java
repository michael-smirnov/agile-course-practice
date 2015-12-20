package ru.unn.agile.Minesweeper.Model;

class CellModel {
    private boolean mine;
    private boolean issue;
    private boolean flag;
    private boolean isOpen = true;
    private int value;

    private final int y;
    private final int x;

    public CellModel(final int y, final int x) {
        this.y = y;
        this.x = x;
    }

    public void setMine() {
        mine = true;
    }
    public boolean isMine() {
        return mine;
    }

    public void setValue(final int val) {
        value = val;
    }

    public int getValue() {
        return value;
    }

    public void setIssue(final boolean issue) {
        this.issue = issue;
    }

    public boolean isIssue() {
        return issue;
    }

    public void setFlag(final boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void open() {
        isOpen = true;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void clear() {
        setIssue(false);
        setFlag(false);
        setValue(0);
        isOpen = false;
        mine = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
