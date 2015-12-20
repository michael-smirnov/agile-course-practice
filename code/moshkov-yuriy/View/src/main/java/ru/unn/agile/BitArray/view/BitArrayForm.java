package ru.unn.agile.BitArray.view;

import ru.unn.agile.BitArray.viewmodel.ViewModel;
import ru.unn.agile.BitArray.model.BitArray;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public final class BitArrayForm {
    private final ViewModel viewModel;

    private JPanel mainPanel;
    private JTextField sizeArrayTextField;
    private JButton initArrayButton;
    private JButton doOperationButton;
    private JTable firstBitArrayTable;
    private JTable secondBitArrayTable;
    private JTable resultBitArrayTable;
    private JComboBox<ViewModel.Operation> operationCombobox;
    private JScrollPane firstBitArrayScrollPane;
    private JScrollPane secondBitArrayScrollPane;
    private JScrollPane resultBitArrayScrollPane;
    private JTextPane infoTextPane;
    private JLabel notificationLabel;

    private BitArrayForm(final ViewModel viewModel) {
        this.viewModel = viewModel;
        loadListOfOperations();
        backBind();

        initArrayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bind();
                viewModel.initializeArray();
                backBind();
            }
        });

        doOperationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bind();
                viewModel.doOperation();
                backBind();
            }
        });

        operationCombobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bind();
                backBind();
            }
        });

        sizeArrayTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent event) {
                bind();
                backBind();
            }

            @Override
            public void removeUpdate(final DocumentEvent event) {
                bind();
                backBind();
            }

            @Override
            public void changedUpdate(final DocumentEvent event) {
                bind();
                backBind();
            }
        });
    }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("BitArrayForm");
        frame.setContentPane(new BitArrayForm(new ViewModel()).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void bind() {
        viewModel.setArraySize(sizeArrayTextField.getText());
        viewModel.setOperation((ViewModel.Operation) operationCombobox.getSelectedItem());

        BitArray firstBitArray = viewModel.getFirstBitArray();
        BitArray secondBitArray = viewModel.getSecondBitArray();
        for (int i = 0; i < firstBitArrayTable.getColumnCount(); i++) {
            firstBitArray.setBit(i, (Boolean) firstBitArrayTable.getValueAt(0, i));
            secondBitArray.setBit(i, (Boolean) secondBitArrayTable.getValueAt(0, i));
        }

        viewModel.setFirstBitArray(firstBitArray);
        viewModel.setSecondBitArray(secondBitArray);
    }

    private void loadListOfOperations() {
        ViewModel.Operation[] operations = ViewModel.Operation.values();
        operationCombobox.setModel(new JComboBox<>(operations).getModel());
    }

    private void backBind() {
        doOperationButton.setEnabled(viewModel.isDoOperationEnabled());
        initArrayButton.setEnabled(viewModel.isInitializeArrayButtonEnabled());
        notificationLabel.setText(viewModel.getNotification());

        firstBitArrayTable = createTableFromBitArray(viewModel.getFirstBitArray());
        firstBitArrayTable.addMouseListener(new BitArrayMouseAdapter());
        firstBitArrayScrollPane.setViewportView(firstBitArrayTable);

        secondBitArrayTable = createTableFromBitArray(viewModel.getSecondBitArray());
        secondBitArrayTable.addMouseListener(new BitArrayMouseAdapter());
        secondBitArrayScrollPane.setViewportView(secondBitArrayTable);

        resultBitArrayTable = createTableFromBitArray(viewModel.getResultBitArray());
        resultBitArrayTable.setEnabled(false);
        resultBitArrayScrollPane.setViewportView(resultBitArrayTable);
    }

    private JTable createTableFromBitArray(final BitArray bitArray) {
        JTable result = new JTable();

        Object[][] values = new Boolean[1][bitArray.getSize()];
        Object[] header = new String[bitArray.getSize()];

        int counter = 0;
        for (Object item : bitArray.getBitArray()) {
            header[counter] = Integer.toString(counter);
            values[0][counter++] = item;
        }

        result.setModel(new DefaultTableModel(values, header) {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return false;
            }
        });

        return result;
    }

    private void createUIComponents() {
        Object[][] values = new Object[][]{};
        Object[] header = new Object[]{};

        firstBitArrayTable = new JTable(values, header);
        secondBitArrayTable = new JTable(values, header);
        resultBitArrayTable = new JTable(values, header);
    }

    private class BitArrayMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(final MouseEvent e) {
            if (e.getClickCount() == 2) {
                JTable target = (JTable) e.getSource();
                int row = target.getSelectedRow();
                int column = target.getSelectedColumn();
                boolean value = (boolean) target.getValueAt(row, column);
                target.setValueAt(!value, row, column);
            }
        }
    }
}
