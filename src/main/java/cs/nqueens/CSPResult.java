package cs.nqueens;

import java.util.Map;

public class CSPResult {
    boolean solved;
    Map<Integer, Integer> assignments = null;

    public CSPResult() {
        this.solved = false;
    }

    public CSPResult(BinaryCSP csp) {
        if (csp == null) {
            this.solved = false;
        } else {
            assert(csp.isSolved());

            this.solved = true;
            this.assignments = csp.getAssignments();
        }
    }

    public String toString() {
        if (!this.solved) {
            return "No solution found";
        }

        StringBuilder sb = new StringBuilder();

        for (Map.Entry<Integer, Integer> e : this.assignments.entrySet()) {
            sb.append("$")
                .append(e.getKey())
                .append(" = ")
                .append(e.getValue())
                .append("\n");
        }

        return sb.toString();
    }
}
