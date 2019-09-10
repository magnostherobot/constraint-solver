package cs.nqueens.solvers;

import cs.nqueens.BinaryCSP;
import cs.nqueens.Variable;
import org.jetbrains.annotations.Contract;

public interface CSPSolver {

    @Contract(pure = true)
    BinaryCSP beforeSolving(BinaryCSP csp);

    @Contract(pure = true)
    BinaryCSP leftBranch(BinaryCSP csp, Variable v, int value);

    @Contract(pure = true)
    BinaryCSP rightBranch(BinaryCSP csp, Variable v, int value);
}
