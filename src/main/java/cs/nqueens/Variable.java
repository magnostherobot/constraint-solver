package cs.nqueens;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Variable {
    private final int index;
    private Domain domain;
    private final List<Constraint> constraints;

    static List<Variable> copyVariableList(List<Variable> that) {
        List<Variable> result = new ArrayList<>();
        for (Variable v : that) {
            result.add(new Variable(v));
        }
        return result;
    }

    public Variable(int index, Pair domainBounds) {
        this.index = index;
        this.domain = new Domain(domainBounds);

        this.constraints = new ArrayList<>();
    }

    public Variable(Variable that) {
        this.index = that.index;
        this.domain = new Domain(that.domain);
        this.constraints = Constraint.copyConstraintList(that.constraints);
    }

    public Variable(int index, int lowerBound, int upperBound) {
        this(index, new Pair(lowerBound, upperBound));
    }

    public String toString() {
        return "$" + this.getIndex();
    }

    public void addConstraint(Constraint con) {
        assert(con.constrained == this.index);

        this.constraints.add(con);
    }

    public int getIndex() {
        return this.index;
    }

    public Domain getDomain() {
        return this.domain;
    }

    public boolean constrainDomain(Constraint arc, Domain d) {
        Domain possibleValues = new Domain();

        for (int i : d) {
            possibleValues.union(arc.findConsistentValues(i));
        }

        int oldDomainSize = this.domain.size();
        this.domain.intersect(possibleValues);
        return oldDomainSize != this.domain.size();
    }

    public Domain findConsistentValues(Map<Integer, Integer> assignments) {
        Domain result = new Domain(this.domain);

        for (Map.Entry<Integer, Integer> pair : assignments.entrySet()) {
            for (Constraint c : this.constraints) {
                if (c.constrainer == pair.getKey()) {
                    result.intersect(c.findConsistentValues(pair.getValue()));
                }
            }
        }

        return result;
    }

    public void refineDomain(Map<Integer, Integer> assignments) {
        this.domain = findConsistentValues(assignments);
    }
}
