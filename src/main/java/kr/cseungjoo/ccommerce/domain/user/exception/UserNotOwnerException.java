package kr.cseungjoo.ccommerce.domain.user.exception;

import kr.cseungjoo.ccommerce.global.exception.BasicException;
import kr.cseungjoo.ccommerce.global.exception.ErrorCode;

public class UserNotOwnerException extends BasicException {
    public UserNotOwnerException() {
        super(ErrorCode.USER_NOT_OWNER);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.USER_NOT_OWNER;
    }
}
