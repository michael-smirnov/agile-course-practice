package ru.unn.agile.StatisticValueCalculator.viewmodel;

final class LogMessages {
    private LogMessages() {
    }

    public static String inputRowValueIsSet(final String value) {
        return "<Changed input row value>: " + value;
    }

    public static String inputParameterValueIsSet(final StatisticParameter parameter,
                                                  final String value) {
        return "<Changed parameter '" + parameter + "' value>: " + value;
    }

    public static String newValueToDataTableIsAdded(final String value) {
        return "<New value to statistic data is added>: " + value;
    }

    public static String statisticValueSelected(final StatisticValue statistic) {
        return "<Selected statistic>: " + statistic;
    }

    public static String rowInDataTableSelected(final int rowNumber, final String rowValue) {
        return "<Selected row in data table>: row number = " + rowNumber
                + ", row value = " + rowValue;
    }

    public static String rowInDataTableDeleted(final int rowNumber, final String rowValue) {
        return "<Deleted row in data table>: row number = " + rowNumber
                + ", row value = " + rowValue;
    }

    public static String dataTableIsCleared() {
        return "<Data table is cleared>";
    }

    public static String statisticValueCalculated(final StatisticValue statistic,
                                                  final String calculatedValue,
                                                  final String parameterValue) {
        String message = "<Statistic value is calculated>: name = " + statistic
                + ", value = " + calculatedValue;
        if (statistic.getParameter() != null) {
            message += ", " + statistic.getParameter() + " value = " + parameterValue;
        }
        return message;
    }
}
