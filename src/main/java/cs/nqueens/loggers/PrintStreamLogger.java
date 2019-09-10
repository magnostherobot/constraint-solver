package cs.nqueens.loggers;

import java.io.OutputStream;
import java.io.PrintStream;

public class PrintStreamLogger implements CSPLogger {
    PrintStream out;

    public PrintStreamLogger(PrintStream out) {
        this.out = out;
    }

    public PrintStreamLogger() {
        this(System.out);
    }

    @Override
    public void log(String msg, int depth) {
        out.println("" + depth + ": " + msg);
    }
}
