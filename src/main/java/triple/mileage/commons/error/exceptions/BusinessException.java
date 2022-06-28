package triple.mileage.commons.error.exceptions;

import org.springframework.validation.Errors;
import triple.mileage.commons.error.ErrorCode;

public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
    private Errors errors;

    public BusinessException(String message, ErrorCode errorCode, Errors errors) {
        super(message);
        this.errorCode = errorCode;
        this.errors = errors;
    }

    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Errors getErrors() {
        return errors;
    }
}
