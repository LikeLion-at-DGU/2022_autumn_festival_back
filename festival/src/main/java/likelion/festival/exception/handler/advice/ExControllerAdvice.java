package likelion.festival.exception.handler.advice;
import likelion.festival.exception.ExceptionCode;
import likelion.festival.exception.WrongBoothId;
import likelion.festival.exception.WrongCommentId;
import likelion.festival.exception.WrongPassword;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongBoothId.class)
    public ErrorResult wrongBoothId(WrongBoothId e) {
        return new ErrorResult(ExceptionCode.WRONG_BOOTH_ID);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongCommentId.class)
    public ErrorResult wrongCommentId(WrongCommentId e){
        return new ErrorResult(ExceptionCode.WRONG_COMMENT_ID);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongPassword.class)
    public ErrorResult wrongPassword(WrongPassword e){
        return new ErrorResult(ExceptionCode.WRONG_PASSWORD);
    }
}
