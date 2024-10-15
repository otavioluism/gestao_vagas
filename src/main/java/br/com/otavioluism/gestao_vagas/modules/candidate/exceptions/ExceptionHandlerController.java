package br.com.otavioluism.gestao_vagas.modules.candidate.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice // anotacao responsável por trabalhar com exceções, tem como objetivo monitorar as exceções para saber manipular
public class ExceptionHandlerController {

    private MessageSource messageSource;

    public ExceptionHandlerController(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // este tipo de anotação tem como objetivo mapear a exceção, quando cair no cenário MethodArgumentNotValidException ele realizará esta função
    public ResponseEntity<List<ErrorMessageDTO>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ErrorMessageDTO> dto = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach(err -> {
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale()); // realiza um tratamento na mensagem para uma forma mais amigável, pegando somente a mensagem em nosso caso o defaultMessage, utilizando o idioma local do Brasil
            ErrorMessageDTO error = new ErrorMessageDTO(message, err.getField());
            dto.add(error);
        });


        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

}
