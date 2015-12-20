package ru.unn.agile.PercentAccretion.Model;

public class PercentAccretionFactory {
    public enum AccretionType {
        SIMPLE_PERCENT_SUM("simple"),
        COMPLEX_PERCENT_SUM("complex");

        private String type;

        AccretionType(final String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }

    public PercentAccretion create(final AccretionType type) {
        switch (type) {
            case SIMPLE_PERCENT_SUM: return new SimplePercentAccretion();
            case COMPLEX_PERCENT_SUM: return new ComplexPercentAccretion();
            default: throw new IllegalArgumentException();
        }
    }
}
