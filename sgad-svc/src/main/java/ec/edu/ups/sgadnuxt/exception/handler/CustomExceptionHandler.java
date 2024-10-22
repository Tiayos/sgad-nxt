package ec.edu.ups.sgadnuxt.exception.handler;

import ec.edu.ups.sgadnuxt.exception.CustomException;
import ec.edu.ups.sgadnuxt.exception.models.ErrorResponseCustom;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

        @ExceptionHandler({NoHandlerFoundException.class})
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public ResponseEntity<String> handleNotFoundError(NoHandlerFoundException ex) {
                String errorMessage = "La ruta solicitada no existe";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }



        @ExceptionHandler(CustomException.class)
        public ResponseEntity<ErrorResponseCustom> handleException(CustomException e, HttpServletRequest request) {
                return ResponseEntity.status(e.getHttpStatus()).body(
                                new ErrorResponseCustom(
                                                LocalDateTime.now(),
                                                e.getHttpStatus(),
                                                e.getMessage(),
                                                request.getRequestURI()));
        }

        @ResponseBody
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(RuntimeException.class)
        public ErrorResponseCustom handleValidationException(MethodArgumentNotValidException ex,
                        HttpServletRequest request

        ) {
                Map<String, String> errors = new HashMap<>();

                ex.getBindingResult().getAllErrors().forEach((error) -> {
                        String fieldName = ((FieldError) error).getField();
                        String errorMessage = error.getDefaultMessage();
                        errors.put(fieldName, errorMessage);
                });

                LocalDateTime timestamp = LocalDateTime.now();

                ErrorResponseCustom errorResponse = new ErrorResponseCustom(timestamp,
                                ex.getStatusCode(),
                                ex.getBody().getDetail(), ex.getBody().getInstance().getPath(), errors);
                return errorResponse;

        }

}
