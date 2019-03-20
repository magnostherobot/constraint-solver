import java.util.ArrayList;
import java.util.List;

class Variable {
    private final int index;
    private final Domain domain;
    private final List<Constraint> constraints;

    private Variable(int index, Pair domainBounds) {
        this.index = index;
        this.domain = new Domain(domainBounds);

        this.constraints = new ArrayList<>();
    }

    public Variable(int index, int lowerBound, int upperBound) {
        this(index, new Pair(lowerBound, upperBound));
    }

    public void addConstraint(Constraint con) {
        assert (con.constrainer == this.index);

        this.constraints.add(con);
    }

    public Domain getDomain() {
        return this.domain;
    }

    public Domain findConsistentValues(List<Pair> assignments) {
        Domain result = new Domain(this.domain);

        for (Pair t : assignments) {
            for (Constraint c : this.constraints) {
                if (c.constrained == t.lhs) {
                    result.intersect(c.findConsistentValues(t.rhs));
                }
            }
        }

        return result;
    }
}
