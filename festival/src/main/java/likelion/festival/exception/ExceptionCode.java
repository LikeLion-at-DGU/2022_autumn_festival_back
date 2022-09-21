package likelion.festival.exception;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {
    WRONG_BOOTH_ID(HttpStatus.BAD_REQUEST.value(), "잘못된 페이지입니다.");

    private final int code;
    private final String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;

    }
}