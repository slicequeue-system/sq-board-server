package app.slicequeue.sq_board.common.exception;

import app.slicequeue.common.base.BaseRuntimeException;
import app.slicequeue.common.dto.CommonErrorResponse;
import jakarta.validation.constraints.NotNull;
import lombok.Generated;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @Generated
    private static final Logger log = LogManager.getLogger(RestResponseExceptionHandler.class);

    public RestResponseExceptionHandler() {
    }

    @ExceptionHandler({BaseRuntimeException.class})
    ResponseEntity<Object> handleBaseRuntimeException(BaseRuntimeException ex) {
        log.error(ex);
        return handleExceptionAndGetResponseEntity(ex);
    }

    @ExceptionHandler({RuntimeException.class})
    ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        log.error(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CommonErrorResponse.from(ex));
    }

    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity(CommonErrorResponse.builder().code(400).message("요청 본문의 형식이 올바르지 않습니다.").build(), HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Map<String, Object> detail = new HashMap();
        Iterator var7 = fieldErrors.iterator();

        while(var7.hasNext()) {
            FieldError fieldError = (FieldError)var7.next();
            detail.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity(CommonErrorResponse.builder().code(400).message("입력값이 올바르지 않습니다.").detail(detail).build(), HttpStatus.BAD_REQUEST);
    }

    @NotNull
    private static ResponseEntity<Object> handleExceptionAndGetResponseEntity(BaseRuntimeException ex) {
        return ResponseEntity.status(ex.getCode()).body(CommonErrorResponse.from(ex));
    }
}
