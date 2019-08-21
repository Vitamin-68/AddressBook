package constants;

public enum ResponseCode {

    WRONG_DATA_TYPE(415),
    OBJECT_EXIST(302),
    NOT_FOUND(404);

    private int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
