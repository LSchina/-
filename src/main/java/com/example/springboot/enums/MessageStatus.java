package com.example.springboot.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.example.springboot.common.BaseEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum MessageStatus implements BaseEnum {
    MESSAGE(0, "消息"),
    NOTICE(1, "公告"),
    NEWS(2, "新闻"),
    ;

    @JsonValue
    @EnumValue
    final
    int value;
    final String desc;

    MessageStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static MessageStatus of(Integer value){
        if (value == null) {
            return null;
        }
        for (MessageStatus status : values()) {
            if (status.equalsValue(value)) {
                return status;
            }
        }
        return null;
    }
}
