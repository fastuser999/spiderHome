package com.ipl.spider.framework.task;

/**
 * @auther amxing
 * @desc
 * @createTime 2018/8/19 6:28
 */
public enum TaskCode {
    /**
     * 请求状态
     */
    SUCCESS(200, "执行成功"),
    FAILURE(500, "执行失败"),
    EXCEPTION(10100, "系统异常"),

    /**
     *
     */
    ;

    private int code;

    private String msg;

    TaskCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "TaskCode{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
