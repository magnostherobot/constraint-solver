package cs.nqueens;

import cs.nqueens.exceptions.EmptyDomainException;
import cs.nqueens.exceptions.ManyValuesException;
import cs.nqueens.heuristics.DumbHeuristic;
import cs.nqueens.loggers.SilentLogger;
import cs.nqueens.solvers.ForwardChecker;
import org.junit.jupiter.api.BeforeAll;

public class ForwardCheckerTest extends ConstraintSolverTest {
    static protected Metasol solver;

    @BeforeAll
    static private void createSolver() {
        solver = new Metasol(new ForwardChecker(), new DumbHeuristic(),
            new SilentLogger());
    }

    @Override
    protected CSPResult solve(BinaryCSP csp) throws ManyValuesException, EmptyDomainException {
        return solver.solve(csp);
    }
}
