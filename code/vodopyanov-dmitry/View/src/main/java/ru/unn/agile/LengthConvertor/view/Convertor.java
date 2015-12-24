package ru.unn.agile.LengthConvertor.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ru.unn.agile.LengthConvertor.Model.LengthUnit;
import ru.unn.agile.LengthConvertor.viewmodel.ViewModel;
import ru.unn.agile.LengthConvertor.infrastructure.LengthConvertorLogger;

public class Convertor {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField inputValueTextBox;
    @FXML
    private ComboBox<LengthUnit> inputUnitComboBox;
    @FXML
    private ComboBox<LengthUnit> outputUnitComboBox;
    @FXML
    private Button convertButton;

    @FXML
    void initialize() {
        viewModel.setLogger(new LengthConvertorLogger("./LengthConvertorLogger.xml"));

        final ChangeListener<Boolean> focusValueChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean oldValue,
                                final Boolean newValue) {
                viewModel.valueChanged(oldValue, newValue);
            }
        };

        final ChangeListener<LengthUnit> focusLengthUnitChangeListener =
                                                              new ChangeListener<LengthUnit>() {
            @Override
            public void changed(final ObservableValue<? extends LengthUnit> observable,
                                final LengthUnit oldValue,
                                final LengthUnit newValue) {
                viewModel.lengthUnitsChanged(oldValue, newValue);
            }
        };

        inputValueTextBox.textProperty().bindBidirectional(viewModel.inputValueProperty());
        inputValueTextBox.focusedProperty().addListener(focusValueChangeListener);

        inputUnitComboBox.valueProperty().bindBidirectional(viewModel.inputUnitProperty());
        inputUnitComboBox.valueProperty().addListener(focusLengthUnitChangeListener);

        outputUnitComboBox.valueProperty().bindBidirectional(viewModel.outputUnitProperty());
        outputUnitComboBox.valueProperty().addListener(focusLengthUnitChangeListener);

        convertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.convert();
            }
        });
    }
}
