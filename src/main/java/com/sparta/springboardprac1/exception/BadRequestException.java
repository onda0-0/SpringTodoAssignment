package com.sparta.springboardprac1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//HTTP 상태 코드: 400 Bad Request /잘못된 클라이언트 요청. 요청을 수정하여 다시 시도해야함
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
