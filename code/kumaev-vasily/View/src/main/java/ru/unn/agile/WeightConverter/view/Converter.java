package ru.unn.agile.WeightConverter.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.unn.agile.WeightConverter.Model.WeightUnit;
import ru.unn.agile.WeightConverter.viewmodel.ViewModel;
import ru.unn.agile.WeightConverter.infrastructure.WeightConverterCsvLogger;

public class Converter {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField txtInputValue;
    @FXML
    private ComboBox<WeightUnit> comboBoxInputUnit;
    @FXML
    private ComboBox<WeightUnit> comboBoxOutputUnit;
    @FXML
    private Button buttonConvert;

    @FXML
    void initialize() {
        final ChangeListener<Boolean> focusChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean oldValue, final Boolean newValue) {
                viewModel.onFocusChanged(oldValue, newValue);
            }
        };
        final ChangeListener<WeightUnit> focusWeightUnitChangeListener =
                new ChangeListener<WeightUnit>() {
                    @Override
                    public void changed(final ObservableValue<? extends WeightUnit> observable,
                                        final WeightUnit oldValue, final WeightUnit newValue) {
                        viewModel.unitsChanged(oldValue, newValue);
                    }
                };

        viewModel.setLogger(new WeightConverterCsvLogger("./TxtLogger-lab3-weight-converter.csv"));

        txtInputValue.textProperty().bindBidirectional(viewModel.valueProperty());
        txtInputValue.focusedProperty().addListener(focusChangeListener);
        comboBoxInputUnit.valueProperty().bindBidirectional(viewModel.inputUnitProperty());
        comboBoxOutputUnit.valueProperty().bindBidirectional(viewModel.outputUnitProperty());
        comboBoxInputUnit.valueProperty().addListener(focusWeightUnitChangeListener);
        comboBoxOutputUnit.valueProperty().addListener(focusWeightUnitChangeListener);

        buttonConvert.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.convert();
            }
        });
    }
}
