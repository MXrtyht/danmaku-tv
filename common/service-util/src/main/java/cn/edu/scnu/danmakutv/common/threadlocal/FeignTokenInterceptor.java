package cn.edu.scnu.danmakutv.common.threadlocal;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignTokenInterceptor implements RequestInterceptor {

    @Override
    public void apply (RequestTemplate template) {
        // 获取当前请求的 RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }

        // 从请求中获取 token
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String token = request.getHeader("token");

        // 添加 token 到 Feign 请求中
        if (token != null) {
            template.header("token", token);
        }
    }
}
