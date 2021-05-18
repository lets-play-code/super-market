package mob.code.supermarket.controller;

import mob.code.supermarket.dto.Response;
import mob.code.supermarket.model.SupermarketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SupermarketExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupermarketExceptionHandler.class);

    @ExceptionHandler(SupermarketException.class)
    public ResponseEntity<Response<String>> handlerException(SupermarketException exception) {
        if (exception.getCause() != null) {
            LOGGER.error("Something wrong", exception.getCause());
        }
        return new ResponseEntity<>(Response.error(exception.getMessage()), HttpStatus.OK);
    }
}
