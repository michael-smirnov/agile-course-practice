package ru.unn.agile.Polinom.viewmodel;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.experimental.runners.Enclosed;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

@RunWith(Enclosed.class)
public class PolinomViewModelTests {
    public static class ArithmeticOperationsTest {
        @Before
        public void setUp() {
            PolinomViewModelTests.viewModel = new PolinomViewModel();
        }

        @After
        public void tearDown() {
            PolinomViewModelTests.viewModel = null;
        }

        @Test
        public void canSetDefaultValues() {
            assertEquals("", PolinomViewModelTests.viewModel.firstOperandProperty().get());
            assertEquals("", PolinomViewModelTests.viewModel.secondOperandProperty().get());
            assertEquals("", PolinomViewModelTests.viewModel.resultProperty().get());
        }

        @Test
        public void operationAddHasCorrectResult() {
            PolinomViewModelTests.viewModel.firstOperandProperty().set("2x^1 + 2.5");
            PolinomViewModelTests.viewModel.secondOperandProperty().set("1");

            PolinomViewModelTests.viewModel.operation(PolinomViewModel.Operation.ADD);

            assertEquals("2.0x^1+3.5", PolinomViewModelTests.viewModel.resultProperty().get());
        }

        @Test
        public void operationSubstractHasCorrectResult() {
            PolinomViewModelTests.viewModel.firstOperandProperty().set("2.5x^1 + 1");
            PolinomViewModelTests.viewModel.secondOperandProperty().set("2");

            PolinomViewModelTests.viewModel.operation(PolinomViewModel.Operation.SUBSTRACT);

            assertEquals("2.5x^1-1.0", PolinomViewModelTests.viewModel.resultProperty().get());
        }

        @Test
        public void operationMultiplyHasCorrectResult() {
            PolinomViewModelTests.viewModel.firstOperandProperty().set("2x^1 + 2");
            PolinomViewModelTests.viewModel.secondOperandProperty().set("-1");

            PolinomViewModelTests.viewModel.operation(PolinomViewModel.Operation.MULTIPLY);

            assertEquals("-2.0x^1-2.0", PolinomViewModelTests.viewModel.resultProperty().get());
        }

        @Test
        public void operationDivideHasCorrectResult() {
            PolinomViewModelTests.viewModel.firstOperandProperty().set("2x^1 + 2");
            PolinomViewModelTests.viewModel.secondOperandProperty().set("1");

            PolinomViewModelTests.viewModel.operation(PolinomViewModel.Operation.DIVIDE);

            assertEquals("2.0x^1+2.0", PolinomViewModelTests.viewModel.resultProperty().get());
        }

        @Test
        public void canSetCorrectMessageWhenDivideByZero() {
            PolinomViewModelTests.viewModel.firstOperandProperty().set("2x^1 + 2");
            PolinomViewModelTests.viewModel.secondOperandProperty().set("0");

            PolinomViewModelTests.viewModel.operation(PolinomViewModel.Operation.DIVIDE);

            assertEquals(PolinomViewModel.Errors.DIVIDE_BY_ZERO.getMessage(),
                PolinomViewModelTests.viewModel.resultProperty().get());
        }

        @Test
        public void canSetCorrectMessageWhenDivideLargeDegreePolinom() {
            PolinomViewModelTests.viewModel.firstOperandProperty().set("2x^1 + 2");
            PolinomViewModelTests.viewModel.secondOperandProperty().set("3x^4 + 1");

            PolinomViewModelTests.viewModel.operation(PolinomViewModel.Operation.DIVIDE);

            assertEquals(PolinomViewModel.Errors.DIVIDE_BY_LARGE_DEGREE.getMessage(),
                    PolinomViewModelTests.viewModel.resultProperty().get());
        }
    }

    @RunWith(Parameterized.class)
    public static class CalculateUsingCorrectInputTest {
        public CalculateUsingCorrectInputTest(final String firstOperand, final String secondOperand,
                                            final String expectedResult) {
            PolinomViewModelTests.firstOperand = firstOperand;
            PolinomViewModelTests.secondOperand = secondOperand;
            PolinomViewModelTests.expectedResult = expectedResult;
        }

