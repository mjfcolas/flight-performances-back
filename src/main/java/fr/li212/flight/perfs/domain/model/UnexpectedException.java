package fr.li212.flight.perfs.domain.model;

public class UnexpectedException extends RuntimeException{

    public UnexpectedException(final Throwable cause) {
        super(cause);
    }
}
