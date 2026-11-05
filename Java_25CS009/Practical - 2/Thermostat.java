import java.util.Scanner;

public class Thermostat {
    private String location;
    private int temperature;
    private static final int MIN = 16;
    private static final int MAX = 30;
    private static int activeCount = 0;

    public Thermostat(String location, int startTemp) {
        this.location = location;
        if (startTemp >= MIN && startTemp <= MAX) {
            this.temperature = startTemp;
        } else {
            this.temperature = 22;
        }
        activeCount++;
    }

    public Thermostat(String location) {
        this(location, 22);
    }

    public void raise() {
        if (temperature < MAX) {
            temperature++;
            System.out.println("Temperature: " + temperature);
        } else {
            System.out.println("Already at maximum (" + MAX + ")");
        }
    }

    public void lower() {
        if (temperature > MIN) {
            temperature--;
            System.out.println("Temperature: " + temperature);
        } else {
            System.out.println("Already at minimum (" + MIN + ")");
        }
    }

    public int getTemperature() {
        return temperature;
    }

    public static int getActiveCount() {
        return activeCount;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter location for Thermostat 1: ");
        String loc1 = sc.nextLine();

        System.out.print("Enter initial temperature for " + loc1 + ": ");
        int temp1 = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter location for Thermostat 2 (default 22°C): ");
        String loc2 = sc.nextLine();

        Thermostat t1 = new Thermostat(loc1, temp1);
        Thermostat t2 = new Thermostat(loc2);

        System.out.println("\nInitial Temperature of " + loc1 + ": " + t1.getTemperature());

        System.out.print("Enter number of times to raise: ");
        int raiseCount = sc.nextInt();
        for (int i = 0; i < raiseCount; i++) {
            t1.raise();
        }

        System.out.print("Enter number of times to lower: ");
        int lowerCount = sc.nextInt();
        for (int i = 0; i < lowerCount; i++) {
            t1.lower();
        }

        System.out.println("Active thermostats count: " + Thermostat.getActiveCount());

        sc.close();
    }
}