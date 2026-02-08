package org.example.paymentservice.model.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponseDto {
    private final LocalDateTime timestamp;
    private final int statusCode;
    private final String message;

}
