package cn.edu.scnu.danmakutv.video.config;

//import org.elasticsearch.client.RestHighLevelClient;
import cn.edu.scnu.danmakutv.video.service.ElasticSearchService;
import cn.edu.scnu.danmakutv.video.service.impl.ElasticSearchServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.client.erhlc.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.client.erhlc.RestClients;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
@EnableElasticsearchRepositories(basePackages = "cn.edu.scnu.danmakutv.video.repository")
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris}")
    private String esUrl;

//    @Override
//    @Bean
//    public RestHighLevelClient elasticsearchClient()  {
//        final ClientConfiguration clientConfiguration=ClientConfiguration.builder()
//                .connectedTo(esUrl).build();
//        return RestClients.create(clientConfiguration).rest();
//    }

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(esUrl) // ES 服务器地址
                // 可选：添加认证（如 ES 8.x 默认启用安全配置）
                //.withBasicAuth("elastic", "your-password") // 用户名和密码
                // 可选：启用 SSL（如果 ES 配置了 HTTPS）
                // .usingSsl()
                // 可选：配置超时时间
                // .withConnectTimeout(Duration.ofSeconds(10))
                // .withSocketTimeout(Duration.ofSeconds(5))
                .build();
    }



}
