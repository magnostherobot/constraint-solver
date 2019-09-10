package cs.nqueens;

import cs.nqueens.exceptions.EmptyDomainException;
import cs.nqueens.exceptions.ManyValuesException;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BinaryCSP {
    private final List<Variable> variables;
    private final List<Constraint> constraints;
    private final Map<Integer, Integer> assignments;

    public BinaryCSP(List<Variable> variables, List<Constraint> constraints,
                     Map<Integer, Integer> assignments) {
        this.variables = variables;

        this.constraints = new ArrayList<>(constraints.size());
        this.constraints.addAll(constraints);

        this.assignments = assignments;
    }

    public BinaryCSP(List<Variable> variables, List<Constraint> constraints) {
        this(variables, constraints, new HashMap<>());
    }

    public BinaryCSP(BinaryCSP that) {
        this(Variable.copyVariableList(that.variables),
                Constraint.copyConstraintList(that.constraints, that),
                new HashMap<>(that.assignments));
    }

    public boolean isSolved() {
        return this.variables.isEmpty();
    }

    public Variable getAnyVariable() {
        return this.variables.get(0);
    }

    public Variable getVariable(int index) {
        for (Variable v : this.variables) {
            if (index == v.getIndex()) {
                return v;
            }
        }

        return null;
    }

    public void removeVariable(int index) {
        Iterator<Variable> it = this.variables.listIterator();
        while (it.hasNext()) {
            Variable v = it.next();
            if (index == v.getIndex()) {
                it.remove();
                return;
            }
        }
    }

    public void assignToVariable(Variable v, int assignment) {
        this.removeVariable(v.getIndex());
        this.assignments.put(v.getIndex(), assignment);
    }

    public void removeFromVariableDomain(int index, int value)
            throws ManyValuesException, EmptyDomainException {
        Variable v = this.getVariable(index);
        Domain d = v.getDomain();
        d.remove(value);
    }

    public void addConstraint(Constraint constraint) {
        constraints.add(constraint);
        this.getVariable(constraint.constrained).addConstraint(constraint);
    }

    public static BinaryCSP forwardCheck(BinaryCSP csp_orig, Variable variable,
            int value) {
        BinaryCSP csp = new BinaryCSP(csp_orig);

        List<Constraint> rc = csp.getRelatedConstraints(variable);
        for (Constraint constraint : rc) {
            Variable v = csp.getVariable(constraint.constrained);
            if (v != null) {
                Domain d = v.getDomain();
                d.intersect(constraint.constrainerIs(value));

                if (d.isEmpty()) {
                    return null;
                }
            }
        }

        return csp;
    }

    Domain getDomainOf(int index) {
        Variable v = this.getVariable(index);
        if (v == null) {
            return new Domain(this.assignments.get(index));
        } else {
            return v.getDomain();
        }
    }

    List<Constraint> getArcsConstrainedBy(int index) {
        LinkedList<Constraint> result = new LinkedList<>();

        for (Constraint constraint : this.constraints) {
            if (constraint.constrainer == index) {
                result.addLast(constraint);
            }
        }

        return result;
    }

    List<Constraint> getArcsConstrainedBy(Variable v) {
        return this.getArcsConstrainedBy(v.getIndex());
    }

    static BinaryCSP checkArcs(BinaryCSP cspOrig, ArcQueue q) {
        BinaryCSP csp = new BinaryCSP(cspOrig);

        while (!q.isEmpty()) {
            Constraint arc = q.remove();

            Domain constrainerDomain = csp.getDomainOf(arc.constrainer);
            Variable constrained = csp.getVariable(arc.constrained);

            if (constrained != null) {
                if (constrained.constrainDomain(arc, constrainerDomain)) {
                    q.enqueueAll(csp.getArcsConstrainedBy(arc.constrained));
                }

                if (constrained.getDomain().isEmpty()) {
                    return null;
                }
            }
        }

        return csp;
    }

    public static BinaryCSP checkAllArcs(BinaryCSP csp) {
        ArcQueue q = new ArcQueue(csp.constraints);
        return checkArcs(csp, q);
    }

    public static BinaryCSP checkArcs(BinaryCSP csp, Variable v) {
        ArcQueue q = new ArcQueue(csp.getArcsConstrainedBy(v));
        return checkArcs(csp, q);
    }

    public List<Constraint> getRelatedConstraints(Variable v) {
        List<Constraint> result = new ArrayList<>();
        for (Constraint constraint : this.constraints) {
            if (constraint.constrainer == v.getIndex()) {
                result.add(constraint);
            }
        }

        return result;
    }

    public void populateGraph(Graph graph) {
        for (Variable v : this.variables) {
            for (int i : v.getDomain()) {
                Node n = graph.addNode("" + v.getIndex() + i);
                n.addAttribute("ui.label",
                        "$" + v.getIndex() + " = " + i);
            }
        }

        for (Constraint c : this.constraints) {
            for (Pair p : c.getAsPairs()) {
                try {
                    graph.addEdge(
                            "" + c.constrained + p.lhs + c.constrainer + p.rhs,
                            "" + c.constrained + p.lhs,
                            "" + c.constrainer + p.rhs
                    );
                } catch (Exception e) {
                }
            }
        }
    }

    static long uniqueGraphId = 0;
    public Graph makeGraph() {
        Graph g = new SingleGraph("" + uniqueGraphId);
        uniqueGraphId++;
        this.populateGraph(g);
        return g;
    }

    @NotNull
    public String toString() {
        StringBuilder result = new StringBuilder()
                .append(this.variables.size()).append("\n");

        for (Variable variable : this.variables) {
            result.append(variable.getDomain().toString()).append("\n");
        }

        for (Constraint constraint : this.constraints) {
            result.append(constraint);
        }

        return result.toString();
    }

    public Map<Integer, Integer> getAssignments() {
        return this.assignments;
    }
}
