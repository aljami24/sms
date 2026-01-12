package com.smatik.sms.common.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private boolean isApiRequest(WebRequest request) {
        String path = request.getDescription(false);
        return path.contains("/api/");
    }

    @ExceptionHandler(Exception.class)
    public Object handleAllException(Exception ex, WebRequest request, Model model) {
        String message =
                "Oops! Something went wrong.\n Please refresh the page or try again in a moment.";

        ResponseApi<Object, Object> response = new ResponseApi<>(
                ex.getMessage(),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unsuccessful"
        );
        if (isApiRequest(request)) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        model.addAttribute("responseApi", response);
        return "exception"; // Thymeleaf page
    }

    @ExceptionHandler(UserNotFound.class)
    public Object handleUserNotFound(UserNotFound ex, WebRequest request, Model model) {

        ResponseApi<Object, Object> response = new ResponseApi<>(
                ex.getMessage(),
                null,
                HttpStatus.NOT_FOUND.value(),
                "Failed"
        );
        if (isApiRequest(request)) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        model.addAttribute("responseApi", response);
        return "exception";
    }

    @ExceptionHandler(NullPointerException.class)
    public Object handleNPE(NullPointerException ex, WebRequest request, Model model) {

        ResponseApi<Object, Object> response = new ResponseApi<>(
                ex.getMessage(),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Failed"
        );
        if (isApiRequest(request)) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        model.addAttribute("responseApi", response);
        return "exception";
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> Map.of("field", error.getField(), "errorMessage", error.getDefaultMessage()))
                .toList();
        ResponseApi<Object, Object> response = new ResponseApi<>(
                errors,
                null,
                HttpStatus.BAD_REQUEST.value(),
                "Failed"
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
