public class ChatFilter {
    public static boolean filterLine(String line, String keyword, StringBuilder report) {
        if (line == null) {
            return false;
        }

        String[] parts = line.split(" ", 3);
        if (parts.length < 3) {
            return false;
        }

        String time = parts[0];
        String user = parts[1];
        String message = parts[2];

        if (message.toLowerCase().contains(keyword.toLowerCase())) {
            report.append(time).append(" ").append(user).append(": ").append(message).append("\n");
            return true;
        }

        return false;
    }
}