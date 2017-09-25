package exception;

/**
 * Created by Administrator on 2017/5/19.
 */
public class MsgException extends RuntimeException {
    public MsgException() {
        super();
    }

    public MsgException(String message) {
        super(message);
    }

    public MsgException(String message, Throwable cause) {
        super(message, cause);
    }

    public MsgException(Throwable cause) {
        super(cause);
    }

}
