package cn.edu.scnu.danmakutv.common.swagger;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private String title = "DanmakuTV";
    private String description = "DanmakuTV 相关接口文档";
    private String version = "v0.0.1";
    private String websiteName = "Test";
    private String websiteUrl = "http://edu.scnu.cn";
    private String token = "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWKi5NUrJSSknMy03MLtUNDXYNUtJRSq0oULIyNDc1tjC1tLQ01VEqLU4t8kxRsjKqBQCXFEQfMgAAAA.4kfsxzRQJUPq-M2Arm6LZCXDWesVf7zi9BZcMEDDJQ8V3fb7xFUoSYqJsiUq7HAlIPl49qrJt4sQuSX-M8DRUg";

    @Bean
    public OpenAPI openAPI () {
        return new OpenAPI()
                .info(new Info().title(title)
                                .description(description)
                                .version(version))
                .externalDocs(new ExternalDocumentation().description(websiteName)
                                                         .url(websiteUrl));
    }

    @Bean
    public OperationCustomizer addGlobalTokenHeader () {
        return (operation, handlerMethod) -> {
            operation.addParametersItem(
                    new Parameter()
                            .in(ParameterIn.HEADER.toString())
                            .name("token")
                            .required(true)
                            .description("固定 Token")
                            .schema(new StringSchema()._default(token))
            );
            return operation;
        };
    }
}
