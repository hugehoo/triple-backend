package triple.mileage.commons.error.exceptions;

import triple.mileage.commons.error.ErrorCode;

public class NoContentException extends BusinessException {

    public NoContentException() {
        super(ErrorCode.NO_CONTENT_EXCEPTION.getMessage(), ErrorCode.NO_CONTENT_EXCEPTION);
    }

    public NoContentException(String message) {
        super(message, ErrorCode.NO_CONTENT_EXCEPTION);
    }

}