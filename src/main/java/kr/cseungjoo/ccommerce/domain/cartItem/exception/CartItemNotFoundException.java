package kr.cseungjoo.ccommerce.domain.cartItem.exception;

import kr.cseungjoo.ccommerce.global.exception.BasicException;
import kr.cseungjoo.ccommerce.global.exception.ErrorCode;

public class CartItemNotFoundException extends BasicException {
    public CartItemNotFoundException() {
        super(ErrorCode.CART_ITEM_NOT_FOUND);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.CART_ITEM_NOT_FOUND;
    }
}
