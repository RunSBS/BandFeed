package common.exception;

import common.dto.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 각 서비스의 ErrorCode를 구현한 예외 처리
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonResponse<?>> handleBusinessException(BusinessException e) {
        log.warn("BusinessException: {}", e.getMessage());
        return CommonResponse.error(e.getErrorCode());
    }

    // @Valid 검증 실패 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<?>> handleValidationException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = bindingResult.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse(CommonErrorCode.INVALID_INPUT.getMessage());
        log.warn("ValidationException: {}", message);
        return CommonResponse.error(CommonErrorCode.INVALID_INPUT, message);
    }

    // 그 외 예상치 못한 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<?>> handleException(Exception e) {
        log.error("Unexpected Exception: ", e);
        return CommonResponse.error(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }
}
