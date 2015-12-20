package ru.unn.agile.HypothecCalculator.view;

import ru.unn.agile.HypothecCalculator.viewmodel.ViewModel;
import ru.unn.agile.HypothecsCalculator.model.Hypothec;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public final class HypothecCalculator {

    private JPanel mainPanel;
    private JTextField houseCost;
    private JTextField interestRate;
    private JTextField downPayment;
    private JTextField countOfPeriods;
    private JComboBox<Hypothec.CurrencyType> currencyType;
    private JComboBox<Hypothec.PeriodType> periodType;
    private JComboBox<Hypothec.InterestRateType> interestRateType;
    private JTextField flatFee;
    private JTextField monthlyFee;
    private JComboBox<Hypothec.FlatFeeType> flatFeeType;
    private JComboBox<Hypothec.MonthlyFeeType> monthlyFeeType;
    private JComboBox<Hypothec.CreditType> creditType;
    private JTextField month;
    private JTextField year;
    private JButton compute;
    private JLabel status;

    private JTable graphicOfPayments;
    private JLabel monthlyPayment;
    private JLabel overpaymentWithFees;
    private JLabel overpayment;

    private final ViewModel viewModel = new ViewModel();

    private static final int HEADERS_HEIGHT = 50;

    private HypothecCalculator() {
        loadAllLists();
        backBind();


        KeyAdapter keyListener = new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                bind();
                viewModel.checkInput();
                backBind();
            }
        };
        houseCost.addKeyListener(keyListener);
        interestRate.addKeyListener(keyListener);
        downPayment.addKeyListener(keyListener);
        countOfPeriods.addKeyListener(keyListener);
        flatFee.addKeyListener(keyListener);
        monthlyFee.addKeyListener(keyListener);
        month.addKeyListener(keyListener);
        year.addKeyListener(keyListener);


        currencyType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                bind();
                backBind();
            }
        });
        periodType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                bind();
                backBind();
            }
        });
        interestRateType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                bind();
                backBind();
            }
        });
        flatFeeType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                bind();
                backBind();
            }
        });
        monthlyFeeType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                bind();
                backBind();
            }
        });
        creditType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                bind();
                backBind();
            }
        });
        compute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                bind();
                viewModel.compute();
                backBind();
            }
        });
    }

    private void createUIComponents() {
        mainPanel = new DollarPanel();

        graphicOfPayments = new JTable();
        JTableHeader header = graphicOfPayments.getTableHeader();
        header.setPreferredSize(new Dimension(graphicOfPayments.getWidth(), HEADERS_HEIGHT));
        }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("Credit calculator");
        frame.setContentPane(new HypothecCalculator().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void bind() {
        viewModel.setStatus(status.getText());
        viewModel.setHouseCost(houseCost.getText());
        viewModel.setDownPayment(downPayment.getText());
        viewModel.setCountOfPeriods(countOfPeriods.getText());
        viewModel.setInterestRate(interestRate.getText());
        viewModel.setFlatFee(flatFee.getText());
        viewModel.setMonthlyFee(monthlyFee.getText());

        viewModel.setCurrencyType((Hypothec.CurrencyType) currencyType.getSelectedItem());
        viewModel.setPeriodType((Hypothec.PeriodType) periodType.getSelectedItem());
        viewModel.setInterestRateType(
                (Hypothec.InterestRateType) interestRateType.getSelectedItem());
        viewModel.setFlatFeeType((Hypothec.FlatFeeType) flatFeeType.getSelectedItem());
        viewModel.setMonthlyFeeType((Hypothec.MonthlyFeeType) monthlyFeeType.getSelectedItem());
        viewModel.setCreditType((Hypothec.CreditType) creditType.getSelectedItem());

        viewModel.setStartMonth(month.getText());
        viewModel.setStartYear(year.getText());
    }

    private void backBind() {
        status.setText(viewModel.getStatus());
        compute.setEnabled(viewModel.isButtonEnabled());
        houseCost.setText(viewModel.getHouseCost());
        downPayment.setText(viewModel.getDownPayment());
        countOfPeriods.setText(viewModel.getCountOfPeriods());
        interestRate.setText(viewModel.getInterestRate());
        flatFee.setText(viewModel.getFlatFee());
        monthlyFee.setText(viewModel.getMonthlyFee());

        currencyType.setSelectedItem(viewModel.getCurrencyType());
        periodType.setSelectedItem(viewModel.getPeriodType());
        interestRateType.setSelectedItem(viewModel.getInterestRateType());
        flatFeeType.setSelectedItem(viewModel.getFlatFeeType());
        monthlyFeeType.setSelectedItem(viewModel.getMonthlyFeeType());
        creditType.setSelectedItem(viewModel.getCreditType());

        month.setText(viewModel.getStartMonth());
        year.setText(viewModel.getStartYear());

        monthlyPayment.setText(viewModel.getMonthlyPayment());
        overpaymentWithFees.setText(viewModel.getOverpaymentWithFees());
        overpayment.setText(viewModel.getOverpayment());

        DefaultTableModel model = viewModel.getGraphicOfPayments();
        graphicOfPayments.setModel(model);
        graphicOfPayments.setPreferredSize(new Dimension(graphicOfPayments.getWidth(),
                graphicOfPayments.getRowHeight() * model.getRowCount()));
    }

    private void loadAllLists() {
        loadListOfCurrencies();
        loadListOfPeriods();
        loadListOfInterestRateTypes();
        loadListOfFlatFeeTypes();
        loadListOfMonthlyFeeTypes();
        loadListOfCreditTypes();
    }

    private void loadListOfCurrencies() {
        Hypothec.CurrencyType[] currencies = Hypothec.CurrencyType.values();
        currencyType.setModel(new JComboBox<>(currencies).getModel());
    }

    private void loadListOfPeriods() {
        Hypothec.PeriodType[] periods = Hypothec.PeriodType.values();
        periodType.setModel(new JComboBox<>(periods).getModel());
    }

    private void loadListOfInterestRateTypes() {
        Hypothec.InterestRateType[] types = Hypothec.InterestRateType.values();
        interestRateType.setModel(new JComboBox<>(types).getModel());
    }

    private void loadListOfFlatFeeTypes() {
        Hypothec.FlatFeeType[] types = Hypothec.FlatFeeType.values();
        flatFeeType.setModel(new JComboBox<>(types).getModel());
    }

    private void loadListOfMonthlyFeeTypes() {
        Hypothec.MonthlyFeeType[] types = Hypothec.MonthlyFeeType.values();
        monthlyFeeType.setModel(new JComboBox<>(types).getModel());
    }

    private void loadListOfCreditTypes() {
        Hypothec.CreditType[] types = Hypothec.CreditType.values();
        creditType.setModel(new JComboBox<>(types).getModel());
    }
}

class DollarPanel extends JPanel {

    public void paintComponent(final Graphics g) {
        Image im = null;
        try {
            im = ImageIO.read(new File("code/filicheva-svetlana/View/img/money.jpg"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        g.drawImage(im, 0, 0, null);
    }
}


