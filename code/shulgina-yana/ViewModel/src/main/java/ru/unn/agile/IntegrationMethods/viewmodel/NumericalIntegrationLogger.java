package ru.unn.agile.IntegrationMethods.viewmodel;

import java.util.List;

public interface NumericalIntegrationLogger {
    void addRecord(final String newRecord);
    List<String> getAllRecords();
}
