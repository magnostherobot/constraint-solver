import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * A reader tailored for binary extensional CSPs.
 * It is created from a FileReader and a StreamTokenizer
 */
class BinaryCSPReader {
    private CSPStreamTokenizer in;

    public static void main(@NotNull String[] args)
            throws IOException, ParseException {
        if (args.length != 1) {
            System.out.println("Usage: java BinaryCSPReader <file.csp>");
            return;
        }
        BinaryCSPReader reader = new BinaryCSPReader();
        System.out.println(reader.readBinaryCSP(args[0]));
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
    private BinaryCSP readBinaryCSP(String fn)
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
            Constraint constraint = new Constraint(lhs, rhs);

            in.nextTokenAssertChar(')');

            for (in.nextToken(); in.ttype != CSPStreamTokenizer.TT_EOF
                    && in.ttype != 'c'; in.nextToken()) {

                int tupleLhs = in.tokenInt();
                in.nextTokenAssertChar(',');
                int tupleRhs = in.nextTokenInt();

                constraint.addPair(tupleLhs, tupleRhs);
            }

            constraints.add(constraint);
        }

        return constraints;
    }
}
