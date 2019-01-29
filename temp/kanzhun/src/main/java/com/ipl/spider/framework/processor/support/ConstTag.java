package com.ipl.spider.framework.processor.support;

/**
 * @author dev
 * @desc 定义一些常量字段
 */

public enum ConstTag {

    //处理器状态
    CLOSE(1, "关闭,不在处理任何任务"),
    OPEN(2, "运行"),
    //获取处理器中的数量时用到
    ACTIVE(1, "正在执行的任务数量"),
    WAIT(2, "等待执行的任务数量"),
    COMPLETE(3, "已完成的任务数量");

    private int code;
    private String desc;

    ConstTag(int status, String desc) {
        this.code = status;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public boolean equals(ConstTag tag) {
        return this.code == tag.getCode();
    }

}
