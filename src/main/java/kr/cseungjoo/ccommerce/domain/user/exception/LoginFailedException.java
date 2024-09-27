package kr.cseungjoo.ccommerce.domain.user.exception;

import kr.cseungjoo.ccommerce.global.exception.BasicException;
import kr.cseungjoo.ccommerce.global.exception.ErrorCode;

public class LoginFailedException extends BasicException {
    public LoginFailedException() {
        super(ErrorCode.LOGIN_FAILED);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.LOGIN_FAILED;
    }
}
