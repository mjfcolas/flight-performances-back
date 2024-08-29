package fr.li212.flight.perfs.domain.model.exception;

public class UnexpectedException extends RuntimeException{

    public UnexpectedException(final Throwable cause) {
        super(cause);
    }
}
