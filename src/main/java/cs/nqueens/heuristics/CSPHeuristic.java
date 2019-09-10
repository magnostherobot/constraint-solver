package cs.nqueens.heuristics;

import cs.nqueens.BinaryCSP;
import cs.nqueens.Variable;
import cs.nqueens.exceptions.EmptyDomainException;
import org.jetbrains.annotations.Contract;

public interface CSPHeuristic {

    @Contract(pure = true)
    Variable chooseVariable(BinaryCSP csp);

    @Contract(pure = true)
    int chooseValue(BinaryCSP csp, Variable v) throws EmptyDomainException;
}
