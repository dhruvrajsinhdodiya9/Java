import java.util.Scanner;

enum Coin {
    ONE, TWO, FIVE, TEN
}

public class VendingMachine {
    public static void main(String[] args) {
        int snackPrice = 15;
        int total = 0;
        Scanner sc = new Scanner(System.in);

        while (total < snackPrice) {
            System.out.print("Insert coin (ONE, TWO, FIVE, TEN): ");
            String input = sc.next().toUpperCase();

            Coin coin = Coin.valueOf(input);

            int value = switch (coin) {
                case ONE -> 1;
                case TWO -> 2;
                case FIVE -> 5;
                case TEN -> 10;
            };

            total += value;
            System.out.println("Total so far: " + total);
        }

        int change = total - snackPrice;
        System.out.println("Paid. Change: " + change);

        sc.close();
    }
}