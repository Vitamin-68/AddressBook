package service.impl;

public enum ResponseCode {
    WRONG_DATA_TYPE;

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
