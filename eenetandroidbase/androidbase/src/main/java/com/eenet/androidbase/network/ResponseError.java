package com.eenet.androidbase.network;

/**
 * Created by xiaoma on 2018/1/19.
 */

public class ResponseError {

    private final int mCode;

    private final String mMsg;

    private final ErrorType mType;

    public ResponseError(Builder builder){
        this.mCode = builder.code;
        this.mMsg = builder.msg;
        this.mType = builder.type;
    }


    public int getCode() {
        return mCode;
    }


    public String getMsg() {
        return mMsg;
    }

    public ErrorType getType() {
        return mType;
    }

    public static class Builder{

        private int code;
        private String msg;
        private ErrorType type;

        public Builder code(int code){
            this.code = code;
            return this;
        }

        public Builder msg(String msg){
            this.msg = msg;
            return this;
        }

        public Builder type(ErrorType type){
            this.type = type;
            return this;
        }

        public ResponseError build(){
            return new ResponseError(this);
        }
    }

}
