import java.util.Scanner;

class OutOfStockException extends Exception {
    private int shortfall;

    public OutOfStockException(String item, int shortfall) {
        super("Shortfall of " + shortfall + " units for " + item);
        this.shortfall = shortfall;
    }

    public int getShortfall() {
        return shortfall;
    }
}

class InvalidQuantityException extends Exception {
    public InvalidQuantityException(String message) {
        super(message);
    }
}

class Warehouse {
    private String item;
    private int stock;

    public Warehouse(String item, int stock) {
        this.item = item;
        this.stock = stock;
    }

    public void issue(String requestedItem, int qty) throws OutOfStockException, InvalidQuantityException {
        if (!this.item.equalsIgnoreCase(requestedItem)) {
            System.out.println("Item \"" + requestedItem + "\" not found in this warehouse.");
            return;
        }

        if (qty <= 0) {
            throw new InvalidQuantityException("Invalid quantity: " + qty + ". Must be greater than 0.");
        }

        if (qty > stock) {
            int shortfall = qty - stock;
            throw new OutOfStockException(requestedItem, shortfall);
        }

        stock -= qty;
        System.out.println("Successfully issued " + qty + " of " + requestedItem + ". Remaining stock: " + stock);
    }
}

public class WarehouseDemo {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse("Laptop", 10);
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of requests to process: ");
        int totalRequests = sc.nextInt();

        for (int i = 1; i <= totalRequests; i++) {
            System.out.println("\n--- Request " + i + " ---");
            System.out.print("Enter item name: ");
            String item = sc.next();

            System.out.print("Enter quantity: ");
            int qty = sc.nextInt();

            try {
                warehouse.issue(item, qty);
            } catch (OutOfStockException e) {
                System.out.println("Order Failed: " + e.getMessage() + " (Shortfall: " + e.getShortfall() + ")");
            } catch (InvalidQuantityException e) {
                System.out.println("Order Failed: " + e.getMessage());
            }
        }

        System.out.println("\nAll requests processed.");
        sc.close();
    }
}