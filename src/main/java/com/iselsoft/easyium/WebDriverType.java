package com.iselsoft.easyium;


import java.util.Arrays;
import java.util.List;

public enum WebDriverType {
    IE("ie"), FIREFOX("firefox"), CHROME("chrome"), OPERA("opera"), SAFARI("safari"), EDGE("edge"), PHANTOMJS("phantomjs"), REMOTE("remote"), ANDROID("android"), IOS("ios");

    public final static List<WebDriverType> BROWSER = Arrays.asList(IE, FIREFOX, CHROME, OPERA, SAFARI, EDGE, PHANTOMJS, REMOTE);

    public final static List<WebDriverType> MOBILE = Arrays.asList(ANDROID, IOS);

    private final String name;

    WebDriverType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
