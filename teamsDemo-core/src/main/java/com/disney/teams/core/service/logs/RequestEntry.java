package com.disney.teams.core.service.logs;

public enum RequestEntry {
    /**
     * 后台
     */
    b("background"),

    /**
     * http或https请求
     */
    h("http"),

    /**
     * grpc请求
     */
    g("grpc"),

    /**
     * mq消息
     */
    m("mq"),

    /**
     * canal消息
     */
    c("canal"),

    /**
     * 定时任务
     */
    q("quartz");

    private final String descr;


    private RequestEntry(String descr) {
        this.descr = descr;
    }

    public String descr() {
        return descr;
    }
}
