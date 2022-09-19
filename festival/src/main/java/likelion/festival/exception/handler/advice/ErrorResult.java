package likelion.festival.exception.handler.advice;

import likelion.festival.exception.ExceptionCode;
import lombok.Data;

@Data
public class ErrorResult {
    private int code;
    private String message;

    public ErrorResult(ExceptionCode exceptionCode){
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}
