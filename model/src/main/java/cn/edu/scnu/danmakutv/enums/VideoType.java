package cn.edu.scnu.danmakutv.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/*
视频类型枚举类
 */
public enum VideoType {
    ORIGINAL(0, "自制"),
    REPOSTED(1, "转载");

    @EnumValue
    private final int code;

    private final String type;

    VideoType (int code, String type) {
        this.code = code;
        this.type = type;
    }
}
