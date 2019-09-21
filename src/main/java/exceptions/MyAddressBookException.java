package exceptions;

import constants.ResponseCode;

public class MyAddressBookException extends Exception {
    public ResponseCode code;
    private String message;

    public MyAddressBookException(ResponseCode code) {
        this.code = code;
    }

    public MyAddressBookException(ResponseCode code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseCode getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
