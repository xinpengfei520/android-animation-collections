package com.xpf.shakeanimation.bean;

/**
 * Created by xpf on 2016/12/18 :)
 * GitHub:xinpengfei520
 * Function:AppBean
 */

public class AppBean {

    private String appName;
    private int appIcon;

    public AppBean() {
    }

    public AppBean(String appName, int appIcon) {
        this.appName = appName;
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(int appIcon) {
        this.appIcon = appIcon;
    }

    @Override
    public String toString() {
        return "AppBean{" +
                "appName='" + appName + '\'' +
                ", appIcon=" + appIcon +
                '}';
    }
}
