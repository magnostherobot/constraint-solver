import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BinaryConstraint {
    private int firstVar, secondVar;
    private ArrayList<BinaryTuple> tuples;

    public BinaryConstraint(int fv, int sv, ArrayList<BinaryTuple> t) {
        firstVar = fv;
        secondVar = sv;
        tuples = t;
    }

    @NotNull
    public String toString() {
        StringBuilder result = new StringBuilder()
                .append("c(")
                .append(firstVar)
                .append(", ")
                .append(secondVar)
                .append(")\n");

        for (BinaryTuple bt : tuples) {
            result.append(bt).append("\n");
        }

        return result.toString();
    }

    // TODO: You will want to add methods here to reason about the constraint
}
