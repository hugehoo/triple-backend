package triple.mileage.commons.error.exceptions;

import triple.mileage.commons.error.ErrorCode;

public class DuplicateReviewException extends BusinessException {

    public DuplicateReviewException() {
        super(ErrorCode.NO_CONTENT_EXCEPTION.getMessage(), ErrorCode.NO_CONTENT_EXCEPTION);
    }

    public DuplicateReviewException(String message) {
        super(message, ErrorCode.NO_CONTENT_EXCEPTION);
    }

}