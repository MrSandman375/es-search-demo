//package com.renjie.essearchdemo.config;
//
//import org.apache.http.HttpHost;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
//import org.springframework.util.StringUtils;
//
///**
// * @Auther: fan
// * @Date: 2020/12/9
// * @Description: elasticsearch-High-level-client配置类
// */
//@Configuration
//public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
//
//    @Value("${spring.elasticsearch.rest.username}")
//    private String USERNAME;
//    @Value("${spring.elasticsearch.rest.password}")
//    private String PASSWORD;
//    @Value("${spring.elasticsearch.rest.uris}")
//    private String URLS;// 都好分割
//
//
//    @Bean
//    @Override
//    public RestHighLevelClient elasticsearchClient() {
//        if (StringUtils.isEmpty(URLS)) {
//            throw new RuntimeException("配置有问题，elasticsearch.urls为空");
//        }
//        String[] urls = URLS.split(",");
//        HttpHost[] httpHostArr = new HttpHost[urls.length];
//        for (int i=0; i<urls.length; i++) {
//            String urlStr = urls[i];
//            if(StringUtils.isEmpty(urlStr)) {
//                continue;
//            }
//            httpHostArr[i] = HttpHost.create(urlStr);
//        }
//        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USERNAME, PASSWORD));  //es账号密码（默认用户名为elastic）
//        RestHighLevelClient client = new RestHighLevelClient(
//                RestClient.builder(httpHostArr)
//                        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//                            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
//                                httpClientBuilder.disableAuthCaching();
//                                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
//                            }
//                        }));
//        return client;
//    }
//
//
//
//}
