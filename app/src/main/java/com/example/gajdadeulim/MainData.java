package com.example.gajdadeulim;

public class MainData {
    private int iv_profile;
    private String errandName;
    private String errandTime;
    private String errandContent;
    private String errandPrice;
    private String errandProgress;
    private String errandTitle;
    private int o_Number;

    public MainData(int iv_profile, String errandName, String errandTime, String errandContent, String errandPrice, String errandProgress, String errandTitle, int o_Number) {
        this.errandTitle = errandTitle;
        this.iv_profile = iv_profile;
        this.errandName = errandName;
        this.errandTime = errandTime;
        this.errandContent = errandContent;
        this.errandPrice = errandPrice;
        this.errandProgress = errandProgress;
        this.o_Number = o_Number;
    }

    public int getIv_profile() {
        return iv_profile;
    }

    public void setIv_profile(int iv_profile) {
        this.iv_profile = iv_profile;
    }

    public String getErrandName() {
        return errandName;
    }

    public void setErrandName(String errandName) {
        this.errandName = errandName;
    }

    public String getErrandTime() {
        return errandTime;
    }

    public void setErrandTime(String errandTime) {
        this.errandTime = errandTime;
    }

    public String getErrandContent() {
        return errandContent;
    }

    public void setErrandContent(String errandContent) {
        this.errandContent = errandContent;
    }

    public String getErrandPrice() {
        return errandPrice;
    }

    public void setErrandPrice(String errandPrice) {
        this.errandPrice = errandPrice;
    }

    public String getErrandProgress() { return errandProgress; }

    public void setErrandProgress(String errandProgress) { this.errandProgress = errandProgress;
    }

    public String getErrandTitle() { return errandTitle; }

    public void setErrandTitle(String errandTitle) { this.errandTitle = errandTitle; }

    public int getO_Number() { return o_Number; }

    public void setO_Number(int o_Number) { this.o_Number = o_Number; }
}
