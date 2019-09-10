package cs.nqueens;

import cs.nqueens.exceptions.EmptyDomainException;
import cs.nqueens.exceptions.ManyValuesException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Domain extends HashSet<Integer> {

    public static Domain intersection(Domain a, Domain b) {
        Domain result = new Domain(a);
        result.intersect(b);
        return result;
    }

    public Domain() {
        super();
    }

    public Domain(int e) {
        this();
        this.add(e);
    }

    public Domain(Domain that) {
        super(that);
    }

    public Domain(Pair range) {
        super();

        for (int i = range.lhs; i <= range.rhs; ++i) {
            this.add(i);
        }
    }

    public Domain(int lowerBound, int upperBound) {
        this(new Pair(lowerBound, upperBound));
    }

    public Domain(List<Integer> values) {
        super(values);
    }

    public int getOnlyValue() throws ManyValuesException, EmptyDomainException {
        if (this.size() > 1) {
            throw new ManyValuesException();
        } else {
            for (int value : this) {
                return value;
            }
        }

        throw new EmptyDomainException();
    }

    public void intersect(Domain that) {
        this.retainAll(that);
    }

    public void union(Domain that) {
        this.addAll(that);
    }

    public int getAnyValue() throws EmptyDomainException {
        for (int result : this) {
            return result;
        }

        throw new EmptyDomainException();
    }

    public String toSimpleString() {
        return "" + Collections.min(this) + "," + Collections.max(this);
    }
}
