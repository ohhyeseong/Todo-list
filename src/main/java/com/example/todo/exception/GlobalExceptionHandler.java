package com.example.todo.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.todo.dto.ErrorResponse;

// 전역 예외 처리를 위한 클래스
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 모든 예외를 처리하는 핸들러 메서드
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {

        // ErrorResponse DTO를 사용해 에러 정보 생성
        ErrorResponse errorResponse = ErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // HTTP 500 내부 서버 오류 상태 코드
                "Internal Server Error",                  // 오류 유형
                ex.getMessage(),                          // 예외 메시지
                request.getRequestURI()                   // 요청 경로
        );
        // 응답 객체 생성 및 반환
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
