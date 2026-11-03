import java.util.Scanner;

public class NotificationDemo {

    // Wrapper class to mark a Notifier as Urgent
    static class UrgentNotifier implements Notifier, Urgent {

        private Notifier notifier;

        UrgentNotifier(Notifier notifier) {
            this.notifier = notifier;
        }

        @Override
        public void send(String message) {
            notifier.send(message);
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Email sender as lambda
        Notifier email = message ->
                System.out.println("Email: " + message);

        // SMS sender as lambda
        Notifier sms = message ->
                System.out.println("SMS: " + message);

        // Ask user which sender should be urgent
        System.out.print("Which sender should be urgent? (email/sms): ");
        String choice = sc.nextLine();

        Notifier emailSender;
        Notifier smsSender;

        if (choice.equalsIgnoreCase("email")) {
            emailSender = new UrgentNotifier(email);
            smsSender = sms;
        } else {
            emailSender = email;
            smsSender = new UrgentNotifier(sms);
        }

        // Store senders in array
        Notifier[] senders = {
                emailSender,
                smsSender
        };

        // Runtime message
        System.out.print("Enter notification message: ");
        String message = sc.nextLine();

        // Broadcast
        System.out.println("\n--- Broadcasting ---");

        for (Notifier sender : senders) {

            sender.send(message);

            // Urgent sender sends twice
            if (sender instanceof Urgent) {
                sender.send(message);
            }
        }

        sc.close();
    }
}