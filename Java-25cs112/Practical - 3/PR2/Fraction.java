import java.util.Objects;

public class Fraction {
    private int num;
    private int den;

    public Fraction(int num, int den) {
        if (den == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }

        int g = gcd(Math.abs(num), Math.abs(den));

        this.num = num / g;
        this.den = den / g;

        if (this.den < 0) {
            this.num = -this.num;
            this.den = -this.den;
        }
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    @Override
    public String toString() {
        return num + "/" + den;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Fraction))
            return false;

        Fraction other = (Fraction) obj;

        return num == other.num && den == other.den;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, den);
    }
}