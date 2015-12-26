package ru.unn.agile.HexBinOctCalculator.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ru.unn.agile.HexBinOctCalculator.Model.HexBinOctCalculator.Operation;
import ru.unn.agile.HexBinOctCalculator.Model.NumeralSystem;
import ru.unn.agile.HexBinOctCalculator.infrastructure.RealLogger;
import ru.unn.agile.HexBinOctCalculator.viewmodel.ViewModel;

public class Calculator {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField txtValue1;
    @FXML
    private TextField txtValue2;
    @FXML
    private ComboBox<Operation> cbOperation;
    @FXML
    private ComboBox<NumeralSystem> cbSystem1;
    @FXML
    private ComboBox<NumeralSystem> cbSystem2;
    @FXML
    private ComboBox<NumeralSystem> cbSystemRes;
    @FXML
    private Button btnCalc;

    @FXML
    void initialize() {
        viewModel.setLogger(new RealLogger("./RealLogger.log"));

        final ChangeListener<Boolean> fieldChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean previousValue, final Boolean replacedValue) {
                viewModel.fieldChanged(previousValue, replacedValue);
            }
        };

        final ChangeListener<NumeralSystem> systemChangeListener
                = new ChangeListener<NumeralSystem>() {
            @Override
            public void changed(final ObservableValue<? extends NumeralSystem> observable,
                                final NumeralSystem previousValue,
                                final NumeralSystem replacedValue) {
                viewModel.systemChanged(previousValue, replacedValue);
            }
        };

        txtValue1.textProperty().bindBidirectional(viewModel.value1Property());
        txtValue1.focusedProperty().addListener(fieldChangeListener);

        txtValue2.textProperty().bindBidirectional(viewModel.value2Property());
        txtValue2.focusedProperty().addListener(fieldChangeListener);

        cbSystem1.valueProperty().bindBidirectional(viewModel.system1Property());
        cbSystem1.valueProperty().addListener(systemChangeListener);

        cbSystem2.valueProperty().bindBidirectional(viewModel.system2Property());
        cbSystem2.valueProperty().addListener(systemChangeListener);

        cbSystemRes.valueProperty().bindBidirectional(viewModel.finalSystemProperty());
        cbSystemRes.valueProperty().addListener(new ChangeListener<NumeralSystem>() {
            @Override
            public void changed(final ObservableValue<? extends NumeralSystem> observable,
                                final NumeralSystem previousValue,
                                final NumeralSystem replacedValue) {
                viewModel.resultSystemChanged(previousValue, replacedValue);
            }
        });

        cbOperation.valueProperty().bindBidirectional(viewModel.operationProperty());
        cbOperation.valueProperty().addListener(new ChangeListener<Operation>() {
            @Override
            public void changed(final ObservableValue<? extends Operation> observable,
                                final Operation previousValue, final Operation replacedValue) {
                viewModel.operationChanged(previousValue, replacedValue);
            }
        });

        btnCalc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.calculate();
            }
        });

        cbSystemRes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.calculate();
            }
        });
    }
}
