package ru.unn.agile.arabicroman.viewmodel;

import ru.unn.agile.arabicroman.model.NumeralConverter;
import java.util.List;


public class ArabicRomanConverterViewModel {

    private boolean convertButtonEnabled = false;
    private boolean isConvertedNumberArabic = true;
    private String inputNumber;
    private String outputNumber;
    private String oldInputNumber = "";
    private String errorMessage = "";
    private String inputNumberFormat = "Arabic Number";
    private String outputNumberFormat = "Roman Number";
    private final ILogger logger;

    public ArabicRomanConverterViewModel(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("ViewModel with null logger cannot be created");
        }

        this.logger = logger;
    }

    public String getOutputNumber() {
        return outputNumber;
    }

    public String getInputNumber() {
        return inputNumber;
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
        isConvertedNumberArabic = !isConvertedNumberArabic;
        exchangeTextForIONumbers();
        logger.add(reverseLogMessage());
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
        if (!inputNumber.equals(oldInputNumber)) {
            logger.add(enteredNumberMessage());
            oldInputNumber = inputNumber;
        }
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
        logger.add(convertLogMessage());
        try {
            if (isConvertedNumberArabic) {
                outputNumber = NumeralConverter.convert(Integer.parseInt(inputNumber));
            } else {
                outputNumber = String.valueOf(NumeralConverter.convert(inputNumber));
            }
            logger.add(successfulConvertLogMessage());
        } catch (Exception e) {
            errorMessage = "Illegal input number";
            logger.add(failedConvertLogMessage());
        }
    }

    public List<String> getLogMessages() {
        return logger.getMessages();
    }

    private String convertLogMessage() {
        return LogMessages.CONVERT_HAS_BEEN_PRESSED + "with argument: " + inputNumber
                + " as " + inputNumberFormat;
    }

    private String reverseLogMessage() {
        return LogMessages.REVERSE_HAS_BEEN_PRESSED + "Current converting direction is from "
                + inputNumberFormat + " into  " + outputNumberFormat;
    }

    private String successfulConvertLogMessage() {
        return LogMessages.SUCCESSFUL_CONVERT_OPERATION + "with result " + outputNumber
                + " as " + outputNumberFormat;
    }

    private String failedConvertLogMessage() {
        return LogMessages.FAILED_CONVERT_OPERATION;
    }

    private String enteredNumberMessage() {
        if (inputNumber.isEmpty()) {
            return LogMessages.ENTERED_NUMBER + "empty";
        } else {
            return LogMessages.ENTERED_NUMBER + inputNumber;
        }
    }
}

    final class LogMessages {
        public static final String ENTERED_NUMBER = "Entered number is ";
        public static final String CONVERT_HAS_BEEN_PRESSED = "Convert button has been pressed ";
        public static final String REVERSE_HAS_BEEN_PRESSED = "Reverse button has been pressed ";
        public static final String SUCCESSFUL_CONVERT_OPERATION = "Converted successfully ";
        public static final String FAILED_CONVERT_OPERATION =
                "Convert failed due to illegal argument ";

        private LogMessages() { }
}
