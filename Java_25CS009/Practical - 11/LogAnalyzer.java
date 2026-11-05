import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;

public class LogAnalyzer {

    public static void main(String[] args) throws IOException {
        Path logDir = Paths.get("logs_temp");
        if (!Files.exists(logDir)) {
            Files.createDirectory(logDir);
        }

        // 1. Create mock log files for testing
        setupSampleLogs(logDir);

        String keyword = "ERROR";
        long grandTotalLines = 0;
        long totalKeywordMatches = 0;

        System.out.println("=== Log File Summary ===");
        System.out.printf("%-20s %-12s %-12s %-12s%n", "File Name", "Size (bytes)", "Lines", "Keyword Matches");
        System.out.println("------------------------------------------------------------");

        // 2. Iterate through files using DirectoryStream
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(logDir, "*.log")) {
            for (Path filePath : stream) {
                // File size attribute
                long fileSizeBytes = Files.size(filePath);

                // Read line-by-line efficiently using Stream<String>
                long fileLineCount = 0;
                long fileKeywordCount = 0;

                try (Stream<String> lines = Files.lines(filePath)) {
                    List<String> allLines = lines.toList();
                    fileLineCount = allLines.size();
                    fileKeywordCount = allLines.stream()
                                               .filter(line -> line.contains(keyword))
                                               .count();
                }

                grandTotalLines += fileLineCount;
                totalKeywordMatches += fileKeywordCount;

                System.out.printf("%-20s %-12d %-12d %-12d%n",
                        filePath.getFileName(), fileSizeBytes, fileLineCount, fileKeywordCount);
            }
        }

        System.out.println("------------------------------------------------------------");
        System.out.println("Target Keyword      : " + keyword);
        System.out.println("Grand Total Lines   : " + grandTotalLines);
        System.out.println("Total Matches Found : " + totalKeywordMatches);

        // Cleanup test directory
        cleanup(logDir);
    }

    private static void setupSampleLogs(Path dir) throws IOException {
        Files.write(dir.resolve("auth.log"), List.of(
            "INFO User alice logged in",
            "ERROR Failed password for root from 192.168.1.10",
            "WARN Session expired for user bob",
            "ERROR Invalid authentication token"
        ));

        Files.write(dir.resolve("app.log"), List.of(
            "INFO Application booted on port 8080",
            "INFO Healthcheck OK",
            "DEBUG Database latency: 4ms"
        ));

        Files.write(dir.resolve("database.log"), List.of(
            "INFO Connected to pool",
            "ERROR Deadlock detected on transaction #4091",
            "INFO Rolling back transaction #4091",
            "ERROR Connection pool exhausted",
            "INFO Retrying in 5 seconds..."
        ));
    }

    private static void cleanup(Path dir) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                Files.delete(path);
            }
        }
        Files.delete(dir);
    }
}