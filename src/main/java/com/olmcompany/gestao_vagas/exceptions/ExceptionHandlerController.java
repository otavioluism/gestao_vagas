package com.olmcompany.gestao_vagas.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController {

    private final MessageSource messageSource;

    public ExceptionHandlerController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ExceptionDTO>> methodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<ExceptionDTO> messagesErrors = new ArrayList<>();

        e.getFieldErrors().forEach(err -> {
            String message = this.messageSource.getMessage(err, LocaleContextHolder.getLocale());
            ExceptionDTO exception = new ExceptionDTO(message, err.getField());
            messagesErrors.add(exception);
        });

        return new ResponseEntity<>(messagesErrors, HttpStatus.BAD_REQUEST);

    }
}
