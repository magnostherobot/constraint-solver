package cs.nqueens;

import cs.nqueens.exceptions.EmptyDomainException;
import cs.nqueens.exceptions.ManyValuesException;
import cs.nqueens.heuristics.CSPHeuristic;
import cs.nqueens.heuristics.DumbHeuristic;
import cs.nqueens.loggers.CSPLogger;
import cs.nqueens.loggers.PrintStreamLogger;
import cs.nqueens.solvers.CSPSolver;
import cs.nqueens.solvers.MaintainArcConsistencySolver;
import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.text.ParseException;

public class Metasol {
    final CSPSolver solver;
    final CSPHeuristic heuristic;
    final CSPLogger logger;

    public static void main(String[] args) throws IOException, ParseException, ManyValuesException, EmptyDomainException {
        Metasol ms = new Metasol(new MaintainArcConsistencySolver(),
            new DumbHeuristic(), new PrintStreamLogger());
        BinaryCSPReader reader = new BinaryCSPReader();
        BinaryCSP csp = reader.readBinaryCSP("src/main/resources/nqueens" +
            "/6Queens.csp");
        System.out.println(ms.solve(csp));
    }

    public Metasol(CSPSolver solver, CSPHeuristic heuristic, CSPLogger logger) {
        this.solver = solver;
        this.heuristic = heuristic;
        this.logger = logger;
    }

    @Contract(pure = true)
    private BinaryCSP solve(BinaryCSP csp, int depth)
            throws ManyValuesException, EmptyDomainException {

        if (csp.isSolved()) {
            logger.log("Solution found", depth);
            return csp;
        }

        BinaryCSP leftCopy = new BinaryCSP(csp);
        Variable v = heuristic.chooseVariable(csp);
        int i = heuristic.chooseValue(csp, v);
        leftCopy.assignToVariable(v, i);

        logger.log("Trying " + v + "=" + i, depth);

        BinaryCSP leftARCed = solver.leftBranch(leftCopy, v, i);
        if (leftARCed != null) {
            logger.log("Left branch succeeded", depth);
            BinaryCSP leftResult = solve(leftARCed, depth + 1);

            if (leftResult != null) {
                return leftResult;
            }
        } else {
            logger.log("Left branch failed", depth);
        }

        BinaryCSP rightCopy = new BinaryCSP(csp);
        v = rightCopy.getVariable(v.getIndex());
        rightCopy.removeFromVariableDomain(v.getIndex(), i);

        if (v.getDomain().isEmpty()) {
            return null;
        }

        BinaryCSP rightARCed = this.solver.rightBranch(rightCopy, v, i);
        if (rightARCed != null) {
            logger.log("Right branch succeeded", depth);

            return solve(rightARCed, depth + 1);
        } else {
            logger.log("Right branch failed", depth);
            return null;
        }
    }

    @Contract(pure = true)
    public CSPResult solve(BinaryCSP csp) throws ManyValuesException,
            EmptyDomainException {

        int depth = 0;

        if (csp.isSolved()) {
            logger.log("Solution found", depth);
            return new CSPResult(csp);
        }

        BinaryCSP refinedCsp = solver.beforeSolving(csp);
        if (refinedCsp == null) {
            return new CSPResult();
        }

        return new CSPResult(solve(refinedCsp, depth));
    }
}
