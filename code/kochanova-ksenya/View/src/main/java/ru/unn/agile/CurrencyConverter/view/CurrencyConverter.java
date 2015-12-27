package ru.unn.agile.CurrencyConverter.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ru.unn.agile.CurrencyConverter.Model.UnitCurrency;
import ru.unn.agile.CurrencyConverter.infrastructure.CurrencyConverterLogger;
import ru.unn.agile.CurrencyConverter.viewmodel.ViewModel;


public class CurrencyConverter {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField inputValue;
    @FXML
    private ComboBox<UnitCurrency> inputCurrency;
    @FXML
    private ComboBox<UnitCurrency> outputCurrency;
    @FXML
    private Button btnConvert;

    @FXML
    void initialize() {
        viewModel.setLogger(new CurrencyConverterLogger("./CurrencyConverterLogger-lab3.log"));

        final ChangeListener<Boolean> focusChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean valueBefore, final Boolean valueAfter) {
                viewModel.onFocusChanged(valueBefore, valueAfter);
            }
        };
        inputValue.textProperty().bindBidirectional(viewModel.inputValueProperty());
        inputValue.focusedProperty().addListener(focusChangeListener);

        inputCurrency.valueProperty().bindBidirectional(viewModel.inputUnitProperty());

        outputCurrency.valueProperty().bindBidirectional(viewModel.outputUnitProperty());
        btnConvert.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {

                viewModel.convert();
            }
        });
        inputCurrency.valueProperty().addListener(new ChangeListener<UnitCurrency>() {
            @Override
            public void changed(final ObservableValue<? extends UnitCurrency> observable,
                                final UnitCurrency valueBefore,
                                final UnitCurrency valueAfter) {
                viewModel.onUnitChanged(valueBefore, valueAfter);
            }
        });
    }
}
