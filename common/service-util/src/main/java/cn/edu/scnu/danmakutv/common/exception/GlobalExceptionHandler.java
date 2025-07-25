package cn.edu.scnu.danmakutv.common.exception;

import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 * 配合DanmakuException使用
 */
@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {
    @ExceptionHandler(Exception.class) // 异常处理器
    @ResponseBody  // 返回json数据
    public CommonResponse error (Exception e) {
        e.printStackTrace();
        return CommonResponse.fail(e.getMessage());
    }

    // 自定义异常处理
    @ExceptionHandler(DanmakuException.class)
    @ResponseBody
    public CommonResponse error (DanmakuException exception) {
        return CommonResponse.build(null, exception.getCode(), exception.getMessage());
    }
}
