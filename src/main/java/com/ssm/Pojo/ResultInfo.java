package com.ssm.Pojo;

import java.io.Serializable;

/*
* 后端返回结果封装
* */
public class ResultInfo implements Serializable {

    private boolean flag;       // 结果成功为true
    private String errorMessage; //错误信息
    private Object data;        // 返回给前端的数据

    public ResultInfo() {
    }


    public ResultInfo(boolean flag, String errorMessage, Object data) {
        this.flag = flag;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "flag=" + flag +
                ", errorMessage='" + errorMessage + '\'' +
                ", data=" + data +
                '}';
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
