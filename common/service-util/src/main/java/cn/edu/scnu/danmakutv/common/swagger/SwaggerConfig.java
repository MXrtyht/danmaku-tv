package cn.edu.scnu.danmakutv.common.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private String title = "DanmakuTV";
    private String description = "DanmakuTV 相关接口文档";
    private String version = "v0.0.1";
    private String websiteName = "Test";
    private String websiteUrl = "http://edu.scnu.cn";

    @Bean
    public OpenAPI heroOpenAPI () {
        return new OpenAPI()
                .info(new Info().title(title)
                                .description(description)
                                .version(version))
                .externalDocs(new ExternalDocumentation().description(websiteName)
                                                         .url(websiteUrl));
    }
}
