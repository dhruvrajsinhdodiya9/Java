import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter radius of Circle: ");
        double radius = sc.nextDouble();

        System.out.print("Enter length of Rectangle: ");
        double length = sc.nextDouble();

        System.out.print("Enter width of Rectangle: ");
        double width = sc.nextDouble();

        System.out.print("Enter base of Triangle: ");
        double base = sc.nextDouble();

        System.out.print("Enter height of Triangle: ");
        double height = sc.nextDouble();

        Shape[] shapes = {
                new Circle(radius),
                new Rectangle(length, width),
                new Triangle(base, height)
        };

        double total = 0;
        double largest = 0;

        for (Shape shape : shapes) {
            double area = shape.area();

            System.out.println(shape.getClass().getSimpleName() + " Area: " + area);


            total += area;

            System.out.println("Running Total: " + total);

            if (area > largest) {
                largest = area;
            }

        }

        System.out.println("Largest Area: " + largest);

        sc.close();
    }
}