import java.util.Collections;
import java.util.HashSet;

public class Domain extends HashSet<Integer> {
    public Domain() {
        super();
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

    public void intersect(Domain that) {
        this.retainAll(that);
    }

    public String toSimpleString() {
        return "" + Collections.min(this) + "," + Collections.max(this);
    }
}
