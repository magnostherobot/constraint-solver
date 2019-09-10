package cs.nqueens;

import java.util.*;

public class Constraint {
    public final int constrained, constrainer;
    private final Map<Integer, Domain> constraint;

    public Constraint(Pair indices) {
        this.constrained = indices.lhs;
        this.constrainer = indices.rhs;

        this.constraint = new HashMap<>();
    }

    public Constraint(int constrained, int constrainer) {
        this(new Pair(constrained, constrainer));
    }

    public Constraint(Constraint that) {
        this.constrained = that.constrained;
        this.constrainer = that.constrainer;
        this.constraint = new HashMap<>(that.constraint);
    }

    static List<Constraint> copyConstraintList(List<Constraint> that) {

        List<Constraint> result = new ArrayList<>();
        for (Constraint c : that) {
            result.add(new Constraint(c));
        }
        return result;
    }

    static List<Constraint> copyConstraintList(List<Constraint> that,
                                               BinaryCSP csp) {
        List<Constraint> result = new ArrayList<>();
        for (Constraint c : that) {
            if (csp.getVariable(c.constrained) != null) {
                result.add(new Constraint(c));
            }
        }
        return result;
    }

    public void addPair(Pair t) {
        if (!this.constraint.containsKey(t.lhs)) {
            this.constraint.put(t.lhs, new Domain());
        }

        this.constraint.get(t.lhs).add(t.rhs);
    }

    public void addPair(int constrainerValue, int constrainedValue) {
        this.addPair(new Pair(constrainerValue, constrainedValue));
    }

    public Domain findConsistentValues(int lhsValue) {
        return this.constraint.getOrDefault(lhsValue, new Domain());
    }

    public List<Pair> getAsPairs() {
        List<Pair> result = new LinkedList<>();
        for (Map.Entry<Integer, Domain> e : this.constraint.entrySet()) {
            Integer k = e.getKey();
            for (Integer v : e.getValue()) {
                result.add(new Pair(k, v));
            }
        }
        return result;
    }

    public Domain constrainerIs(int index) {
        return this.constraint.getOrDefault(index, new Domain());
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
