package com.boqii.plant.api;


import com.boqii.plant.api.helper.Result;

/**
 * API返回code
 *
 * @author bin.teng
 */
public class ApiException extends RuntimeException {

    public static final int UNKNOWN_DATA_FORMAT = 9108;

    private int resultCode;

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
        this.resultCode = resultCode;
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     *
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code) {
        String message = "";
        switch (code) {
            case UNKNOWN_DATA_FORMAT:
                message = "未知的数据格式";
                break;
            default:
                message = "未知错误";

        }
        return message;
    }
}