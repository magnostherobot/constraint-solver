package cs.nqueens.solvers;

import cs.nqueens.BinaryCSP;
import cs.nqueens.Constraint;
import cs.nqueens.Domain;
import cs.nqueens.Variable;

import java.util.List;

public class ForwardChecker implements CSPSolver {

    @Override
    public BinaryCSP beforeSolving(BinaryCSP csp) {
        return csp;
    }

    @Override
    public BinaryCSP leftBranch(BinaryCSP cspOrig, Variable variable,
                                int value) {

        BinaryCSP csp = new BinaryCSP(cspOrig);

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

    @Override
    public BinaryCSP rightBranch(BinaryCSP csp, Variable v, int value) {
        return csp;
    }
}
