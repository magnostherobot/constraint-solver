import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.text.ParseException;

class CSPStreamTokenizer extends StreamTokenizer {
    public CSPStreamTokenizer(FileReader fr) {
        super(fr);

        this.ordinaryChar('(');
        this.ordinaryChar(')');
        this.ordinaryChar(',');
        this.ordinaryChar('c');

        this.slashSlashComments(true);

        this.eolIsSignificant(false);
        this.parseNumbers();
    }

    public int nextToken() throws IOException {
        int result = super.nextToken();
        System.out.println(this);
        return result;
    }

    private void tokenAssertType(int expected) throws ParseException {
        if (this.ttype != expected) {
            throw new ParseException(this.toString(), this.lineno());
        }
    }

    public int tokenInt() throws ParseException {
        this.tokenAssertType(TT_NUMBER);
        return (int) this.nval;
    }

    public int nextTokenInt() throws IOException, ParseException {
        this.nextToken();
        return this.tokenInt();
    }

    public void tokenAssertChar(char expected) throws ParseException {
        this.tokenAssertType((int) expected);
    }

    public void nextTokenAssertChar(char expected)
            throws ParseException, IOException {
        this.nextToken();
        this.tokenAssertChar(expected);
    }

    private void tokenAssertWord(String expected) throws ParseException {
        this.tokenAssertType(TT_WORD);
        if (!expected.equals(this.sval)) {
            throw new ParseException(this.toString(), this.lineno());
        }
    }

    public void nextTokenAssertWord(String expected)
            throws IOException, ParseException {

        this.nextToken();
        this.tokenAssertWord(expected);
    }

    public void nextTokenAssertEol() throws ParseException, IOException {
        this.nextToken();
        this.tokenAssertType(TT_EOL);
    }
}
