package ru.unn.agile.Triangle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class Triangle {
    private JPanel mainPanel;
    private JTextField point1X;
    private JTextField point1Y;
    private JTextField point1Z;
    private JTextField point2X;
    private JTextField point2Y;
    private JTextField point2Z;
    private JTextField point3X;
    private JTextField point3Y;
    private JTextField point3Z;
    private JComboBox valueToCalculate;
    private JButton calculateButton;
    private JTextField result;
    private JTextField status;

    private final TriangleViewModel viewModel = new TriangleViewModel();

    private Triangle() {

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bind();
                try {
                    viewModel.compute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                backBind();
            }
        });

        valueToCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                bind();
                backBind();
            }
        });
    }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("Triangle");
        frame.setContentPane(new Triangle().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void bind() {
        viewModel.setStatus(status.getText());
        viewModel.setCoordinate1X(point1X.getText());
        viewModel.setCoordinate1Y(point1Y.getText());
        viewModel.setCoordinate1Z(point1Z.getText());
        viewModel.setCoordinate2X(point2X.getText());
        viewModel.setCoordinate2Y(point2Y.getText());
        viewModel.setCoordinate2Z(point2Z.getText());
        viewModel.setCoordinate3X(point3X.getText());
        viewModel.setCoordinate3Y(point3Y.getText());
        viewModel.setCoordinate3Z(point3Z.getText());
        viewModel.setResult(result.getText());
    }

    private void backBind() {
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
        result.setText(viewModel.getResult());
    }
}
