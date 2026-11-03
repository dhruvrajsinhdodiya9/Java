import java.util.*;

public class WordFrequency {
    public static void main(String[] args) {
        String paragraph = "Java is powerful, robust, and versatile. Java multithreading makes Java fast! "
                         + "Is Java simple? Java is structured and robust.";

        // 1. Normalize: lowercase and replace punctuation/symbols with spaces
        String cleanText = paragraph.toLowerCase().replaceAll("[^a-z0-9\\s]", " ");

        // 2. Split on one or more whitespace characters
        String[] words = cleanText.trim().split("\\s+");

        // 3. Count frequencies in a HashMap
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
            }
        }

        // 4. Convert entrySet to a List for sorting
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(frequencyMap.entrySet());

        // 5. Sort using Comparator: descending by count, then ascending by word on ties
        entryList.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
                int freqComparison = b.getValue().compareTo(a.getValue()); // Descending
                if (freqComparison != 0) {
                    return freqComparison;
                }
                return a.getKey().compareTo(b.getKey()); // Alphabetical tie-breaker
            }
        });

        // 6. Display results
        System.out.println("=== Word Frequency (Highest First) ===");
        System.out.printf("%-15s %-10s%n", "Word", "Frequency");
        System.out.println("-------------------------");
        for (Map.Entry<String, Integer> entry : entryList) {
            System.out.printf("%-15s %-10d%n", entry.getKey(), entry.getValue());
        }
    }
}