package it.beergash.tennis.data.scraper.model.exceptions;

public class MongoDbWriterException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MongoDbWriterException() {
    }

    public MongoDbWriterException(String s) {
        super(s);
    }

    public MongoDbWriterException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public MongoDbWriterException(Throwable throwable) {
        super(throwable);
    }
}
