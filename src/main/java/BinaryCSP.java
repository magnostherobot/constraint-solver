import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BinaryCSP {
    private BinaryTuple[] domainBounds;
    private ArrayList<BinaryConstraint> constraints;

    public BinaryCSP(BinaryTuple[] db, ArrayList<BinaryConstraint> c) {
        domainBounds = db;
        constraints = c;
    }

    @NotNull
    public String toString() {
        StringBuilder result = new StringBuilder()
                .append("CSP:\n");

        for (int i = 0; i < domainBounds.length; i++) {
            result.append("Var ").append(i).append(": ")
                    .append(domainBounds[i].getLhs()).append("..")
                    .append(domainBounds[i].getRhs()).append("\n");
        }

        for (BinaryConstraint bc : constraints) {
            result.append(bc).append("\n");
        }

        return result.toString();
    }

    @Contract(pure = true)
    public int getNoVariables() {
        return domainBounds.length;
    }

    @Contract(pure = true)
    public int getLowerBound(int varIndex) {
        return domainBounds[varIndex].getLhs();
    }

    @Contract(pure = true)
    public int getUpperBound(int varIndex) {
        return domainBounds[varIndex].getRhs();
    }

    @Contract(pure = true)
    public ArrayList<BinaryConstraint> getConstraints() {
        return constraints;
    }
}
