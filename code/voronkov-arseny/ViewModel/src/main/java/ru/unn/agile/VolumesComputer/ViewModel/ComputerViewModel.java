package ru.unn.agile.VolumesComputer.ViewModel;

import ru.unn.agile.VolumesComputer.Model.*;

public class ComputerViewModel {
    public static final String BAD_VOLUME_STRING
            = "I can't solve volume for this strange figure!";
    public static final String EMPTY_VOLUME_STRING = "";
    public static final String DISABLE_PARAMETER_STRING = "No parameter";
    private static final int THREE = 3;
    private String parameter1str;
    private String parameter2str;
    private String parameter3str;
    private double parameter1;
    private double parameter2;
    private double parameter3;
    private FigureName figureName;
    private boolean inputCorrect;
    private boolean parsed;
    private String volumeStr;

    public ComputerViewModel() {
        parameter1str = "";
        parameter2str = "";
        parameter3str = "";
        parameter1 = 0.0;
        parameter2 = 0.0;
        parameter3 = 0.0;
        figureName = FigureName.CUBOID;
        inputCorrect = false;
        parsed = false;
        volumeStr = EMPTY_VOLUME_STRING;
    }
    public void setParameter1(final String parameterString) {
        parsed = parameter1str.equals(parameterString);
        parameter1str = parameterString;
    }
    public void setParameter2(final String parameterString) {
        parsed = parameter2str.equals(parameterString);
        parameter2str = parameterString;
    }
    public void setParameter3(final String parameterString) {
        parsed = parameter3str.equals(parameterString);
        parameter3str = parameterString;
    }
    public void setFigure(final FigureName figureName) {
        parsed = this.figureName.equals(figureName);
        this.figureName = figureName;
    }
    public String getParameter1() {
        return parameter1str;
    }
    public String getParameter2() {
        return parameter2str;
    }
    public String getParameter3() {
        return parameter3str;
    }
    public FigureName getFigure() {
        return figureName;
    }
    public boolean isParameter1enabled() {
        return figureName.getParametersCount() >= 1;
    }
    public boolean isParameter2enabled() {
        return figureName.getParametersCount() >= 2;
    }
    public boolean isParameter3enabled() {
        return figureName.getParametersCount() >= THREE;
    }
    public boolean isInputCorrect() {
        if (!parsed) {
            parse();
        }
        return inputCorrect;
    }
    public String getVolume() {
        return volumeStr;
    }
    public String getParameter1name() {
        return getParameterName(1);
    }
    public String getParameter2name() {
        return getParameterName(2);
    }
    public String getParameter3name() {
        return getParameterName(THREE);
    }
    public void parse() {
        parsed = true;
        final int paramsCount = figureName.getParametersCount();
        inputCorrect = true;
        if (paramsCount < 1) {
            return;
        }
        if (paramsCount > THREE) {
            inputCorrect = false;
            return;
        }
        parameter1 = parseParameter(parameter1str);
        if (inputCorrect && paramsCount > 1) {
            parameter2 = parseParameter(parameter2str);
        }
        if (inputCorrect && paramsCount > 2) {
            parameter3 = parseParameter(parameter3str);
        }
    }
    public void solve() {
        if (!parsed) {
            parse();
        }
        if (inputCorrect) {
            try {
                if (figureName == FigureName.CUBOID) {
                    solveCuboid();
                } else if (figureName == FigureName.SPHEROID) {
                    solveSpheroid();
                } else if (figureName == FigureName.RIGHT_CYLINDER) {
                    solveRightCylinder();
                } else if (figureName == FigureName.RIGHT_CIRCULAR_CONE) {
                    solveRightCircularCone();
                } else {
                    volumeStr = BAD_VOLUME_STRING;
                }
            } catch (NegativeParametersException e) {
                volumeStr = BAD_VOLUME_STRING;
            }
        } else {
            volumeStr = EMPTY_VOLUME_STRING;
        }
    }

    private double parseParameter(final String parameterString) {
        double res = 0.0;
        try {
            res = Double.valueOf(parameterString);
        } catch (NumberFormatException e) {
            inputCorrect = false;
        }
        return res;
    }
    private String getParameterName(final int parameterIndex) {
        return figureName.getParametersCount() >= parameterIndex
                ? figureName.getParametersNames()[parameterIndex - 1]
                : DISABLE_PARAMETER_STRING;
    }
    private void solveCuboid() {
        volumeStr = String.valueOf(VolumesComputer.solve(new Cuboid(
                parameter1, parameter2, parameter3)));
    }
    private void solveSpheroid() {
        volumeStr = String.valueOf(VolumesComputer.solve(
                new Spheroid(parameter1, parameter2)));
    }
    private void solveRightCylinder() {
        volumeStr = String.valueOf(VolumesComputer.solve(new RightCylinder(
                parameter1, parameter2, parameter3)));
    }
    private void solveRightCircularCone() {
        volumeStr = String.valueOf(VolumesComputer.solve(
                new RightCircularCone(parameter1, parameter2)));
    }
}
