package kr.cseungjoo.ccommerce.domain.review.exception;

import kr.cseungjoo.ccommerce.global.exception.BasicException;
import kr.cseungjoo.ccommerce.global.exception.ErrorCode;

public class ReviewNotFoundException extends BasicException {
    public ReviewNotFoundException() {
        super(ErrorCode.REVIEW_NOT_FOUND);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.REVIEW_NOT_FOUND;
    }
}
