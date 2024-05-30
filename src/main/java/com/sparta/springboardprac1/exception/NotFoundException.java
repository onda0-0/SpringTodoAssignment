package com.sparta.springboardprac1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//HTTP 상태 코드: 404 Not Found / 요청한 리소스가 서버에 없음. ex)존재하지 않는 일정, 댓글요청시 발생
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
