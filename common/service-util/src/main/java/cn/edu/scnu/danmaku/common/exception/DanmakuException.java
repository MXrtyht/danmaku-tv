package cn.edu.scnu.danmaku.common.exception;

import cn.edu.scnu.danmaku.common.response.StatusCodeEnum;
import lombok.Data;

@Data
public class DanmakuException extends RuntimeException {
    // 异常状态码
    private Integer code;

    /**
     * 通过状态码和错误消息创建异常对象
     *
     * @param message 错误消息
     * @param code    异常状态码
     */
    public DanmakuException (String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型对象
     *
     * @param statusCodeEnum 状态码枚举
     */
    public DanmakuException (StatusCodeEnum statusCodeEnum) {
        super(statusCodeEnum.getMessage());
        this.code = statusCodeEnum.getCode();
    }
}
