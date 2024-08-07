package io.github.kiransr99.parg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private String error;
    private String httpStatus;
}
