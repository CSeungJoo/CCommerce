package kr.cseungjoo.ccommerce.domain.product.exception;

import kr.cseungjoo.ccommerce.global.exception.BasicException;
import kr.cseungjoo.ccommerce.global.exception.ErrorCode;

public class ProductNotFoundException extends BasicException {
    public ProductNotFoundException() {
        super(ErrorCode.PRODUCT_NOT_FOUNT);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.PRODUCT_NOT_FOUNT;
    }
}
