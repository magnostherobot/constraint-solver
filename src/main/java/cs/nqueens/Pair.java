package cs.nqueens;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Assumes tuple values are integers
 */
class Pair {
    final int lhs, rhs;

    public Pair(int v1, int v2) {
        lhs = v1;
        rhs = v2;
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
