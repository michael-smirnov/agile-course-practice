package ru.unn.agile.VolumesComputer.ViewModel;

public enum FigureName {
    CUBOID("Cuboid", new String[] {
            "Width", "Height", "Length"
    }),
    SPHEROID("Spheroid", new String[] {
            "Semimajor axis", "Semiminor axis"
    }),
    RIGHT_CYLINDER("Right cylinder", new String[] {
            "Semimajor axis", "Semiminor axis", "Height"
    }),
    RIGHT_CIRCULAR_CONE("Right circular cone", new String[] {
            "Radius", "Height"
    });

    private final String name;
    private final int parametersCount;
    private final String[] parametersNames;

    FigureName(final String name,
               final String[] parametersNames) {
        this.name = name;
        parametersCount = parametersNames.length;
        this.parametersNames = parametersNames.clone();
    }
    @Override
    public String toString() {
        return name;
    }
    public int getParametersCount() {
        return parametersCount;
    }
    public String[] getParametersNames() {
        return parametersNames;
    }
}
