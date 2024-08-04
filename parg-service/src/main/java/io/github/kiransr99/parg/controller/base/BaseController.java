package io.github.kiransr99.parg.controller.base;

import io.github.kiransr99.parg.dto.GlobalApiResponse;
import io.github.kiransr99.parg.dto.GlobalErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class BaseController {

    public <T> ResponseEntity<GlobalApiResponse<T>> successResponse(T data) {
        GlobalApiResponse<T> response = new GlobalApiResponse<>(LocalDateTime.now(), "success", data, HttpStatus.OK.name());
        return ResponseEntity.ok(response);
    }

    public <T> ResponseEntity<GlobalApiResponse<T>> successResponse(T data, String message) {
        GlobalApiResponse<T> response = new GlobalApiResponse<>(LocalDateTime.now(), message, data, HttpStatus.OK.name());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<GlobalErrorResponse> errorResponse(HttpStatus status, String message, Exception exception) {
        GlobalErrorResponse response = new GlobalErrorResponse(LocalDateTime.now(), message, exception.getMessage(), status.name());
        response.setMessage(message);
        response.setError(exception.getMessage());
        return ResponseEntity.status(status).body(response);
    }
}
