package ru.unn.agile.arabicroman.viewmodel;

import ru.unn.agile.arabicroman.model.NumeralConverter;
import java.util.List;


public class ArabicRomanConverterViewModel {

    private boolean convertButtonEnabled = false;
    private boolean isConvertedNumberArabic = true;
    private String inputNumber;
    private String outputNumber;
    private String errorMessage = "";
    private String inputNumberFormat = "Arabic Number";
    private String outputNumberFormat = "Roman Number";
    private ILogger logger;

    public ArabicRomanConverterViewModel() { /**/ };
    public ArabicRomanConverterViewModel(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("ViewModel with null logger cannot be created");
        }

        this.logger = logger;
    }

    public String getOutputNumber() {
        return outputNumber;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getInputNumberFormat() {
        return inputNumberFormat;
    }

    public String getOutputNumberFormat() {
        return outputNumberFormat;
    }

    public boolean isConvertButtonEnabled() {
        return convertButtonEnabled;
    }

    public boolean isConvertedNumberArabic() {
        return isConvertedNumberArabic;
    }

    public void reverseConvertingDirection() {
        logger.addLogMessage(reverseLogMessage());
        isConvertedNumberArabic = !isConvertedNumberArabic;
        exchangeTextForIONumbers();
    }

    private void exchangeTextForIONumbers() {
        String tempString = inputNumberFormat;
        inputNumberFormat = outputNumberFormat;
        outputNumberFormat = tempString;
    }

    public void setInputNumber(final String inputNumber) {
        outputNumber = "";
        errorMessage = "";
        this.inputNumber = inputNumber;
        if (isConvertedNumberArabic) {
            try {
                Integer.parseInt(inputNumber);
                convertButtonEnabled = true;
            } catch (NumberFormatException e) {
                convertButtonEnabled = false;
            }
        } else {
            convertButtonEnabled = true;
        }
    }

    public void convert() {
        logger.addLogMessage(convertLogMessage());
        try {
            if (isConvertedNumberArabic) {
                outputNumber = NumeralConverter.convert(Integer.parseInt(inputNumber));
            } else {
                outputNumber = String.valueOf(NumeralConverter.convert(inputNumber));
            }
        } catch (Exception e) {
            errorMessage = "Illegal input number";
        }
    }

    public List<String> getLogMessages() {
        return logger.getLogMessages();
    }

    private String convertLogMessage() {
        return LogMessages.CONVERT_WAS_PRESSED + "with argument: " + inputNumber + "as " + inputNumberFormat;
    }

    private String reverseLogMessage() {
        return LogMessages.REVERSE_WAS_PRESSED + "Current cinverting direction is from "
                + inputNumberFormat + "into  " + outputNumberFormat;
    }
}

    final class LogMessages {
    public static final String CONVERT_WAS_PRESSED = "Convert button was pressed ";
    public static final String REVERSE_WAS_PRESSED = "Reverse button was pressed ";

    private LogMessages() { }
}
