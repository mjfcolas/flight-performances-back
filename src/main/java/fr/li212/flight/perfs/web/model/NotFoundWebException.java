package fr.li212.flight.perfs.web.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundWebException extends WebException {
    public String getErrorCode() {
        return "RESOURCE_NOT_FOUND";
    }
}
