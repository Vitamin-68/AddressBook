package constants;

public enum ResponseCode {

    WRONG_DATA_TYPE(415),
    NOT_FOUND(404),
    FAILED_SAVE_DB(410),
    FAILED_GET_DATA(411),
    FAILED_DELETE_CONTACT_FROM_DB(412);

    private int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
