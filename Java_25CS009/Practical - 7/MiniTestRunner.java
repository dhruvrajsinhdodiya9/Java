import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@interface Run {
}

class MyTests {
    @Run
    public void test1() {
        System.out.println("Running Test 1: Passed");
    }

    public void test2() {
        System.out.println("Running Test 2");
    }

    @Run
    public void test3() {
        System.out.println("Running Test 3: Passed");
    }
}

public class MiniTestRunner {
    public static void main(String[] args) throws Exception {
        MyTests obj = new MyTests();
        Class c = obj.getClass();
        Method[] methods = c.getDeclaredMethods();

        int count = 0;

        for (int i = 0; i < methods.length; i++) {
            Method m = methods[i];

            if (m.isAnnotationPresent(Run.class)) {
                m.invoke(obj);
                count++;
            }
        }

        System.out.println("\nTotal tests executed: " + count);
    }
}