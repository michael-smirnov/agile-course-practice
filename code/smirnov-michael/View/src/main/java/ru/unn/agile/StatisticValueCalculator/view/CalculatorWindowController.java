package ru.unn.agile.StatisticValueCalculator.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.Pair;
import ru.unn.agile.StatisticValueCalculator.infrastructure.TxtLoggerOfStatisticCalculator;
import ru.unn.agile.StatisticValueCalculator.viewmodel.StatisticValue;
import ru.unn.agile.StatisticValueCalculator.viewmodel.StatisticCalculatorViewModel;

public class CalculatorWindowController {
    @FXML
    private TextArea logArea;
    @FXML
    private TableColumn<Pair<String, String>, String> columnNumber;
    @FXML
    private TableColumn<Pair<String, String>, String> columnValue;
    @FXML
    private StatisticCalculatorViewModel viewModel;
    @FXML
    private Button clearTableButton;
    @FXML
    private TableView<Pair<String, String>> tableData;
    @FXML
    private ChoiceBox<StatisticValue> statisticsList;
    @FXML
    private Button calculateButton;
    @FXML
    private TextField textStatisticParameterValue;
    @FXML
    private Button deleteRowButton;
    @FXML
    private Button addRowButton;
    @FXML
    private TextField textRowValue;

    private abstract class CellFactory implements
            Callback<TableColumn.CellDataFeatures<Pair<String, String>, String>,
                    ObservableValue<String>> {
        @Override
        public ObservableValue<String> call(
                final TableColumn.CellDataFeatures<Pair<String, String>, String> param) {
            return makePropertyForCell(param.getValue());
        }

        public abstract ObservableValue<String> makePropertyForCell(
                final Pair<String, String> rowData);
    }

    @FXML
    private void initialize() {
        viewModel.setLogger(new TxtLoggerOfStatisticCalculator("StatisticCalculatorLogger.log"));

        final ChangeListener<Boolean> inputFieldFocusChangeListener =
                new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean oldValue, final Boolean newValue) {
                viewModel.onInputFieldFocusChanged(oldValue, newValue);
            }
        };

        textRowValue.textProperty()
                .bindBidirectional(viewModel.inputRowProperty());
        textRowValue.focusedProperty().addListener(inputFieldFocusChangeListener);

        textStatisticParameterValue.textProperty()
                .bindBidirectional(viewModel.inputStatisticParameterProperty());
        textStatisticParameterValue.focusedProperty().addListener(inputFieldFocusChangeListener);

        addRowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.addRowToStatisticData();
            }
        });

        deleteRowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.deleteSelectedRowInStatisticData();
            }
        });

        clearTableButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.clearStatisticData();
            }
        });

        calculateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.calculateSelectedStatistic();
            }
        });

        tableData.setItems(viewModel.getStatisticData());

        columnNumber.setCellValueFactory(new CellFactory() {
            @Override
            public ObservableValue<String> makePropertyForCell(
                    final Pair<String, String> rowData) {
                return new SimpleStringProperty(rowData.getKey());
            }
        });

        columnValue.setCellValueFactory(new CellFactory() {
            @Override
            public ObservableValue<String> makePropertyForCell(
                    final Pair<String, String> rowData) {
                return new SimpleStringProperty(rowData.getValue());
            }
        });

        tableData.getFocusModel()
                .focusedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(final ObservableValue<? extends Number> observable,
                                        final Number oldValue, final Number newValue) {
                        viewModel.selectRowInStatisticData(newValue.intValue() + 1);
                    }
                });

        statisticsList.getSelectionModel()
                .selectedItemProperty()
                .addListener(new ChangeListener<StatisticValue>() {
                    @Override
                    public void changed(final ObservableValue<? extends StatisticValue> observable,
                                        final StatisticValue oldValue,
                                        final StatisticValue newValue) {
                        viewModel.setSelectedStatistic(newValue);
                    }
                });
    }
}
