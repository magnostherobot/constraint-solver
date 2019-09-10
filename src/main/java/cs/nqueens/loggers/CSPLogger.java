package cs.nqueens.loggers;

public interface CSPLogger {
    void log(String msg, int depth);

    default void log(String msg) {
        log(msg, 0);
    }
}
