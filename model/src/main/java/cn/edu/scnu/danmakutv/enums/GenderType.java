package cn.edu.scnu.danmakutv.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum GenderType {
    FEMALE(0, "女"),
    MALE(1, "男"),
    UNKNOWN(2, "未知");

    @EnumValue
    private final int code;

    private final String label;

    GenderType (int code, String label) {
        this.code = code;
        this.label = label;
    }
}
