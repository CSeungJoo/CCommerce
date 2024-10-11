package kr.cseungjoo.ccommerce.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.security.auth.login.FailedLoginException;

@Getter
public enum ErrorCode {
    //user
    ALREADY_USING_EMAIL("이미 사용중인 이메일 입니다.", HttpStatus.CONFLICT, "1409"),
    ALREADY_VALIDATION("이미 인증된 계정입니다.", HttpStatus.BAD_REQUEST, "1400"),
    LOGIN_FAILED("로그인에 실패하였습니다.", HttpStatus.UNAUTHORIZED, "1401"),
    EMAIL_NOT_FOUND("이메일이 존재하지 않습니다.", HttpStatus.NOT_FOUND, "1404"),
    //product
    PRODUCT_NOT_FOUNT("상품을 찾을수 없습니다.", HttpStatus.NOT_FOUND, "2404"),
    //cart
    //cartItem
    CART_ITEM_NOT_FOUND("장바구니 안에 존재하지 않습니다.", HttpStatus.NOT_FOUND, "4404"),
    INSUFFICIENT_STOCK("구매 수량은 상품 수량보다 높을 수 없습니다.", HttpStatus.BAD_REQUEST, "4400");


    private final String msg;
    private final HttpStatus status;
    private final String code;

    ErrorCode(String msg, HttpStatus status, String code) {
        this.msg = msg;
        this.status = status;
        this.code = code;
    }

}
