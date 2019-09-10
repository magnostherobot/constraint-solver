package cs.nqueens;

import cs.nqueens.exceptions.EmptyDomainException;
import cs.nqueens.exceptions.ManyValuesException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

abstract class ConstraintSolverTest {

    protected abstract CSPResult solve(BinaryCSP csp) throws ManyValuesException, EmptyDomainException;

    public void findSolutionCommon(String filename) throws IOException,
        ParseException, ManyValuesException, EmptyDomainException {

        BinaryCSPReader reader = new BinaryCSPReader();
        BinaryCSP csp = reader.readBinaryCSP(filename);

        CSPResult solution = solve(csp);
        assertTrue(solution.solved);

        System.out.println(solution);
    }

    @Test
    public void findSolution4Queens() throws IOException, ParseException, ManyValuesException, EmptyDomainException {
        findSolutionCommon("src/main/resources/nqueens/4Queens.csp");
    }

    @Test
    public void findSolution6Queens() throws IOException, ParseException, ManyValuesException, EmptyDomainException {
        findSolutionCommon("src/main/resources/nqueens/6Queens.csp");
    }

    @Test
    public void findSolution8Queens() throws IOException, ParseException, ManyValuesException, EmptyDomainException {
        findSolutionCommon("src/main/resources/nqueens/8Queens.csp");
    }

    @Test
    public void findSolution10Queens() throws IOException, ParseException, ManyValuesException, EmptyDomainException {
        findSolutionCommon("src/main/resources/nqueens/10Queens.csp");
    }

    @Test
    public void findSolutionFinnishSudoku() throws IOException, ParseException, ManyValuesException, EmptyDomainException {
        findSolutionCommon("src/main/resources/sudoku/FinnishSudoku.csp");
    }

    @Test
    public void findSolutionSimonisSudoku() throws IOException, ParseException, ManyValuesException, EmptyDomainException {
        findSolutionCommon("src/main/resources/sudoku/SimonisSudoku.csp");
    }

    @Test
    public void findSolutionLangfords2_3() throws IOException, ParseException, ManyValuesException, EmptyDomainException {
        findSolutionCommon("src/main/resources/langford/langfords2_3.csp");
    }

    @Test
    public void findSolutionLangfords2_4() throws IOException, ParseException, ManyValuesException, EmptyDomainException {
        findSolutionCommon("src/main/resources/langford/langfords2_4.csp");
    }

    @Test
    public void findSolutionLangfords3_9() throws IOException, ParseException, ManyValuesException, EmptyDomainException {
        findSolutionCommon("src/main/resources/langford/langfords3_9.csp");
    }

    @Test
    public void findSolutionLangfords3_10() throws IOException, ParseException, ManyValuesException, EmptyDomainException {
        findSolutionCommon("src/main/resources/langford/langfords3_10.csp");
    }
}
