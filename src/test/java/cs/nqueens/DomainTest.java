package cs.nqueens;

import java.util.List;
import java.util.ArrayList;

import cs.nqueens.exceptions.EmptyDomainException;
import cs.nqueens.exceptions.ManyValuesException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DomainTest {

    @Test
    public void emptyConstructorEmptyDomain() {
        Domain d = new Domain();
        assertTrue(d.isEmpty());
    }

    @Test
    public void constructorGeneratesAllInRange() {
        int lower = 20;
        int upper = 30;

        Domain result = new Domain(lower, upper);

        // +1 because the upper bound is included
        assertEquals(result.size(), upper - lower + 1);

        for (int i = lower; i <= upper; ++i) {
            assertTrue(result.contains(i));
        }
    }

    @Test
    public void constructFromList() {
        List<Integer> in = new ArrayList<>();
        in.add(1);
        in.add(2);
        in.add(10);

        Domain out = new Domain(in);

        assertEquals(out.size(), in.size());
        for (int i : in) {
            assertTrue(out.contains(i));
        }
    }

    @Test
    public void intersectionIncludesUpperBound() {
        Domain a = new Domain(1, 3);
        Domain b = new Domain(3, 5);

        Domain c = Domain.intersection(a, b);
        assertTrue(c.contains(3));
    }

    @Test
    public void intersectionLeftContained() {
        Domain left  = new Domain(4, 6);
        Domain right = new Domain(1, 9);

        Domain result = Domain.intersection(left, right);

        for (int i : left) {
            assertTrue(result.contains(i));
        }
    }

    @Test
    public void intersectionRightContained() {
        Domain left  = new Domain(1, 9);
        Domain right = new Domain(4, 6);

        Domain result = Domain.intersection(left, right);

        for (int i : right) {
            assertTrue(result.contains(i));
        }
    }

    @Test
    public void getOnlyValueThrowsIfEmpty() {
        Domain d = new Domain();
        assertThrows(EmptyDomainException.class, () -> { d.getOnlyValue(); });
    }

    @Test
    public void getOnlyValueThrowsIfMoreThanOneValue() {
        Domain d = new Domain(1, 10);
        assertThrows(ManyValuesException.class, () -> { d.getOnlyValue(); });
    }

    @Test
    public void getAnyValueThrowsIfEmpty() {
        Domain d = new Domain();
        assertThrows(EmptyDomainException.class, () -> { d.getAnyValue(); });
    }
}
