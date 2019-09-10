package cs.nqueens.solvers;

import cs.nqueens.BinaryCSP;
import cs.nqueens.Variable;

public class MaintainArcConsistencySolver implements CSPSolver {

    @Override
    public BinaryCSP beforeSolving(BinaryCSP csp) {
        return BinaryCSP.checkAllArcs(csp);
    }

    @Override
    public BinaryCSP leftBranch(BinaryCSP csp, Variable v, int value) {
        return BinaryCSP.checkArcs(csp, v);
    }

    @Override
    public BinaryCSP rightBranch(BinaryCSP csp, Variable v, int value) {
        return BinaryCSP.checkArcs(csp, v);
    }
}
