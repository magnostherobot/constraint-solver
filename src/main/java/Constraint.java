import java.util.HashMap;
import java.util.Map;

class Constraint {
    final int constrained, constrainer;
    private final Map<Integer, Domain> constraint;

    public Constraint(Pair indices) {
        this.constrained = indices.lhs;
        this.constrainer = indices.rhs;

        this.constraint = new HashMap<>();
    }

    public Constraint(int lindex, int rindex) {
        this(new Pair(lindex, rindex));
    }

    public void addPair(Pair t) {
        if (!this.constraint.containsKey(t.lhs)) {
            this.constraint.put(t.lhs, new Domain());
        }

        this.constraint.get(t.lhs).add(t.rhs);
    }

    public void addPair(int lhs, int rhs) {
        this.addPair(new Pair(lhs, rhs));
    }

    public Domain findConsistentValues(int lhsValue) {
        return this.constraint.get(lhsValue);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("c(")
                .append(this.constrained).append(",")
                .append(this.constrainer).append(")\n");

        for (Map.Entry<Integer, Domain> entry : this.constraint.entrySet()) {
            int lhs = entry.getKey();
            for (Integer rhs : entry.getValue()) {
                result.append(lhs).append(",").append(rhs).append("\n");
            }
        }

        return result.toString();
    }
}
