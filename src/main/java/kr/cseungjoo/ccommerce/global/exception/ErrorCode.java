package kr.cseungjoo.ccommerce.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.security.auth.login.FailedLoginException;

@Getter
public enum ErrorCode {
    //user
    ALREADY_USING_EMAIL("이미 사용중인 이메일 입니다.", HttpStatus.CONFLICT, "14090"),
    ALREADY_VALIDATION("이미 인증된 계정입니다.", HttpStatus.BAD_REQUEST, "14000"),
    LOGIN_FAILED("로그인에 실패하였습니다.", HttpStatus.UNAUTHORIZED, "14010"),
    EMAIL_NOT_FOUND("이메일이 존재하지 않습니다.", HttpStatus.NOT_FOUND, "14040"),
    USER_NOT_FOUND("존재하지 않은 유저입니다.", HttpStatus.NOT_FOUND, "14041"),
    USER_NOT_OWNER("해당 유저는 그것의 주인이 아닙니다.", HttpStatus.UNAUTHORIZED, "14010"),
    //product
    PRODUCT_NOT_FOUNT("상품을 찾을수 없습니다.", HttpStatus.NOT_FOUND, "24040"),
    //cart
    //cartItem
    CART_ITEM_NOT_FOUND("장바구니 안에 존재하지 않습니다.", HttpStatus.NOT_FOUND, "44040"),
    INSUFFICIENT_STOCK("구매 수량은 상품 수량보다 높을 수 없습니다.", HttpStatus.BAD_REQUEST, "44000"),
    //order
    ORDER_NOT_FOUND("주문을 찾을수 없습니다.", HttpStatus.NOT_FOUND, "44040"),
    ORDER_CANCEL_FAIL_IN_TRANSIT("주문 취소를 실패 하였습니다. 이유: 이미 배송 시작함", HttpStatus.CONFLICT, "44090");
    //orderItem

    private final String msg;
    private final HttpStatus status;
    private final String code;

    ErrorCode(String msg, HttpStatus status, String code) {
        this.msg = msg;
        this.status = status;
        this.code = code;
    }

}
