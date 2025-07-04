package cn.edu.scnu.danmaku.common.response;

import lombok.Data;

@Data
public class CommonResponse<T> {
    // 状态码
    private Integer code;
    // 信息
    private String message;
    // 数据
    private T data;

    // 构造私有化，使该类不能被new
    // 类中创建了static方法，使用时用这些static方法。这样做更加规范
    private CommonResponse () {
    }

    // 设置数据,返回对象的方法
    public static <T> CommonResponse<T> build (T data, Integer code, String message) {
        // 创建commonResponse对象，设置值，返回对象
        CommonResponse<T> commonResponse = new CommonResponse<>();
        // 判断返回结果中是否需要数据
        if (data != null) {
            // 设置数据到result对象
            commonResponse.setData(data);
        }
        // 设置其他值
        commonResponse.setCode(code);
        commonResponse.setMessage(message);
        // 返回设置值之后的对象
        return commonResponse;
    }


    // 设置数据,返回对象的方法
    public static <T> CommonResponse<T> build (T data, StatusCodeEnum statusCodeENum) {
        // 创建CommonResponse对象，设置值，返回对象
        CommonResponse<T> commonResponse = new CommonResponse<>();
        // 判断返回结果中是否需要数据
        if (data != null) {
            // 设置数据到result对象
            commonResponse.setData(data);
        }
        // 设置其他值
        commonResponse.setCode(statusCodeENum.getCode());
        commonResponse.setMessage(statusCodeENum.getMessage());
        // 返回设置值之后的对象
        return commonResponse;
    }

    // 成功的方法
    public static <T> CommonResponse<T> success (T data) {
        CommonResponse<T> commonResponse = build(data, StatusCodeEnum.SUCCESS);
        return commonResponse;
    }

    // 失败的方法
    public static <T> CommonResponse<T> fail (T data) {
        return build(data, StatusCodeEnum.FAIL);
    }
}
