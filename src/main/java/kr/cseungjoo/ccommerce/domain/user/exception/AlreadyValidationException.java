package kr.cseungjoo.ccommerce.domain.user.exception;

import kr.cseungjoo.ccommerce.global.exception.BasicException;
import kr.cseungjoo.ccommerce.global.exception.ErrorCode;

public class AlreadyValidationException extends BasicException {
    public AlreadyValidationException() {
        super(ErrorCode.ALREADY_VALIDATION);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.ALREADY_VALIDATION;
    }
}
