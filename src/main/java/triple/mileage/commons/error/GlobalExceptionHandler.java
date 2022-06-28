package triple.mileage.commons.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import triple.mileage.commons.error.exceptions.BusinessException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleLineException(final BusinessException e) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        final ErrorCode errorCode = e.getErrorCode();
        final Errors errors = e.getErrors();
        final ErrorResponse response = ErrorResponse.of(errorCode, e.getMessage(), errors);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleBadRequestException(final MethodArgumentNotValidException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.NOT_ACCEPTABLE_STATUS_EXCEPTION,
                "유효성 검사 실패 : " + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> getBadRequestErrorResponseEntity(Errors errors) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        final ErrorCode errorCode = ErrorCode.BAD_REQUEST_EXCEPTION;

        StringBuilder message = new StringBuilder();
        message.append("Validation failed for Object '").append(errors.getObjectName()).append("' Bad Request");

        final ErrorResponse response = ErrorResponse.of(errorCode, message.toString(), errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
