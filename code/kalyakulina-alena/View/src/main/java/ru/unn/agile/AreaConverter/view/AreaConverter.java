package ru.unn.agile.AreaConverter.view;

import ru.unn.agile.AreaConverter.infrastructure.AreaConverterTxtLogger;
import ru.unn.agile.AreaConverter.model.AreaMeasure;
import ru.unn.agile.AreaConverter.viewmodel.ViewModel;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public final class AreaConverter {

    private ViewModel viewModel;

    private JPanel mainPanel;
    private JTextField inputArea;
    private JComboBox<AreaMeasure> from;
    private JButton convertButton;
    private JComboBox<AreaMeasure> to;
    private JTextField resultArea;
    private JTextField status;
    private JList<String> logList;

    private AreaConverter() { }

    private AreaConverter(final ViewModel viewModel) {
        this.viewModel = viewModel;

        loadListOfMeasures();
        bind();

        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                backBind();
                viewModel.convert();
                bind();
            }
        });

        from.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                backBind();
                bind();
            }
        });

        to.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                backBind();
                bind();
            }
        });

        KeyAdapter keyListener = new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                backBind();
                viewModel.parseInput();
                bind();
            }
        };

        inputArea.addKeyListener(keyListener);

        FocusAdapter focusLostListener = new FocusAdapter() {
            public void focusLost(final FocusEvent e) {
                backBind();
                viewModel.focusLost();
                bind();
            }
        };

        inputArea.addFocusListener(focusLostListener);
    }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("AreaConverter");
        AreaConverterTxtLogger logger = new AreaConverterTxtLogger("./AreaConverter.log");
        frame.setContentPane(new AreaConverter(new ViewModel(logger)).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void loadListOfMeasures() {
        AreaMeasure[] measures = AreaMeasure.values();
        from.setModel(new JComboBox<>(measures).getModel());
        to.setModel(new JComboBox<>(measures).getModel());
    }

    private void backBind() {
        viewModel.setInputArea(inputArea.getText());

        viewModel.setFrom((AreaMeasure) from.getSelectedItem());
        viewModel.setTo((AreaMeasure) to.getSelectedItem());
    }

    private void bind() {
        convertButton.setEnabled(viewModel.isConvertButtonEnabled());

        resultArea.setText(viewModel.getResultArea());
        status.setText(viewModel.getStatus());

        List<String> log = viewModel.getLog();
        String[] logItems = log.toArray(new String[log.size()]);
        logList.setListData(logItems);
    }
}
