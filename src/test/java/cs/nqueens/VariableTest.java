package cs.nqueens;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class VariableTest {

    @Test
    public void findConsistentValues() {
        Variable v = new Variable(0, 0, 2);

        Constraint ca = new Constraint(0, 1);
        ca.addPair(0, 0);
        ca.addPair(0, 1);
        v.addConstraint(ca);

        Constraint cb = new Constraint(0, 2);
        cb.addPair(1, 1);
        cb.addPair(1, 2);
        v.addConstraint(cb);

        Map<Integer, Integer> assignments = new HashMap<>();
        assignments.put(1, 0);
        assignments.put(2, 1);

        Domain result = v.findConsistentValues(assignments);
        assertFalse(result.contains(0));
        assertTrue (result.contains(1));
        assertFalse(result.contains(2));
    }
}
