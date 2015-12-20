package ru.unn.agile.PercentAccretion.view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ru.unn.agile.PercentAccretion.Model.PercentAccretionFactory;
import ru.unn.agile.PercentAccretion.viewmodel.PercentAccretionViewModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class PercentAccretionView {
    private final PercentAccretionViewModel viewModel;
    private JTextField initialSumTextField;
    private JTextField percentRateTextField;
    private JTextField countOfYearsTextField;
    private JTextArea errorTextArea;
    private JPanel labelPanel;
    private JPanel fieldsPanel;
    private JRadioButton simplePercentRadioButton;
    private JRadioButton complexPercentRadioButton;
    private JLabel initialSumLabel;
    private JLabel percentRateLabel;
    private JLabel timeLabel;
    private JButton calculateSumButton;
    private JPanel resultPanel;
    private JPanel choosePercentPanel;
    private JPanel calculationPanel;
    private JPanel formPanel;
    private JTextField resultTextField;
    private final ButtonGroup radioButtonGroup;

    public static void main(final String[] args) {
        JFrame frame = new JFrame("PercentAccretionView");
        frame.setContentPane(new PercentAccretionView().formPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private PercentAccretionView() {
        viewModel = new PercentAccretionViewModel();

        radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(simplePercentRadioButton);
        radioButtonGroup.add(complexPercentRadioButton);

        radioButtonGroup.setSelected(simplePercentRadioButton.getModel(), true);
        simplePercentRadioButton.setActionCommand(
                PercentAccretionFactory.AccretionType.SIMPLE_PERCENT_SUM.toString());
        complexPercentRadioButton.setActionCommand(
                PercentAccretionFactory.AccretionType.COMPLEX_PERCENT_SUM.toString());

        ActionListener radioButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                backBindPercentAccretionView();
                bindPercentAccretionView();
            }
        };

        simplePercentRadioButton.addActionListener(radioButtonListener);
        complexPercentRadioButton.addActionListener(radioButtonListener);

        DocumentListener textFieldsListener = new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) {
                backBindPercentAccretionView();
                bindPercentAccretionView();
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                backBindPercentAccretionView();
                bindPercentAccretionView();
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                backBindPercentAccretionView();
                bindPercentAccretionView();
            }
        };

        initialSumTextField.getDocument().addDocumentListener(textFieldsListener);
        countOfYearsTextField.getDocument().addDocumentListener(textFieldsListener);
        percentRateTextField.getDocument().addDocumentListener(textFieldsListener);

        calculateSumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                backBindPercentAccretionView();
                viewModel.calculateResultSum();
                bindPercentAccretionView();
            }
        });

        bindPercentAccretionView();
    }

    private void bindPercentAccretionView() {
        calculateSumButton.setEnabled(viewModel.isCalculateButtonEnabled());
        errorTextArea.setText(viewModel.getErrorMessage());
        resultTextField.setText(viewModel.getResultSum());
    }

    private void backBindPercentAccretionView() {
        viewModel.setInitialSum(initialSumTextField.getText());
        viewModel.setPercentRate(percentRateTextField.getText());
        viewModel.setCountOfYears(countOfYearsTextField.getText());
        viewModel.setPercentType(radioButtonGroup.getSelection().getActionCommand());
    }
}
