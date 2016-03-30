package com.iselsoft.easyium;


public enum WebDriverType {
    IE("ie"), FIREFOX("firefox"), CHROME("chrome"), OPERA("opera"), SAFARI("safari"), EDGE("edge"), ANDROID("android"), IOS("ios");

    private String name;

    private WebDriverType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
