import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.Scanner;

@Retention(RetentionPolicy.RUNTIME)
@interface NotBlank {
}

@Retention(RetentionPolicy.RUNTIME)
@interface MaxLength {
    int value();
}

class SignupForm {
    @NotBlank
    @MaxLength(10)
    String username;

    @NotBlank
    @MaxLength(15)
    String password;

    public SignupForm(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

public class FormValidatorDemo {
    public static void checkForm(SignupForm form) throws Exception {
        Class c = form.getClass();
        Field[] fields = c.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            String val = (String) f.get(form);

            if (f.isAnnotationPresent(NotBlank.class)) {
                if (val == null || val.trim().length() == 0) {
                    System.out.println("Error: " + f.getName() + " cannot be blank!");
                }
            }

            if (f.isAnnotationPresent(MaxLength.class)) {
                MaxLength m = f.getAnnotation(MaxLength.class);
                if (val != null && val.length() > m.value()) {
                    System.out.println("Error: " + f.getName() + " is too long! Max limit is " + m.value());
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter username: ");
        String u = sc.nextLine();

        System.out.print("Enter password: ");
        String p = sc.nextLine();

        SignupForm myForm = new SignupForm(u, p);

        System.out.println("\nChecking form...");
        checkForm(myForm);

        sc.close();
    }
}