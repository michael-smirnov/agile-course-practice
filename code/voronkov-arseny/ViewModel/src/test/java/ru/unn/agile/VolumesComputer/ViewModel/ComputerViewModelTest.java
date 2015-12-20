package ru.unn.agile.VolumesComputer.ViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.VolumesComputer.Model.*;

import static org.junit.Assert.*;

public class ComputerViewModelTest {
    private ComputerViewModel viewModel;

    @Before
    public void initialize() {
        viewModel = new ComputerViewModel();
    }
    @After
    public void free() {
        viewModel = null;
    }
    @Test
    public void setParameter1() {
        final String param = "2.0";
        viewModel.setParameter1(param);
        assertEquals(viewModel.getParameter1(), param);
    }
    @Test
    public void setParameter2() {
        final String param = "3.0";
        viewModel.setParameter2(param);
        assertEquals(viewModel.getParameter2(), param);
    }
    @Test
    public void setParameter3() {
        final String param = "4.0";
        viewModel.setParameter3(param);
        assertEquals(viewModel.getParameter3(), param);
    }
    @Test
    public void setFigure() {
        final FigureName figure = FigureName.RIGHT_CYLINDER;
        viewModel.setFigure(figure);
        assertEquals(viewModel.getFigure(), figure);
    }

    @Test
    public void cuboidParametersEnabling() {
        viewModel.setFigure(FigureName.CUBOID);
        assertTrue(viewModel.isParameter1enabled()
                && viewModel.isParameter2enabled()
                && viewModel.isParameter3enabled());
    }
    @Test
    public void spheroidParametersEnabling() {
        viewModel.setFigure(FigureName.SPHEROID);
        assertTrue(viewModel.isParameter1enabled()
                && viewModel.isParameter2enabled()
                && !viewModel.isParameter3enabled());
    }
    @Test
    public void rightCylinderParametersEnabling() {
        viewModel.setFigure(FigureName.RIGHT_CYLINDER);
        assertTrue(viewModel.isParameter1enabled()
                && viewModel.isParameter2enabled()
                && viewModel.isParameter3enabled());
    }
    @Test
    public void rightCircularConeParametersEnabling() {
        viewModel.setFigure(FigureName.RIGHT_CIRCULAR_CONE);
        assertTrue(viewModel.isParameter1enabled()
                && viewModel.isParameter2enabled()
                && !viewModel.isParameter3enabled());
    }

    @Test
    public void parseTwoGoodParameters() {
        viewModel.setFigure(FigureName.SPHEROID);
        viewModel.setParameter1("2.0");
        viewModel.setParameter2("3.0");
        viewModel.parse();
        assertTrue(viewModel.isInputCorrect());
    }
    @Test
    public void parseTwoBadParameters() {
        viewModel.setFigure(FigureName.SPHEROID);
        viewModel.setParameter1("2.0");
        viewModel.setParameter2("oioioi");
        viewModel.parse();
        assertFalse(viewModel.isInputCorrect());
    }
    @Test
    public void parseTwoEmptyParameters() {
        viewModel.setFigure(FigureName.SPHEROID);
        viewModel.setParameter1("");
        viewModel.setParameter2("");
        viewModel.parse();
        assertFalse(viewModel.isInputCorrect());
    }

    @Test
    public void parseThreeGoodParameters() {
        viewModel.setFigure(FigureName.CUBOID);
        viewModel.setParameter1("2.0");
        viewModel.setParameter2("3.0");
        viewModel.setParameter3("4.0");
        viewModel.parse();
        assertTrue(viewModel.isInputCorrect());
    }
    @Test
    public void parseThreeBadParameters() {
        viewModel.setFigure(FigureName.CUBOID);
        viewModel.setParameter1("2.0");
        viewModel.setParameter2("3.0");
        viewModel.setParameter3("oioioi");
        viewModel.parse();
        assertFalse(viewModel.isInputCorrect());
    }
    @Test
    public void parseThreeEmptyParameters() {
        viewModel.setFigure(FigureName.CUBOID);
        viewModel.setParameter1("");
        viewModel.setParameter2("");
        viewModel.setParameter3("");
        viewModel.parse();
        assertFalse(viewModel.isInputCorrect());
    }

    @Test
    public void solveBadParameters() {
        viewModel.setParameter1("hehehe");
        viewModel.solve();
        assertEquals(viewModel.getVolume(),
                ComputerViewModel.EMPTY_VOLUME_STRING);
    }

