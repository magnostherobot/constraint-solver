package cs.nqueens;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Queue;

public class ArcQueue extends LinkedHashSet<Constraint> implements Queue<Constraint> {

    public ArcQueue(Collection<Constraint> that) {
        super(that);
    }

    @Override
    public boolean offer(Constraint constraint) {
        this.add(constraint);
        return true;
    }

    @Override
    public Constraint remove() {
        Constraint result = this.poll();

        if (result == null) {
            throw new IllegalStateException();
        } else {
            return result;
        }
    }

    @Override
    public Constraint poll() {
        Iterator<Constraint> it = this.iterator();

        while (it.hasNext()) {
            Constraint constraint = it.next();
            it.remove();
            return constraint;
        }

        return null;
    }

    @Override
    public Constraint element() {
        Constraint result = this.peek();

        if (result == null) {
            throw new IllegalStateException();
        } else {
            return result;
        }
    }

    @Override
    public Constraint peek() {
        for (Constraint constraint : this) {
            return constraint;
        }

        return null;
    }

    public void enqueue(Constraint constraint) {
        this.add(constraint);
    }

    public void enqueueAll(Collection<Constraint> constraints) {
        this.addAll(constraints);
    }
}
