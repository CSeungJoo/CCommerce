package kr.cseungjoo.ccommerce.domain.user.exception;

import kr.cseungjoo.ccommerce.global.exception.BasicException;
import kr.cseungjoo.ccommerce.global.exception.ErrorCode;

public class AlreadyUsingEmailException extends BasicException {
    public AlreadyUsingEmailException() {
        super(ErrorCode.ALREADY_USING_EMAIL);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.ALREADY_USING_EMAIL;
    }
}
