package triple.mileage.commons.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.Errors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String message;
    private int status;
    private Errors errors;
    private String code;

    private ErrorResponse(final ErrorCode code, final String message, final Errors errors) {
        this.message = message;
        this.status = code.getStatus();
        this.errors = errors;
        this.code = code.getCode();
    }

    private ErrorResponse(final ErrorCode code, final Errors errors) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.errors = errors;
        this.code = code.getCode();
    }

    ErrorResponse(final ErrorCode code, final String message) {
        this.message = message;
        this.status = code.getStatus();
        this.code = code.getCode();
        this.errors = null;
    }

    private ErrorResponse(final ErrorCode code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
        this.errors = null;
    }

    public static ErrorResponse of(final ErrorCode code, final String message, final Errors errors) {
        return new ErrorResponse(code, message, errors);
    }

    public static ErrorResponse of(final ErrorCode code, final Errors errors) {
        return new ErrorResponse(code, errors);
    }

    public static ErrorResponse of(final ErrorCode code, final String message) {
        return new ErrorResponse(code, message);
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

}
