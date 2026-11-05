import java.io.*;
import java.util.Arrays;

// Must implement Serializable to permit serialization
class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private double gpa;

    // transient fields are ignored by ObjectOutputStream
    private transient String tempSessionToken;

    public Student(int id, String name, double gpa, String tempSessionToken) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
        this.tempSessionToken = tempSessionToken;
    }

    @Override
    public String toString() {
        return String.format("Student[ID=%d, Name=%-7s, GPA=%.2f, SessionToken=%s]",
                id, name, gpa, (tempSessionToken == null ? "null (LOST via transient)" : tempSessionToken));
    }
}

public class SerializationDemo {
    private static final String DATA_FILE = "students.ser";

    public static void main(String[] args) {
        Student[] originalStudents = {
            new Student(101, "Alice", 3.85, "TOKEN_XYZ_1"),
            new Student(102, "Bob", 3.40, "TOKEN_XYZ_2"),
            new Student(103, "Charlie", 3.92, "TOKEN_XYZ_3")
        };

        // --- STEP 1: Write (Serialize) to File ---
        System.out.println("=== 1. Writing Original Objects ===");
        for (Student s : originalStudents) {
            System.out.println(s);
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            // Arrays of serializable objects are directly serializable
            oos.writeObject(originalStudents);
            System.out.println("\nSuccessfully written array to: " + DATA_FILE);
        } catch (IOException e) {
            System.err.println("Serialization Error: " + e.getMessage());
            return;
        }

        // --- STEP 2: Read (Deserialize) from File ---
        System.out.println("\n=== 2. Reading Back into Fresh Objects ===");
        Student[] restoredStudents = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            restoredStudents = (Student[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Deserialization Error: " + e.getMessage());
            return;
        }

        if (restoredStudents != null) {
            for (Student s : restoredStudents) {
                System.out.println(s);
            }
        }

        // Cleanup created file
        new File(DATA_FILE).delete();
    }
}