package ec.edu.ups.sgadnuxt.exception.models;

import java.time.LocalDateTime;

public record ApiError(
                String path,
                String message,
                String error,
                int statusCode,
                LocalDateTime timestamp) {
}
