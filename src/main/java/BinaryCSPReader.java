import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * A reader tailored for binary extensional CSPs.
 * It is created from a FileReader and a StreamTokenizer
 */
public class BinaryCSPReader {
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

        int n = in.nextTokenInt();
        BinaryTuple[] domainBounds = new BinaryTuple[n];

        for (int i = 0; i < n; i++) {
            int lowerBound = in.nextTokenInt();
            in.nextTokenAssertChar(',');
            int upperBound = in.nextTokenInt();
            domainBounds[i] = new BinaryTuple(lowerBound, upperBound);
        }

        ArrayList<BinaryConstraint> constraints = readBinaryConstraints();
        BinaryCSP csp = new BinaryCSP(domainBounds, constraints);

        inFR.close();
        return csp;
    }

    /**
     * Reads a list of binary constraints.
     */
    private ArrayList<BinaryConstraint> readBinaryConstraints()
            throws IOException, ParseException {

        ArrayList<BinaryConstraint> constraints = new ArrayList<>();

        // 'c' or EOF
        in.nextToken();
        while (in.ttype != CSPStreamTokenizer.TT_EOF) {
            in.tokenAssertChar('c');

            in.nextTokenAssertChar('(');
            int lhs = in.nextTokenInt();

            in.nextTokenAssertChar(',');
            int rhs = in.nextTokenInt();

            in.nextTokenAssertChar(')');

            ArrayList<BinaryTuple> tuples = new ArrayList<>();
            for (in.nextToken(); in.ttype != CSPStreamTokenizer.TT_EOF
                    && in.ttype != 'c'; in.nextToken()) {

                int tuple_lhs = in.tokenInt();
                in.nextTokenAssertChar(',');
                int tuple_rhs = in.nextTokenInt();
                tuples.add(new BinaryTuple(tuple_lhs, tuple_rhs));
            }

            constraints.add(new BinaryConstraint(lhs, rhs, tuples));
        }

        return constraints;
    }
}
