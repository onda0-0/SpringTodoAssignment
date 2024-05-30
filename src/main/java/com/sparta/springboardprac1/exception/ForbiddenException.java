package com.sparta.springboardprac1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//HTTP 상태 코드: 403 Forbidden / 클라이언트가 요청한 작업을 수행할 권한이 없음. 클라이언트가 리소스에 접근할 권한이 없음 ex)다른 사용자의 댓글 수정 및 삭제시 발생
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}