package ru.unn.agile.NewtonMethod.view;

import ru.unn.NewtonMethod.viewModel.NewtonMethodViewModel;
import ru.unn.agile.NewtonMethod.infrastructure.NewtonMethodTxtLogger;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public final class NewtonMethod {
    private final NewtonMethodViewModel viewModel;
    private JPanel mainPanel;
    private JTextField txtLeftPoint;
    private JTextField txtRightPoint;
    private JTextField txtFunction;
    private JButton calculateButton;
    private JTextField txtRoot;
    private JLabel labelStatus;
    private JTextField txtDerivative;
    private JList<String> newtonMethodListLog;

    private NewtonMethod(final NewtonMethodViewModel viewModel) {
        this.viewModel = viewModel;
        newtonMethodBind();

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                newtonMethodBackBind();
                viewModel.processKeyInTextField(NewtonMethodViewModel.KeyboardKeys.ENTER.getKey());
                newtonMethodBind();
            }
        });

        KeyAdapter keyListener = new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent e) {
                newtonMethodBackBind();
                viewModel.processKeyInTextField(e.getKeyCode());
                newtonMethodBind();
            }
        };

        txtLeftPoint.addKeyListener(keyListener);
        txtRightPoint.addKeyListener(keyListener);
        txtFunction.addKeyListener(keyListener);
        txtDerivative.addKeyListener(keyListener);

        FocusAdapter focusListener = new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent e) {
                newtonMethodBackBind();
                viewModel.valueFieldFocusLost();
                newtonMethodBind();
            }
        };

        txtLeftPoint.addFocusListener(focusListener);
        txtRightPoint.addFocusListener(focusListener);
        txtFunction.addFocusListener(focusListener);
        txtDerivative.addFocusListener(focusListener);
    }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("Newton method");
        String newtonMethodLogFileName = "./NewtonMethod.log";
        NewtonMethodTxtLogger txtLogger = new NewtonMethodTxtLogger(newtonMethodLogFileName);
        frame.setContentPane(new NewtonMethod(new NewtonMethodViewModel(txtLogger)).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void newtonMethodBind() {
        calculateButton.setEnabled(viewModel.isCalculateButtonEnabled());
        txtRoot.setText(viewModel.getRoot());
        labelStatus.setText(viewModel.getStatus());
        txtLeftPoint.setText(viewModel.getLeftPoint());
        txtRightPoint.setText(viewModel.getRightPoint());

        List<String> newtonMethodLog = viewModel.getLog();
        String[] items = newtonMethodLog.toArray(new String[newtonMethodLog.size()]);
        newtonMethodListLog.setListData(items);
    }

    private void newtonMethodBackBind() {
        viewModel.setFunction(txtFunction.getText());
        viewModel.setDerivative(txtDerivative.getText());
        viewModel.setLeftPointOfRange(txtLeftPoint.getText());
        viewModel.setRightPointOfRange(txtRightPoint.getText());
    }
}
