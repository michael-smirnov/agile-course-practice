package ru.unn.agile.Triangle.view;

import javax.swing.*;

public final class Triangle {
    private JPanel mainPanel;
    private JButton enterCoordinatesOfPointsButton;
    private JButton calculatePerimeterButton;
    private JButton calculateSquareButton;
    private JComboBox comboBox1;
    private JButton calculateButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;

    private Triangle() { }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("Triangle");
        frame.setContentPane(new Triangle().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
