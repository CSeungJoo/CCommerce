package kr.cseungjoo.ccommerce.domain.order.exception;

import kr.cseungjoo.ccommerce.global.exception.BasicException;
import kr.cseungjoo.ccommerce.global.exception.ErrorCode;

public class OrderNotFoundException extends BasicException {
    public OrderNotFoundException() {
        super(ErrorCode.ORDER_NOT_FOUND);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.ORDER_NOT_FOUND;
    }
}
