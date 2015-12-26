package ru.unn.agile.Triangle;

import ru.unn.agile.TriangleViewModel.TriangleViewModel;
import ru.unn.agile.TriangleViewModel.ValuesToCalculate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public final class TriangleView {
    private JPanel main;
    private JTextField point1X;
    private JTextField point1Y;
    private JTextField point1Z;
    private JTextField point2X;
    private JTextField point2Y;
    private JTextField point2Z;
    private JTextField point3X;
    private JTextField point3Y;
    private JTextField point3Z;
    private JComboBox<ValuesToCalculate> valueToCalculate;
    private JButton calculate;
    private JTextField result;
    private JTextField status;

    private final TriangleViewModel viewModel = new TriangleViewModel();

    private TriangleView() {
        backBind();
        loadListOfValues();
        calculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                backBind();
                try {
                    viewModel.compute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                bind();
            }
        });

        valueToCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                backBind();
                bind();
            }
        });
        KeyAdapter keyListener = new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                backBind();
                viewModel.checkInputAndChangeStateIfOK();
                bind();
            }
        };
        point1X.addKeyListener(keyListener);
        point1Y.addKeyListener(keyListener);
        point1Z.addKeyListener(keyListener);
        point2X.addKeyListener(keyListener);
        point2Y.addKeyListener(keyListener);
        point2Z.addKeyListener(keyListener);
        point3X.addKeyListener(keyListener);
        point3Y.addKeyListener(keyListener);
        point3Z.addKeyListener(keyListener);
        bind();
    }

    private void loadListOfValues() {
        ValuesToCalculate[] values = ValuesToCalculate.values();
        valueToCalculate.setModel(new JComboBox<>(values).getModel());
    }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("TriangleView");
        frame.setContentPane(new TriangleView().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void backBind() {
        viewModel.setCoordinate1X(point1X.getText());
        viewModel.setCoordinate1Y(point1Y.getText());
        viewModel.setCoordinate1Z(point1Z.getText());
        viewModel.setCoordinate2X(point2X.getText());
        viewModel.setCoordinate2Y(point2Y.getText());
        viewModel.setCoordinate2Z(point2Z.getText());
        viewModel.setCoordinate3X(point3X.getText());
        viewModel.setCoordinate3Y(point3Y.getText());
        viewModel.setCoordinate3Z(point3Z.getText());
        viewModel.setValueToCalculate((ValuesToCalculate) valueToCalculate.getSelectedItem());
    }

    private void bind() {
        status.setText(viewModel.getStatus());
        point1X.setText(viewModel.getCoordinate1X());
        point1Y.setText(viewModel.getCoordinate1Y());
        point1Z.setText(viewModel.getCoordinate1Z());
        point2X.setText(viewModel.getCoordinate2X());
        point2Y.setText(viewModel.getCoordinate2Y());
        point2Z.setText(viewModel.getCoordinate2Z());
        point3X.setText(viewModel.getCoordinate3X());
        point3Y.setText(viewModel.getCoordinate3Y());
        point3Z.setText(viewModel.getCoordinate3Z());
        calculate.setEnabled(viewModel.isCalculateButtonEnabled());
        result.setText(viewModel.getResult());
    }
}
