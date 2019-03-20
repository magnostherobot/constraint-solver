import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Assumes tuple values are integers
 */
public class BinaryTuple {
    private int lhs, rhs;

    public BinaryTuple(int v1, int v2) {
        lhs = v1;
        rhs = v2;
    }

    public int getLhs() {
        return this.lhs;
    }

    public int getRhs() {
        return this.rhs;
    }

    @NotNull
    @Contract(pure = true)
    public String toString() {
        return "<" + lhs + ", " + rhs + ">";
    }

    @Contract(pure = true)
    public boolean matches(int v1, int v2) {
        return (lhs == v1) && (rhs == v2);
    }
}
