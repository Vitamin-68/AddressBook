package constants;

public enum ResponseCode {
    WRONG_DATA_TYPE(302);

    private int code;

    ResponseCode(int code) {

    }

    public int getCode() {
        return code;
    }

}
