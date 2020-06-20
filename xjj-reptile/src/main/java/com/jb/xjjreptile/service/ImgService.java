package com.jb.xjjreptile.service;


import com.alibaba.fastjson.JSON;
import com.jb.xjjreptile.pojo.ImgInfo;
import com.jb.xjjreptile.utlis.StringUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ImgService {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient esClient ;


    public  Boolean  addInfo(ImgInfo imgInfo) throws IOException {


        //处理Url
        List<String> urlIds = StringUtils.getUrlId(imgInfo.getUrls());
        for (String urlId : urlIds) {
            //先检测是否存在图片ID
            if (searchImgInfo(urlId)){

                //返回true就存在就跳过
                return false;
            }
        }



        boolean flag = true;
        IndexRequest indexRequest = new IndexRequest("img_info");
        Map<String,Object> map = new HashMap<>();
        map.put("theme",imgInfo.getTheme());
        map.put("nikeName",imgInfo.getNikeName());
        map.put("tags",imgInfo.getTags());
        map.put("urls", imgInfo.getUrls());

        indexRequest.source(JSON.toJSONString(map), XContentType.JSON);
        indexRequest.timeout("10s");

        try {
            IndexResponse indexResponse = esClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            flag = false;
        }

        return flag;
    }

    //检测数据库是否存在此图片url信息
    public boolean searchImgInfo(String urlId) throws IOException {
        boolean flag = false;

        //条件搜索
        SearchRequest img_info = new SearchRequest("img_info");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //精准匹配
        searchSourceBuilder.query(QueryBuilders.termQuery("urls", urlId));
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //执行搜索
        img_info.source(searchSourceBuilder);
        SearchResponse search = esClient.search(img_info, RequestOptions.DEFAULT);

        //["https://photo.tuchong.com/16242977/f/38260457.jpg"]
        if (search.getHits().getHits().length>0){
            flag = true;
        }else {
            flag = false;
        }

        return flag;
    }


    //根据标签查询数据
    public List<Map<String,Object>> searchPage(String keyword, int pageNo, int pageSize) throws IOException {
        if (pageNo <= 1) {
            pageNo = 1;
        }
        //条件搜索
        SearchRequest img_info = new SearchRequest("img_info");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //分页
        searchSourceBuilder.from(pageNo);
        searchSourceBuilder.size(pageSize);
        //精准匹配
        searchSourceBuilder.query(QueryBuilders.matchQuery("tags",keyword));
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("tags");
        highlightBuilder.requireFieldMatch(true);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);

        //执行搜索
        img_info.source(searchSourceBuilder);
        SearchResponse search = esClient.search(img_info, RequestOptions.DEFAULT);

        ArrayList<Map< String,Object>> list = new ArrayList<>();
        for (SearchHit documentFields :search.getHits().getHits()) {

            Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();

            HighlightField title = highlightFields.get("tags");
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();

            if (title!=null){
                Text[] fragments = title.fragments();
                String n_title = "";
                for (Text text : fragments){
                    n_title += text;
                }
                sourceAsMap.put("tags",n_title);
            }

            list.add(documentFields.getSourceAsMap());
        }
        return list;
    }





}
