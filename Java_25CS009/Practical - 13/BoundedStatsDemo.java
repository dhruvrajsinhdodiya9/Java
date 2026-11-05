public class BoundedStatsDemo {

    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }

        double sum = 0.0;
        for (N val : values) {
            if (val != null) {
                sum += val.doubleValue();
            }
        }
        return sum / values.length;
    }

    public static void main(String[] args) {
        Integer[] intArr = {10, 20, 30, 40, 50};
        Double[] doubleArr = {2.5, 3.5, 4.0, 5.0};
        Long[] longArr = {100L, 200L, 300L, 400L};

        System.out.println("=== Bounded Generics: average() ===");
        System.out.printf("Integer Array Average : %.2f%n", average(intArr));
        System.out.printf("Double Array Average  : %.2f%n", average(doubleArr));
        System.out.printf("Long Array Average    : %.2f%n", average(longArr));
    }
}