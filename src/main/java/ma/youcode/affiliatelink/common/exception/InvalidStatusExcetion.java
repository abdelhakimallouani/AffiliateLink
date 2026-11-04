package ma.youcode.affiliatelink.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidStatusExcetion extends ResponseStatusException {
    public InvalidStatusExcetion(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
