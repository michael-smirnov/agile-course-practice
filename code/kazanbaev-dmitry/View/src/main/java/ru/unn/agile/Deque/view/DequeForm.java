package ru.unn.agile.Deque.view;

import ru.unn.agile.Deque.infrastructure.DequeLogger;
import ru.unn.agile.Deque.viewmodel.DequeViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

public final class DequeForm {
    private final DequeViewModel viewModel;

    private JPanel mainPanel;
    private JTextField inputNumber;
    private JTable dequeTable;
    private JScrollPane mainScrollPane;
    private JTextField outputText;
    private JComboBox<String> selectActionBox;
    private JButton doActionButton;
    private JList<String> logList;

    private DequeForm() {
        viewModel = new DequeViewModel(new DequeLogger("./Deque.log"));

        inputNumber.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent event) {
                backBind();
                bind();
            }

            @Override
            public void removeUpdate(final DocumentEvent event) {
                backBind();
                bind();
            }

            @Override
            public void changedUpdate(final DocumentEvent event) {
                backBind();
                bind();
            }
        });

        inputNumber.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent e) {
                backBind();
                viewModel.logUpdatedInput();
                bind();
            }
        });

        doActionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                backBind();
                viewModel.doAction();
                bind();
            }
        });

        selectActionBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                backBind();
                bind();
            }
        });

        bind();
    }

    private void backBind() {
        viewModel.setInputNumber(inputNumber.getText());
        viewModel.setAction(selectActionBox.getSelectedIndex());
    }

    private void bind() {
        doActionButton.setEnabled(viewModel.isDoActionButtonEnabled());
        outputText.setText(viewModel.getOutput());

        Object[][] values = new Integer[viewModel.getAction().getSize()][1];
        int counter = 0;
        for (Object item : viewModel.getAction().toArray()) {
            values[counter++][0] = item;
        }

        dequeTable = new JTable(values, new Object[]{"DEQUE"});
        dequeTable.setEnabled(false);
        mainScrollPane.setViewportView(dequeTable);

        List<String> log = viewModel.getLog();
        String[] arrayOfMessages = log.toArray(new String[log.size()]);
        logList.setListData(arrayOfMessages);
    }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("DequeForm");
        frame.setContentPane(new DequeForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        dequeTable = new JTable(new Object[][]{}, new Object[]{"DEQUE"});
    }
}
