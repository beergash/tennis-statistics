package it.beergash.tennis.data.scanner.model.exceptions;

public class TennisScannerException extends Exception {

    private static final long serialVersionUID = 1L;

    public TennisScannerException() {
    }

    public TennisScannerException(String s) {
        super(s);
    }

    public TennisScannerException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TennisScannerException(Throwable throwable) {
        super(throwable);
    }
}
