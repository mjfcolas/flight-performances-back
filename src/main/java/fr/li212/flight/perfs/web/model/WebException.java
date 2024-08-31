package fr.li212.flight.perfs.web.model;

public abstract class WebException extends RuntimeException {
    public abstract String getErrorCode();
}
