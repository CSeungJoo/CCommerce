package kr.cseungjoo.ccommerce.domain.order.exception;

import kr.cseungjoo.ccommerce.global.exception.BasicException;
import kr.cseungjoo.ccommerce.global.exception.ErrorCode;

public class OrderCancelFailException extends BasicException {
    public OrderCancelFailException(ErrorCode errorCode) {
        super(errorCode);
    }
    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }
}
