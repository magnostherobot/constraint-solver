package cs.nqueens.heuristics;

import cs.nqueens.BinaryCSP;
import cs.nqueens.Variable;
import cs.nqueens.exceptions.EmptyDomainException;

public class DumbHeuristic implements CSPHeuristic {

    @Override
    public Variable chooseVariable(BinaryCSP csp) {
        return csp.getAnyVariable();
    }

    @Override
    public int chooseValue(BinaryCSP csp, Variable v)
            throws EmptyDomainException {

        return v.getDomain().getAnyValue();
    }
}
