package kr.cseungjoo.ccommerce.domain.cartItem.exception;

import kr.cseungjoo.ccommerce.global.exception.BasicException;
import kr.cseungjoo.ccommerce.global.exception.ErrorCode;

public class InsufficientStockException extends BasicException {
    public InsufficientStockException() {
        super(ErrorCode.INSUFFICIENT_STOCK);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INSUFFICIENT_STOCK;
    }
}
