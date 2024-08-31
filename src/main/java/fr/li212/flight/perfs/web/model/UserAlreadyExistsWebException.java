package fr.li212.flight.perfs.web.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserAlreadyExistsWebException extends WebException {
    public String getErrorCode() {
        return "USER_ALREADY_EXISTS";
    }
}
