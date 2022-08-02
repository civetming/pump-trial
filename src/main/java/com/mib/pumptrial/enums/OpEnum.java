package com.mib.pumptrial.enums;

/**
 * 描述: 操作枚举
 *
 * @author rb.ming
 */

public enum OpEnum {
    TEST(1000, "测试连接"),
    LIST(1001, "在线设备"),
    INFUSION(1002, "胰岛输注"),
    BASE(1003, "基础率"),
    TEMP_BASE(1004, "临时基础率"),
    LARGE(1005, "大剂量"),
    ECHO(1006, "胰岛素泵回显"),
    STOP_TEMP_BASE(2001, "停止临时基础率"),
    STOP_LARGE(2002, "停止大剂量"),
    ;
    private final Integer code;

    private final String name;

    OpEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
