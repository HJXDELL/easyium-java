package com.iselsoft.easyium;


public enum WebDriverType {
    IE("ie"), FIREFOX("firefox"), CHROME("chrome"), OPERA("opera"), SAFARI("safari"), EDGE("edge"), ANDROID("android"), IOS("ios");

    public final static WebDriverType[] BROWSER = new WebDriverType[]{IE, FIREFOX, CHROME, OPERA, SAFARI, EDGE};

    public final static WebDriverType[] MOBILE = new WebDriverType[]{ANDROID, IOS};

    private final String name;

    WebDriverType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
