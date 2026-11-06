package com.rewardplus.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiError> handleRoleNotFoundException(
            RoleNotFoundException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleRoleAlreadyExistsException(
            RoleAlreadyExistsException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(
            UserNotFoundException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExistsException(
            UserAlreadyExistsException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyExistsException(
            EmailAlreadyExistsException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(
            Exception ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
public ResponseEntity<ApiError> handleCustomerNotFound(
        CustomerNotFoundException ex,
        HttpServletRequest request) {

    ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
    );

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}

@ExceptionHandler(CustomerAlreadyExistsException.class)
public ResponseEntity<ApiError> handleCustomerAlreadyExists(
        CustomerAlreadyExistsException ex,
        HttpServletRequest request) {

    ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.CONFLICT.value(),
            HttpStatus.CONFLICT.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
    );

    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
}

@ExceptionHandler(PhoneAlreadyExistsException.class)
public ResponseEntity<ApiError> handlePhoneAlreadyExists(
        PhoneAlreadyExistsException ex,
        HttpServletRequest request) {

    ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.CONFLICT.value(),
            HttpStatus.CONFLICT.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
    );

    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
}

@ExceptionHandler(LoyaltyAccountNotFoundException.class)
public ResponseEntity<ApiError> handleLoyaltyAccountNotFound(
        LoyaltyAccountNotFoundException ex,
        HttpServletRequest request) {

    ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
    );

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}

@ExceptionHandler(InsufficientPointsException.class)
public ResponseEntity<ApiError> handleInsufficientPoints(
        InsufficientPointsException ex,
        HttpServletRequest request) {

    ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
    );

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
}
@ExceptionHandler(RewardCategoryNotFoundException.class)
public ResponseEntity<ApiError> handleRewardCategoryNotFoundException(
        RewardCategoryNotFoundException ex,
        HttpServletRequest request) {

    ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
    );

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}

@ExceptionHandler(RewardNotFoundException.class)
public ResponseEntity<ApiError> handleRewardNotFoundException(
        RewardNotFoundException ex,
        HttpServletRequest request) {

    ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
    );

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}
@ExceptionHandler(RedemptionNotFoundException.class)
public ResponseEntity<ApiError> handleRedemptionNotFoundException(
        RedemptionNotFoundException ex,
        HttpServletRequest request) {

    ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
    );

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}
@ExceptionHandler(PromotionNotFoundException.class)
public ResponseEntity<ApiError> handlePromotionNotFoundException(
        PromotionNotFoundException ex,
        HttpServletRequest request) {

    ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
    );

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}
@ExceptionHandler(CustomerSegmentNotFoundException.class)
public ResponseEntity<ApiError> handleCustomerSegmentNotFoundException(
        CustomerSegmentNotFoundException ex,
        HttpServletRequest request) {

    ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
    );

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}

@ExceptionHandler(CustomerAddressNotFoundException.class)
public ResponseEntity<ApiError> handleCustomerAddressNotFoundException(
        CustomerAddressNotFoundException ex,
        HttpServletRequest request) {

    ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
    );

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}
@ExceptionHandler(AuditLogNotFoundException.class)
public ResponseEntity<ApiError> handleAuditLogNotFoundException(
        AuditLogNotFoundException ex,
        HttpServletRequest request) {

    ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
    );

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}
@ExceptionHandler(PromotionRuleNotFoundException.class)
public ResponseEntity<ApiError> handlePromotionRuleNotFoundException(
        PromotionRuleNotFoundException ex,
        HttpServletRequest request) {

    ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
    );

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}

}