        @Parameterized.Parameters
        public static List<Object[]> inputPolinomsAndExpectedResult() {
            return Arrays.asList(new Object[][] {
                    {"1", "2", "3.0"},
                    {"1 + 2x^1", "2", "2.0x^1+3.0"},
                    {"1 - 3x^1 -4x^2", "2x^1 +10", "-4.0x^2-1.0x^1+11.0"},
                    {"1", "-1", "0.0"},
                    {"2x^1 + 4.25", "2x^1 - 1x^2 - 4.25", "-1.0x^2+4.0x^1"},
                    {"2x^2 + 2x^2 + 1", "-1", "4.0x^2"},
                    {"  -1   +2x^1+1x^1  ", "1", "3.0x^1"}
            });
        }

        @Before
        public void setUp() {
            PolinomViewModelTests.viewModel = new PolinomViewModel();
            PolinomViewModelTests.viewModel.secondOperandProperty().
            set(PolinomViewModelTests.secondOperand);
            PolinomViewModelTests.viewModel.firstOperandProperty().
            set(PolinomViewModelTests.firstOperand);
        }

        @After
        public void tearDown() {
            PolinomViewModelTests.viewModel = null;
        }

        @Test
        public void canCorrectCalculateUsingVariousFormatOfInput() {
            PolinomViewModelTests.viewModel.operation(PolinomViewModel.Operation.ADD);

            assertEquals(PolinomViewModelTests.expectedResult,
                PolinomViewModelTests.viewModel.resultProperty().get());
        }
    }

    @RunWith(Parameterized.class)
    public static class IncorrectInputTest {
        public IncorrectInputTest(final String firstOperand, final String secondOperand,
            final String expectedResult) {
            PolinomViewModelTests.firstOperand = firstOperand;
            PolinomViewModelTests.secondOperand = secondOperand;
            PolinomViewModelTests.expectedResult = expectedResult;
        }

        @Parameterized.Parameters
        public static List<Object[]> incorrectInputAndExpectedErrorMessage() {
            return Arrays.asList(new Object[][] {
                {"", "NoPolinom", PolinomViewModel.Errors.EMPTY_FIELD.getMessage()},
                {"NoPolinom", "", PolinomViewModel.Errors.EMPTY_FIELD.getMessage()},
                {"", "", PolinomViewModel.Errors.EMPTY_FIELD.getMessage()},
                {"NoPolinom", "NoPolinom", PolinomViewModel.Errors.BAD_FORMAT.getMessage()},
                {"1", "NoPolinom", PolinomViewModel.Errors.BAD_FORMAT.getMessage()},
                {"NoPolinom", "1", PolinomViewModel.Errors.BAD_FORMAT.getMessage()},
                {"1x^ 1", "2", PolinomViewModel.Errors.BAD_FORMAT.getMessage()},
                {"1 x^1", "2", PolinomViewModel.Errors.BAD_FORMAT.getMessage()},
                {"1y^1", "2", PolinomViewModel.Errors.BAD_FORMAT.getMessage()},
                {"1x^1 * 2", "2", PolinomViewModel.Errors.BAD_FORMAT.getMessage()},
                {"1x^2.0", "2", PolinomViewModel.Errors.BAD_FORMAT.getMessage()}
            });
        }

        @Before
        public void setUp() {
            PolinomViewModelTests.viewModel = new PolinomViewModel();
            PolinomViewModelTests.viewModel.firstOperandProperty().
            set(PolinomViewModelTests.firstOperand);
            PolinomViewModelTests.viewModel.secondOperandProperty().
            set(PolinomViewModelTests.secondOperand);
        }

        @After
        public void tearDown() {
            PolinomViewModelTests.viewModel = null;
        }

        @Test
        public void canSetCorrectErrorMessageIfIncorrectInput() {
            PolinomViewModelTests.viewModel.operation(PolinomViewModel.Operation.DIVIDE);

            assertEquals(PolinomViewModelTests.expectedResult,
                PolinomViewModelTests.viewModel.resultProperty().get());
        }
    }

    private static PolinomViewModel viewModel;
    private static String firstOperand;
    private static String secondOperand;
    private static String expectedResult;
}
