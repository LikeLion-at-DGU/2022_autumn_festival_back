package likelion.festival.exception.handler.advice;

import likelion.festival.exception.ExceptionCode;
import likelion.festival.exception.WrongBoothId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongBoothId.class)
    public ErrorResult wrongBoothId(WrongBoothId e) {
        return new ErrorResult(ExceptionCode.WRONG_BOOTH_ID);
    }
}
