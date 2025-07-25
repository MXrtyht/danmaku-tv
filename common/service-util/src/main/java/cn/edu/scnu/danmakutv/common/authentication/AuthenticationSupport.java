package cn.edu.scnu.danmakutv.common.authentication;

import cn.edu.scnu.danmakutv.common.exception.DanmakuException;
import cn.edu.scnu.danmakutv.common.response.StatusCodeEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

// 通过此类可以实现用户认证相关的支持功能 暂时先这样 后面考虑用拦截器
@Component
public class AuthenticationSupport {
    public Long getCurrentUserId () {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("token");
        Long userId = JwtHelper.getUserId(token);
        if (userId < 0) {
            throw new DanmakuException(StatusCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }
        return userId;
    }

    public String getCurrentToken(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("token");
        if (token == null || token.isEmpty()) {
            throw new DanmakuException(StatusCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }
        return token;
    }
}
