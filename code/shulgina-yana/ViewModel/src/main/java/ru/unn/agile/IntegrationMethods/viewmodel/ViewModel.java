package ru.unn.agile.IntegrationMethods.viewmodel;

import ru.unn.agile.IntegrationMethods.Model.*;

import java.util.List;

public class ViewModel {
    private Function function;
    private String lowerLimit;
    private String upperLimit;
    private IntegrationMethod integrationMethod;
    private String result;
    private String status;
    private boolean isIntegrateButtonEnabled;
    private NumericalIntegrationLogger logger;
    private boolean isLowerLimitChanged;
    private boolean isUpperLimitChanged;

    public ViewModel(final NumericalIntegrationLogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Error: logger is null");
        }
        this.logger = logger;
        function = Function.X;
        lowerLimit = "";
        upperLimit = "";
        integrationMethod = IntegrationMethod.LEFT_RECTANGLES;
        result = "";
        status = Status.WAITING.toString();
        isIntegrateButtonEnabled = false;
        isLowerLimitChanged = false;
        isUpperLimitChanged = false;
    }

    private boolean isThereEmptyTextField() {
        return lowerLimit.isEmpty() || upperLimit.isEmpty();
    }

    private boolean parseLimitsInput() {
        try {
            if (!lowerLimit.isEmpty()) {
                Double.parseDouble(lowerLimit);
            }
            if (!upperLimit.isEmpty()) {
                Double.parseDouble(upperLimit);
            }
        } catch (Exception e) {
            status = Status.BAD_FORMAT.toString();
            isIntegrateButtonEnabled = false;
            return false;
        }
        if (isThereEmptyTextField()) {
            status = Status.WAITING.toString();
            isIntegrateButtonEnabled = false;
        } else {
            isIntegrateButtonEnabled = true;
            status = Status.READY.toString();
        }
        return isIntegrateButtonEnabled;
    }

    private String formRecordForLoggerAfterIntegration() {
        String record = RecordsTemplatesForLogger.INTEGRATE_WAS_PRESSED
                + "Lower limit = " + lowerLimit
                + ", upper limit = " + upperLimit
                + ", function = " + function.toString()
                + ", integration method: " + integrationMethod.toString()
                + ", status: " + status
                + ", result = " + result
                + ".";

        return record;
    }

    public void integrate() {
        if (!parseLimitsInput()) {
            return;
        }

        Integrator integrator = new Integrator(Double.parseDouble(lowerLimit),
                Double.parseDouble(upperLimit), function.getIFunctionObject());

        switch (integrationMethod) {
            case LEFT_RECTANGLES:
                integrator.leftRectangles();
                break;
            case RIGHT_RECTANGLES:
                integrator.rightRectangles();
                break;
            case MIDPOINT_RECTANGLES:
                integrator.midpointRectangles();
                break;
            case TRAPEZES:
                integrator.trapezes();
                break;
            case SIMPSON:
                integrator.simpson();
                break;
            default:
                throw new IllegalArgumentException("This method is not supported");
        }

        result = Double.toString(integrator.getIntegralValue());
        status = Status.SUCCESS.toString();
        logger.addRecord(formRecordForLoggerAfterIntegration());
    }

    public void processKeyPressing(final int keyCode) {
        parseLimitsInput();

        if (keyCode == KeyboardKeys.ENTER && isIntegrateButtonEnabled()) {
            integrate();
        }
    }

    public void lowerLimitHasLostFocus() {
        if (isLowerLimitChanged) {
            logger.addRecord(RecordsTemplatesForLogger.LOWER_LIMIT_WAS_CHANGED.toString()
                    + lowerLimit + ", status: " + status + ", result = " + result);
            isLowerLimitChanged = false;
        }
    }

    public void upperLimitHasLostFocus() {
        if (isUpperLimitChanged) {
            logger.addRecord(RecordsTemplatesForLogger.UPPER_LIMIT_WAS_CHANGED.toString()
                    + upperLimit + ", status: " + status + ", result = " + result);
            isUpperLimitChanged = false;
        }
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(final Function function) {
        if (this.function != function) {
            this.function = function;
            logger.addRecord(RecordsTemplatesForLogger.FUNCTION_WAS_CHANGED.toString()
                    + this.function.toString() + ", status: " + status + ", result = "
                    + result);
        }
    }

    public String getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(final String lowerLimit) {
        if (!this.lowerLimit.equals(lowerLimit)) {
            this.lowerLimit = lowerLimit;
            isLowerLimitChanged = true;
        }
    }

    public String getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(final String upperLimit) {
        if (!this.upperLimit.equals(upperLimit)) {
            this.upperLimit = upperLimit;
            isUpperLimitChanged = true;
        }
    }

    public IntegrationMethod getIntegrationMethod() {
        return integrationMethod;
    }

    public void setIntegrationMethod(final IntegrationMethod integrationMethod) {
        if (this.integrationMethod != integrationMethod) {
            this.integrationMethod = integrationMethod;
            logger.addRecord(RecordsTemplatesForLogger.METHOD_WAS_CHANGED.toString()
                    + this.integrationMethod.toString() + ", status: " + status
                    + ", result = " + result);
        }
    }

    public String getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

    public boolean isIntegrateButtonEnabled() {
        return isIntegrateButtonEnabled;
    }

    public List<String> getLoggersRecords() {
        return logger.getAllRecords();
    }

    public enum Function {
        X("x") {
            @Override
            public IFunction getIFunctionObject() {
                return new XFunction();
            }
        },
        COS("cos(x)") {
            @Override
            public IFunction getIFunctionObject() {
                return new CosFunction();
            }
        },
        EXP("exp(x)") {
            @Override
            public IFunction getIFunctionObject() {
                return new ExpFunction();
            }
        };
        private final String name;

        private Function(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public abstract IFunction getIFunctionObject();
    }

    public enum IntegrationMethod {
        LEFT_RECTANGLES("left rectangles method"),
        RIGHT_RECTANGLES("right rectangles method"),
        MIDPOINT_RECTANGLES("midpoint rectangles method"),
        TRAPEZES("trapezes method"),
        SIMPSON("Simpson method");
        private final String name;

        private IntegrationMethod(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum Status {
        WAITING("Please provide input data"),
        READY ("Press 'Integrate' or Enter"),
        BAD_FORMAT("Bad format"),
        SUCCESS("Success");
        private final String name;

        private Status(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum RecordsTemplatesForLogger {
        INTEGRATE_WAS_PRESSED("Integrate. "),
        LOWER_LIMIT_WAS_CHANGED("Lower limit was changed to "),
        UPPER_LIMIT_WAS_CHANGED("Upper limit was changed to "),
        FUNCTION_WAS_CHANGED("Function was changed to "),
        METHOD_WAS_CHANGED("Integration method was changed to ");
        private final String name;

        private RecordsTemplatesForLogger(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
