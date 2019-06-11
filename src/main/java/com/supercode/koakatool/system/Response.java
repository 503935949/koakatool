package com.supercode.koakatool.system;

public class Response {

    private String msg;

    private String data;

    private String stat;

    private boolean success;

    public Response() {
    }


    public Response( String data, String stat,String msg) {
        this.msg = msg;
        this.data = data;
        this.stat = stat;
    }

    public Response( boolean isSuccess,String msg) {
        this.success = isSuccess;
        this.msg = msg;
    }

    public Response( boolean isSuccess) {
        this.success = isSuccess;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        success = success;
    }

    public static Response SuccessResponse(){
        return new Response(true);
    }

    public static Response ErrorResponse(){
        return new Response(false);
    }

    public static Response ErrorResponse(String msg){
        return new Response(false,msg);
    }

}
