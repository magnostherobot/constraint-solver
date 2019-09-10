package cs.nqueens;

import cs.nqueens.exceptions.EmptyDomainException;
import cs.nqueens.exceptions.ManyValuesException;
import cs.nqueens.heuristics.DumbHeuristic;
import cs.nqueens.loggers.SilentLogger;
import cs.nqueens.solvers.MaintainArcConsistencySolver;
import org.junit.jupiter.api.BeforeAll;

public class MaintainArcConsistencyTest extends ConstraintSolverTest {
    static Metasol solver;

    @BeforeAll
    static public void createSolver() {
        solver = new Metasol(new MaintainArcConsistencySolver(),
            new DumbHeuristic(), new SilentLogger());
    }

    @Override
    protected CSPResult solve(BinaryCSP csp) throws ManyValuesException, EmptyDomainException {
        return solver.solve(csp);
    }
}
