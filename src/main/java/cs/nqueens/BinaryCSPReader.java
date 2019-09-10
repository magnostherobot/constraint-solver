package cs.nqueens;

import cs.nqueens.exceptions.EmptyDomainException;
import cs.nqueens.exceptions.ManyValuesException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * A reader tailored for binary extensional CSPs.
 * It is created from a FileReader and a StreamTokenizer.
 */
class BinaryCSPReader {
    private CSPStreamTokenizer in;

    static void debug(String msg, int depth) {
        System.out.println("" + depth + ": " + msg);
    }

    public static void main(@NotNull String[] args)
            throws IOException, ParseException, ManyValuesException,
        EmptyDomainException {
        if (args.length != 1) {
            System.out.println("Usage: java BinaryCSPReader <file.csp>");
            return;
        }
        BinaryCSPReader reader = new BinaryCSPReader();
        BinaryCSP csp = reader.readBinaryCSP(args[0]);

        CSPResult solution = solve(csp);
    }

    @Contract(pure = true)
    static BinaryCSP solveMARC(BinaryCSP csp, int depth)
            throws ManyValuesException, EmptyDomainException {

        if (csp.isSolved()) {
            debug("Solution found", depth);
            return csp;
        }

        BinaryCSP leftCopy = new BinaryCSP(csp);
        Variable v = leftCopy.getAnyVariable();
        int i = v.getDomain().getAnyValue();
        leftCopy.assignToVariable(v, i);

        debug("Trying " + v + "=" + i, depth);

        BinaryCSP leftARCed = BinaryCSP.checkArcs(leftCopy, v);
        if (leftARCed != null) {
            debug("Left MARC succeeded", depth);
            BinaryCSP leftResult = solveMARC(leftARCed, depth);

            if (leftResult != null) {
                return leftResult;
            }
        } else {
            debug("Left MARC failed", depth);
        }

        BinaryCSP rightCopy = new BinaryCSP(csp);
        v = rightCopy.getVariable(v.getIndex());
        rightCopy.removeFromVariableDomain(v.getIndex(), i);

        if (v.getDomain().isEmpty()) {
            return null;
        }

        BinaryCSP rightARCed = BinaryCSP.checkArcs(rightCopy, v);
        if (rightARCed != null) {
            debug("Right MARC succeeded", depth);

            return solveMARC(rightARCed, depth);
        } else {
            debug("Right MARC failed", depth);
            return null;
        }
    }

    static CSPResult solveMARC(BinaryCSP csp) throws ManyValuesException, EmptyDomainException {
        int depth = 0;

        if (csp.isSolved()) {
            debug("Solution found", depth);
            return new CSPResult(csp);
        }

        BinaryCSP refinedCsp = BinaryCSP.checkAllArcs(csp);
        if (refinedCsp == null) {
            return null;
        }

        return new CSPResult(solveMARC(refinedCsp, depth + 1));
    }

    static BinaryCSP solve(BinaryCSP csp, int depth)
            throws ManyValuesException, EmptyDomainException {

        if (csp.isSolved()) {
            debug("Solution found", depth);
            return csp;
        }

        BinaryCSP leftCopy = new BinaryCSP(csp);

        // for now, get any old variable
        Variable v = leftCopy.getAnyVariable();
        int i = v.getDomain().getAnyValue();
        leftCopy.assignToVariable(v, i);

        debug("Trying " + v + "=" + i, depth);

        BinaryCSP leftForwardChecked =
                BinaryCSP.forwardCheck(leftCopy, v, i);
        if (leftForwardChecked != null) {
            debug("Forward checking succeeded", depth);
            BinaryCSP leftResult = solve(leftForwardChecked, depth + 1);

            if (leftResult != null) {
                return leftResult;
            }
        } else {
            debug("Forward checking failed", depth);
        }

        BinaryCSP rightCopy = new BinaryCSP(csp);
        v = rightCopy.getVariable(v.getIndex());
        rightCopy.removeFromVariableDomain(v.getIndex(), i);

        if (v.getDomain().isEmpty()) {
            return null;
        }

        return solve(rightCopy, depth + 1);
    }

    static CSPResult solve(BinaryCSP csp) throws ManyValuesException,
            EmptyDomainException {

        return new CSPResult(solve(csp, 0));
    }

    /*
     * File format:
     * <no. vars>
     * NB vars indexed from 0
     * We assume that the domain of all vars is specified in terms of bounds
     * <lb>, <ub> (one per var)
     * Then the list of constraints
     * c(<varno>, <varno>)
     * binary tuples
     * <domain val>, <domain val>
     */
    public BinaryCSP readBinaryCSP(String fn)
            throws IOException, ParseException {

        FileReader inFR = new FileReader(fn);
        in = new CSPStreamTokenizer(inFR);

        List<Variable> variables = readVariables();
        List<Constraint> constraints = readBinaryConstraints();

        BinaryCSP csp = new BinaryCSP(variables, constraints);

        inFR.close();
        return csp;
    }

    private List<Variable> readVariables() throws IOException, ParseException {

        int n = in.nextTokenInt();
        List<Variable> variables = new ArrayList<>(n);

        for (int i = 0; i < n; ++i) {
            int lowerBound = in.nextTokenInt();
            in.nextTokenAssertChar(',');
            int upperBound = in.nextTokenInt();

            variables.add(new Variable(i, lowerBound, upperBound));
        }

        return variables;
    }

    /**
     * Reads a list of binary constraints.
     */
    private List<Constraint> readBinaryConstraints()
            throws IOException, ParseException {

        List<Constraint> constraints = new ArrayList<>();

        // 'c' or EOF
        in.nextToken();
        while (in.ttype != CSPStreamTokenizer.TT_EOF) {
            in.tokenAssertChar('c');

            in.nextTokenAssertChar('(');
            int lhs = in.nextTokenInt();

            in.nextTokenAssertChar(',');
            int rhs = in.nextTokenInt();

            Constraint ltrConstraint = new Constraint(lhs, rhs);
            Constraint rtlConstraint = new Constraint(rhs, lhs);

            in.nextTokenAssertChar(')');

            for (in.nextToken(); in.ttype != CSPStreamTokenizer.TT_EOF
                    && in.ttype != 'c'; in.nextToken()) {

                int tupleLhs = in.tokenInt();
                in.nextTokenAssertChar(',');
                int tupleRhs = in.nextTokenInt();

                ltrConstraint.addPair(tupleLhs, tupleRhs);
                rtlConstraint.addPair(tupleRhs, tupleLhs);
            }

            constraints.add(ltrConstraint);
            constraints.add(rtlConstraint);
        }

        return constraints;
    }
}
