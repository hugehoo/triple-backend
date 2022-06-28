package triple.mileage.commons.error;

public enum ErrorCode {
    // Http Status 2xx (Success)
    NO_CONTENT_EXCEPTION(204, "S001", "No Content Exception"),

    // Http Status 4xx (Request)
    UN_AUTHORIZATION_EXCEPTION(401, "R001", "Un Authorization Exception"),
    HANDLE_ACCESS_DENIED_EXCEPTION(403, "R003", "Handle Access Is Denied Exception"),
    METHOD_NOT_ALLOWED(405, "R002", "Method Not Allowed Exception"),
    BAD_REQUEST_EXCEPTION(400, "R004", "Bad Request Exception"),
    ILLEGAL_ARGUMENT_EXCEPTION(400, "R005", "Illegal Argument Exception"),
    ILLEGAL_STATUS_EXCEPTION(400, "R006", "Illegal Status Exception"),
    NOT_ACCEPTABLE_STATUS_EXCEPTION(400, "R006", "Not Acceptable Status Exception"),
    USER_NOT_FOUND_EXCEPTION(400, "R007", "User Not Found Exception"),
    ACCESS_DENIED_EXCEPTION(400, "R008", "Access Is Denied Exception"),

    // Http Status 5xx (Internal)
    INTERNAL_SERVER_ERROR(500, "I001", "Server Error"),

    EMPTY_EXCEPTION(999, "", "안씀");

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}
