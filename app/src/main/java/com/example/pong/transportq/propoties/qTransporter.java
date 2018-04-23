package com.example.pong.transportq.propoties;

/**
 * Created by Pong on 4/19/2018.
 */

public class qTransporter {

    private String Login_Code;
    private String DocNo;
    private String xName;
    private String FName;
    private String DueDate;
    private String IsSend;
    private String index;
    private String detail;
    private String tqDI;

    public String getDocNo() {
        return DocNo;
    }

    public void setDocNo(String docNo) {
        DocNo = docNo;
    }

    public String getxName() {
        return xName;
    }

    public void setxName(String xName) {
        this.xName = xName;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getIsSend() {
        return IsSend;
    }

    public void setIsSend(String isSend) {
        IsSend = isSend;
    }

    public String getLogin_Code() {
        return Login_Code;
    }

    public void setLogin_Code(String login_Code) {
        Login_Code = login_Code;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTqDI() {
        return tqDI;
    }

    public void setTqDI(String tqDI) {
        this.tqDI = tqDI;
    }
}
