package com.mib.pumptrial.common.domain;

import java.io.Serializable;

/**
 * 描述: 消息入参
 *
 * @author rb.ming
 * @create 2022-07-12 14:20
 */
public class MessageDTO implements Serializable {

    private static final long serialVersionUID = 8021325306282861073L;

    /**
     * 发送方
     **/
    private String fromId;

    /**
     * 接收方
     **/
    private String toId;

    /**
     * 操作码
     **/
    private Integer opcode;


    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public Integer getOpcode() {
        return opcode;
    }

    public void setOpcode(Integer opcode) {
        this.opcode = opcode;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "fromId='" + fromId + '\'' +
                ", toId='" + toId + '\'' +
                ", opcode=" + opcode +
                '}';
    }
}
