import java.util.Scanner;

public class RemoteControl {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Switchable fan = new Fan();
        Switchable light = new Light();

        Switchable[] devices = {fan, light};

        System.out.println("=== Toggling Devices ===");

        for (Switchable device : devices) {
            device.toggle();
        }

        SwitchRule anonymousRule = new SwitchRule() {
            @Override
            public boolean maySwitchOn(Switchable device, int hour) {
                return hour >= 6 && hour <= 22;
            }
        };

        SwitchRule lambdaRule =
                (device, hour) -> hour >= 6 && hour <= 22;

        System.out.print("\nEnter hour (0-23): ");
        int hour = sc.nextInt();

        System.out.println("\n=== Switch Rules ===");

        System.out.println(
                "Anonymous class: "
                        + anonymousRule.maySwitchOn(fan, hour)
        );

        System.out.println(
                "Lambda: "
                        + lambdaRule.maySwitchOn(light, hour)
        );

        sc.close();
    }
}