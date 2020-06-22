package com.jb.xjjreptile.config;

import com.alibaba.fastjson.JSON;
import com.jb.xjjreptile.pojo.ImgInfo;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.stereotype.Component;

import java.util.*;

//ES配置类

@Configuration
public class ElasticSearchClient {

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient esClient = null;
        //初始化ES操作客户端
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "E3EI1A6Tqx20mqVuD0az8cGd"));  //es账号密码
        esClient = new RestHighLevelClient(
                RestClient.builder(
//                        new HttpHost("319ca147271747d7856843c9d5ea661f.asia-northeast1.gcp.cloud.es.io",9243,"https")
                        new HttpHost("47.103.25.206", 9200, "http")
                ).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        httpClientBuilder.disableAuthCaching();
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                })/*.setMaxRetryTimeoutMillis(2000)*/
        );
        return esClient;
    }


}