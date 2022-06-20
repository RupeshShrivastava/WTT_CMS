package com.halodoc.cmstests;

/**
 * @author praveenkumardn
 * This Enum class contains all the error codes and messages
 */
public enum ErrorCodesAndMessages {

    BAD_REQUEST_GENERIC_EXCEPTION("400", "Unable to process JSON"),
    GROUP_ID_NOT_FOUND_EXCEPTION("404", "Group was not found");


    private String errCode;
    private String errMsg;
    private ErrorCodesAndMessages(String errCode, String errMsg){
        this.errCode = errCode ;
        this.errMsg = errMsg ;
    }
    public String errCode(){
        return this.errCode.toString() ;
    }
    public String errMsg(){
        return this.errMsg.toString() ;
    }
}
