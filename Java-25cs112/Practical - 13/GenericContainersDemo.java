public class GenericContainersDemo {

    // 1. Generic single-item container
    static class Box<T> {
        private T value;

        public Box(T value) {
            this.value = value;
        }

        public T get() {
            return value;
        }

        public void set(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Box[" + value + "]";
        }
    }

    // 2. Generic two-item container with distinct type parameters
    static class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "(" + key + " -> " + value + ")";
        }
    }

    // 3. Generic method returning the first element of any array
    public static <T> T firstElement(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return array[0];
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Generic Box Demonstrations ===");
        // Box holding a String
        Box<String> strBox = new Box<>("Hello Java Generics");
        String text = strBox.get(); // No casting required!
        System.out.println("String Box: " + text + " | Length: " + text.length());

        // Box holding an Integer
        Box<Integer> intBox = new Box<>(42);
        int number = intBox.get(); // Auto-unboxing works directly
        System.out.println("Integer Box: " + (number * 2));

        System.out.println("\n=== 2. Generic Pair Demonstrations ===");
        // Pair with String key and Double value
        Pair<String, Double> productPrice = new Pair<>("Laptop", 1299.99);
        System.out.println("Item: " + productPrice.getKey() + ", Price: $" + productPrice.getValue());

        // Pair with Integer key and String value
        Pair<Integer, String> studentRecord = new Pair<>(101, "Alice");
        System.out.println("Student ID: " + studentRecord.getKey() + ", Name: " + studentRecord.getValue());

        System.out.println("\n=== 3. Generic Method: firstElement() ===");
        String[] fruits = {"Apple", "Banana", "Cherry"};
        Integer[] scores = {98, 85, 76, 92};
        Double[] weights = {12.5, 45.2, 7.8};

        // The compiler automatically infers the return type T
        String firstFruit = firstElement(fruits);     // Returns String
        Integer firstScore = firstElement(scores);    // Returns Integer
        Double firstWeight = firstElement(weights);   // Returns Double

        System.out.println("First fruit: " + firstFruit);
        System.out.println("First score: " + firstScore);
        System.out.println("First weight: " + firstWeight);
    }
}