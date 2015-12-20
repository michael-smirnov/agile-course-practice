package ru.unn.agile.WeightConverter.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.unn.agile.WeightConverter.Model.WeightUnit;
import ru.unn.agile.WeightConverter.viewmodel.ViewModel;
import ru.unn.agile.WeightConverter.infrastructure.WeightConverterTxtLogger;

import java.util.List;


public class Converter {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField txtInputValue;
    @FXML
    private ComboBox<WeightUnit> cbInputUnit;
    @FXML
    private ComboBox<WeightUnit> cbOutputUnit;
    @FXML
    private Button btnConv;

    @FXML
    void initialize() {
        ComboBox<WeightUnit>[] combo_boxes = new ComboBox[2];
        combo_boxes[0] = cbInputUnit;
        combo_boxes[1] = cbOutputUnit;

        viewModel.setLogger(new WeightConverterTxtLogger("./TxtLogger-lab3-weight-coverter.log"));

        final ChangeListener<Boolean> focusChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean oldValue, final Boolean newValue) {
                viewModel.onFocusChanged(oldValue, newValue);
            }
        };
        txtInputValue.textProperty().bindBidirectional(viewModel.valueProperty());
        txtInputValue.focusedProperty().addListener(focusChangeListener);

        cbInputUnit.valueProperty().bindBidirectional(viewModel.inputUnitProperty());
        cbOutputUnit.valueProperty().bindBidirectional(viewModel.outputUnitProperty());

        for(int i = 0; i < 2; i++) {
            combo_boxes[i].valueProperty().addListener(new ChangeListener<WeightUnit>() {
                @Override
                public void changed(final ObservableValue<? extends WeightUnit> observable,
                                    final WeightUnit oldValue, final WeightUnit newValue) {
                    viewModel.unitsChanged(oldValue, newValue);
                }
            });
        }

        btnConv.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.convert();
            }
        });
    }
}
