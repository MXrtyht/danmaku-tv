package cn.edu.scnu.danmaku.common.exception;

import cn.edu.scnu.danmaku.common.response.CommonResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {
    @ExceptionHandler(Exception.class) // 异常处理器
    @ResponseBody  // 返回json数据
    public CommonResponse error (Exception e) {
        e.printStackTrace();
        return CommonResponse.fail(null);
    }

    // 自定义异常处理
    @ExceptionHandler(DanmakuException.class)
    @ResponseBody
    public CommonResponse error (DanmakuException exception) {
        return CommonResponse.build(null, exception.getCode(), exception.getMessage());
    }
}
