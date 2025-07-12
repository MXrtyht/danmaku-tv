package cn.edu.scnu.danmakutv.common.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {
    @Bean
    public Docket adminApiConfig () {
        String token = "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWKi5NUrJSSknMy03MLtUNDXYNUtJRSq0oULIyNDc1NjUxNDO11FEqLU4t8kxRsjKqBQA7ZJ38MgAAAA.Hx_lbnuRh9LIr0G8PuISLVhh_-rpSeaVRkdcWEfqCrJ6X-U2uOYPMsll_tE70M-qrzrxqY0iDCis-LGYw1cvIA";

        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("token")
                .description("用户登录令牌")
                .defaultValue(token)
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .build();

        List<Parameter> pars = new ArrayList<>();
        pars.add(tokenPar.build());

        Docket adminApi = new Docket(DocumentationType.SWAGGER_2)
                .groupName("Api")
                .apiInfo(adminApiInfo())
                .select()
                // 只显示admin路径下的页面
                .apis(RequestHandlerSelectors.basePackage("cn.edu.scnu.danmakutv"))
                //.paths(PathSelectors.regex("/admin/.*"))
                .build()
                .globalOperationParameters(pars);
        return adminApi;
    }

    private ApiInfo adminApiInfo () {
        return new ApiInfoBuilder()
                .title("DanmakuTV-API文档")
                .description("本文档描述了DanmakuTV服务接口定义")
                .version("1.0")
                .contact(new Contact("scnu", "http://scnu.edu.cn", "scnu"))
                .build();
    }
}
