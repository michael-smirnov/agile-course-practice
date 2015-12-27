package ru.unn.agile.StatisticValueCalculator.viewmodel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StatisticCalculatorViewModelTestsOnLogging {
    private StatisticCalculatorViewModel viewModel;

    public void setViewModel(final StatisticCalculatorViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        viewModel = new StatisticCalculatorViewModel(new FakeLoggerOfStatisticCalculator());
    }

    @Test
    public void isLogContainsAddRowValueAfterItHadBeenTyped() {
        String rowValue = "abc";
        viewModel.inputRowProperty().set(rowValue);

        viewModel.onInputFieldFocusChanged(Boolean.TRUE, Boolean.FALSE);

        assertTrue(viewModel.getLog().get(0).matches(".*"
                + LogMessages.inputRowValueIsSet(rowValue)));
    }

    /*@Test
    public void onParameterValueChangedMessageIsAddToLogAfterProbabilitySetAndEventValueChanged() {
        viewModel.setSelectedStatistic(StatisticValue.PROBABILITY);
        String eventValue = "2.4";
        viewModel.inputStatisticParameterProperty().set(eventValue);

        viewModel.onInputFieldFocusChanged(Boolean.TRUE, Boolean.FALSE);

        assertTrue(viewModel.getLog().get(1).matches(".*"
                + LogMessages.inputParameterValueIsSet(StatisticParameter.EVENT, eventValue)));
    }

    @Test
    public void logNotContainsAddRowValueWhenAddRowTextFieldJustLeaveFocus() {
        viewModel.onInputFieldFocusChanged(Boolean.TRUE, Boolean.FALSE);

        assertTrue(viewModel.getLog().isEmpty());
    }

    @Test
    public void logNotUpdatedSecondTimeWhenOrderOfCentralMomentChangedOnesAndFocusedTwice() {
        viewModel.setSelectedStatistic(StatisticValue.CENTRAL_MOMENT);
        String orderValue = "4";

        viewModel.inputStatisticParameterProperty().set(orderValue);
        viewModel.onInputFieldFocusChanged(Boolean.TRUE, Boolean.FALSE);

        viewModel.onInputFieldFocusChanged(Boolean.FALSE, Boolean.TRUE);
        viewModel.inputStatisticParameterProperty().set(orderValue);
        viewModel.onInputFieldFocusChanged(Boolean.TRUE, Boolean.FALSE);

        assertTrue(viewModel.getLog().size() == 2);
    }

    @Test
    public void lastMessageInLogIsAddRowToStatisticDataAfterNewAddingValueHadBeenAddedToTable() {
        String addingValue = "11";
        viewModel.inputRowProperty().set(addingValue);

        viewModel.addRowToStatisticData();

        assertTrue(viewModel.getLog().get(0)
                .matches(".*" + LogMessages.newValueToDataTableIsAdded(addingValue)));
    }

    @Test
    public void logIsUpdatedByStatisticSelectionMessageWhenVarianceHadBeenSelected() {
        StatisticValue statistic = StatisticValue.VARIANCE;
        viewModel.selectedStatisticProperty().set(statistic);

        assertTrue(viewModel.getLog().get(0)
                .matches(".*" + LogMessages.statisticValueSelected(statistic)));
    }

    @Test
    public void rowInDataTableSelectedMessageIsAddToLogAfterSecondRowInDataTableHadBeenSelected() {
        int rowNumber = 2;
        String rowValue = viewModel.getStatisticData().get(rowNumber - 1).getValue();

        viewModel.selectRowInStatisticData(rowNumber);

        assertTrue(viewModel.getLog().get(0).matches(
                ".*" + LogMessages.rowInDataTableSelected(rowNumber, rowValue)));
    }

    @Test
    public void statisticValueIsNotLoggedWhenNotChanged() {
        viewModel.selectedStatisticProperty().set(StatisticValue.PROBABILITY);
        viewModel.selectedStatisticProperty().set(StatisticValue.PROBABILITY);

        assertEquals(viewModel.getLog().size(), 1);
    }

    @Test
    public void rowInDataTableDeletedMessageIsAddToLogAfterThirdRowInTableSelectedAndDeleted() {
        int rowNumber = 3;
        String rowValue = viewModel.getStatisticData().get(rowNumber - 1).getValue();

        viewModel.selectRowInStatisticData(3);
        viewModel.deleteSelectedRowInStatisticData();

        assertTrue(viewModel.getLog().get(1).matches(
                ".*" + LogMessages.rowInDataTableDeleted(rowNumber, rowValue)));
    }

    @Test
    public void dataTableIsClearedMessageIsAddToLogAfterDataTableHadBeenCleared() {
        viewModel.clearStatisticData();

        assertTrue(viewModel.getLog().get(0).matches(".*" + LogMessages.dataTableIsCleared()));
    }

    @Test
    public void statisticValueCalculatedMessageIsAddToLogAfterSelectedStatisticCalculated() {
        viewModel.calculateSelectedStatistic();

        assertTrue(viewModel.getLog().get(0)
                .matches(".*"
                        + LogMessages.statisticValueCalculated(viewModel.getSelectedStatistic(),
                        viewModel.getValueOfCalculatedStatistic(),
                        viewModel.getInputStatisticParameter())));
    }*/
}
