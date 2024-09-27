package kr.cseungjoo.ccommerce.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.security.auth.login.FailedLoginException;

@Getter
public enum ErrorCode {

    ALREADY_USING_EMAIL("이미 사용중인 이메일 입니다.", HttpStatus.CONFLICT, "409"),
    LOGIN_FAILED("로그인에 실패하였습니다.", HttpStatus.UNAUTHORIZED, "401"),
    EMAIL_NOT_FOUND("이메일이 존재하지 않습니다.", HttpStatus.NOT_FOUND, "404");


    private final String msg;
    private final HttpStatus status;
    private final String code;

    ErrorCode(String msg, HttpStatus status, String code) {
        this.msg = msg;
        this.status = status;
        this.code = code;
    }

}
