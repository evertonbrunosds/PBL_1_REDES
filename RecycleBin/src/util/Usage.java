package util;

public enum Usage {
    none, low, medium, high, total;

    public static String toString(final Usage usage) {
        switch (usage) {
            case none:
                return "NONE";
            case low:
                return "LOW";
            case medium:
                return "MEDIUM";
            case high:
                return "HIGH";
            case total:
                return "TOTAL";
            default:
                return "ERROR";
        }
    }

}
