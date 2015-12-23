package ru.unn.agile.IntegrationMethods.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeNumericalIntegrationLogger implements NumericalIntegrationLogger {
    private ArrayList<String> records = new ArrayList<String>();

    @Override
    public void addRecord(final String newRecord) {
        records.add(newRecord);
    }

    @Override
    public List<String> getAllRecords() {
        return records;
    }
}
