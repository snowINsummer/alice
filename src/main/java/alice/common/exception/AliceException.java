package alice.common.exception;

import alice.common.constants.Constants;

/**
 * Created by zhangli on 11/7/2017.
 */
public class AliceException extends Exception {

    private int errorCode;
    private String message;
    private String info;

    public AliceException(Exception e){
        super(e);
    }

    public AliceException(String info){
        super(info);
    }

    public AliceException(int code, String info){
        this.errorCode = code;
        this.info = info;
        setMessage();
    }

    private void setMessage() {
        switch (this.errorCode){
            case Constants.DB_DATA_REPETITION:
                setMessage(info + "已存在");
                break;
            default:
                setMessage(info);
        }
    }

    public int getCode() {
        return errorCode;
    }

    public void setCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
