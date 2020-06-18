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
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.stereotype.Component;

import java.util.*;


public class ElasticSearchClient {
    private static  RestHighLevelClient esClient = null;
    static {
        //初始化ES操作客户端
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "E3EI1A6Tqx20mqVuD0az8cGd"));  //es账号密码
        esClient =new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("319ca147271747d7856843c9d5ea661f.asia-northeast1.gcp.cloud.es.io",9243,"https")
                ).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        httpClientBuilder.disableAuthCaching();
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                })/*.setMaxRetryTimeoutMillis(2000)*/
        );
    }


    public static void  addInfo(ImgInfo imgInfo) throws Exception{
        IndexRequest indexRequest = new IndexRequest("img_info");

        Map<String,Object> map = new HashMap<>();
        map.put("theme",imgInfo.getTheme());
        map.put("nikeName",imgInfo.getNikeName());
        map.put("tags",imgInfo.getTags());
        map.put("urls",JSON.toJSONString(imgInfo.getUrls()));

        indexRequest.source(JSON.toJSONString(map),XContentType.JSON);
        indexRequest.timeout("10s");
        IndexResponse indexResponse = esClient.index(indexRequest, RequestOptions.DEFAULT);
    }

}