    @Test
    public void solveCuboidGoodParameters() {
        viewModel.setFigure(FigureName.CUBOID);
        viewModel.setParameter1("2.0");
        viewModel.setParameter2("3.0");
        viewModel.setParameter3("4.0");
        viewModel.solve();
        assertEquals(viewModel.getVolume(), String.valueOf(
                VolumesComputer.solve(new Cuboid(2.0, 3.0, 4.0))));
    }
    @Test
    public void solveCuboidNegativeParameters() {
        viewModel.setFigure(FigureName.CUBOID);
        viewModel.setParameter1("2.0");
        viewModel.setParameter2("3.0");
        viewModel.setParameter3("-4.0");
        viewModel.solve();
        assertEquals(viewModel.getVolume(),
                ComputerViewModel.BAD_VOLUME_STRING);
    }

    @Test
    public void solveSpheroidGoodParameters() {
        viewModel.setFigure(FigureName.SPHEROID);
        viewModel.setParameter1("2.0");
        viewModel.setParameter2("3.0");
        viewModel.solve();
        assertEquals(viewModel.getVolume(), String.valueOf(
                VolumesComputer.solve(new Spheroid(2.0, 3.0))));
    }
    @Test
    public void solveSpheroidNegativeParameters() {
        viewModel.setFigure(FigureName.SPHEROID);
        viewModel.setParameter1("2.0");
        viewModel.setParameter2("-3.0");
        viewModel.solve();
        assertEquals(viewModel.getVolume(),
                ComputerViewModel.BAD_VOLUME_STRING);
    }

    @Test
    public void solveRightCylinderGoodParameters() {
        viewModel.setFigure(FigureName.RIGHT_CYLINDER);
        viewModel.setParameter1("2.0");
        viewModel.setParameter2("3.0");
        viewModel.setParameter3("4.0");
        viewModel.solve();
        assertEquals(viewModel.getVolume(), String.valueOf(
                VolumesComputer.solve(new RightCylinder(2.0, 3.0, 4.0))));
    }
    @Test
    public void solveRightCylinderNegativeParameters() {
        viewModel.setFigure(FigureName.RIGHT_CYLINDER);
        viewModel.setParameter1("2.0");
        viewModel.setParameter2("-3.0");
        viewModel.setParameter3("4.0");
        viewModel.solve();
        assertEquals(viewModel.getVolume(),
                ComputerViewModel.BAD_VOLUME_STRING);
    }

    @Test
    public void solveRightCircularConeGoodParameters() {
        viewModel.setFigure(FigureName.RIGHT_CIRCULAR_CONE);
        viewModel.setParameter1("2.0");
        viewModel.setParameter2("3.0");
        viewModel.solve();
        assertEquals(viewModel.getVolume(), String.valueOf(
                VolumesComputer.solve(new RightCircularCone(2.0, 3.0))));
    }
    @Test
    public void solveRightCircularConeNegativeParameters() {
        viewModel.setFigure(FigureName.RIGHT_CIRCULAR_CONE);
        viewModel.setParameter1("2.0");
        viewModel.setParameter2("-3.0");
        viewModel.solve();
        assertEquals(viewModel.getVolume(),
                ComputerViewModel.BAD_VOLUME_STRING);
    }

    @Test
    public void parametersNamesCuboid() {
        viewModel.setFigure(FigureName.CUBOID);
        assertArrayEquals(FigureName.CUBOID.getParametersNames(),
                new String[] {
                        viewModel.getParameter1name(),
                        viewModel.getParameter2name(),
                        viewModel.getParameter3name()
                });
    }
    @Test
    public void parametersNamesSpheroid() {
        viewModel.setFigure(FigureName.SPHEROID);
        assertEquals(viewModel.getParameter3name(),
                viewModel.DISABLE_PARAMETER_STRING);
        assertArrayEquals(FigureName.SPHEROID.getParametersNames(),
                new String[] {
                        viewModel.getParameter1name(),
                        viewModel.getParameter2name()
                });
    }
    @Test
    public void parametersNamesRightCylinder() {
        viewModel.setFigure(FigureName.RIGHT_CYLINDER);
        assertArrayEquals(FigureName.RIGHT_CYLINDER.getParametersNames(),
                new String[] {
                        viewModel.getParameter1name(),
                        viewModel.getParameter2name(),
                        viewModel.getParameter3name()
                });
    }
    @Test
    public void parametersNamesRightCircularCone() {
        viewModel.setFigure(FigureName.RIGHT_CIRCULAR_CONE);
        assertEquals(viewModel.getParameter3name(),
                viewModel.DISABLE_PARAMETER_STRING);
        assertArrayEquals(FigureName.RIGHT_CIRCULAR_CONE.getParametersNames(),
                new String[] {
                        viewModel.getParameter1name(),
                        viewModel.getParameter2name()
                });
    }
}
