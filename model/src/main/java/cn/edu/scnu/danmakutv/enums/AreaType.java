package cn.edu.scnu.danmakutv.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/*
视频类型枚举类
 */
public enum AreaType {

    LIFE(1, "生活"),
    FUN(2, "娱乐"),
    ANIME(3, "动画"),
    GAME(4, "游戏");

    @EnumValue
    private final int code;

    private final String type;

    AreaType (int code, String type) {
        this.code = code;
        this.type = type;
    }
}
