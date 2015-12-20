package ru.unn.agile.Minesweeper.View;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ru.unn.agile.Minesweeper.viewmodel.ViewModel;

public class View {

    private final ViewModel minesweeperViewModel = new ViewModel();
    public static final int CELL_SIZE = 20;
    private static final int SMILE_POSITION_Y = 0;
    private static final int MINE_COUNTER_POSITION_Y = 70;
    private static final int SMILE_SIZE = 100;
    private static final int MINE_COUNTER_HEIGHT = 20;
    private static final int MINE_COUNTER_WIDTH = 50;
    private final  BoardView board = new BoardView(
            minesweeperViewModel.getBoardWidth(),
            minesweeperViewModel.getBoardHeight()
    );
    private final  JLabel smile;
    private final  JLabel mineCounter;

    public class BoardView extends JLabel {

        private final CellView[][] cells;

        public class CellView extends JLabel {
            public CellView(final int x, final int y) {
                addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(final MouseEvent e) {
                                if (SwingUtilities.isRightMouseButton(e)) {
                                    minesweeperViewModel.markCell(x, y);
                                }
                                if (SwingUtilities.isLeftMouseButton(e)) {
                                    minesweeperViewModel.openCell(x, y);
                                }
                                binding();
                            }
                        });
            }
        }

        public BoardView(final int boardWidth, final int boardHeight) {
            cells = new CellView[boardHeight][boardWidth];
            for (int y = 0; y < boardHeight; y++) {
                cells[y] = new CellView[boardWidth];
                for (int x = 0; x < boardWidth; x++) {
                    CellView cell = new CellView(x, y);
                    cell.setBounds(y * CELL_SIZE, x * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    add(cell);
                    cells[y][x] = cell;
                }
            }
        }
    }

    public View() {
        JFrame frame = new JFrame("Сапер");
        frame.setSize(minesweeperViewModel.getBoardWidth(), minesweeperViewModel.getBoardHeight());
        smile = new JLabel();
        smile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(final MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            minesweeperViewModel.newGame();
                        }
                        binding();
                    }
                });

        smile.setBounds(
                minesweeperViewModel.getBoardWidth() * CELL_SIZE,
                SMILE_POSITION_Y,
                SMILE_SIZE, SMILE_SIZE
        );
        mineCounter = new JLabel();
        mineCounter.setBounds(
                minesweeperViewModel.getBoardWidth() * CELL_SIZE,
                MINE_COUNTER_POSITION_Y,
                MINE_COUNTER_WIDTH,
                MINE_COUNTER_HEIGHT
        );
        frame.setSize(
                minesweeperViewModel.getBoardWidth() * (CELL_SIZE + 1)
                        +  MINE_COUNTER_WIDTH,
                minesweeperViewModel.getBoardHeight() * (CELL_SIZE + 1)
        );
        frame.add(smile);
        frame.add(mineCounter);
        frame.add(board);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        binding();
    }

    private void binding() {
        for (int y = 0; y < minesweeperViewModel.getBoardHeight(); y++) {
            for (int x = 0; x < minesweeperViewModel.getBoardWidth(); x++) {
                board.cells[y][x].setText(minesweeperViewModel.getCellText(x, y));
            }
        }
        mineCounter.setText(minesweeperViewModel.getMineCounter());
        smile.setText(minesweeperViewModel.getTextSmile());
    }

    public int getCellSize() {
        return CELL_SIZE;
    }
}
