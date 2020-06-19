package com.jb.xjjreptile.reptile;

import com.alibaba.fastjson.JSON;
import com.jb.xjjreptile.config.ElasticSearchClient;
import com.jb.xjjreptile.pojo.ImgInfo;
import com.jb.xjjreptile.service.ImgService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ImgProcessor implements PageProcessor {


    @Autowired
    ImgService imgService;

    private static Site site = new Site()
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36 Edg/83.0.478.50")
            .setSleepTime(500)
            .setCharset("utf-8")
            .setTimeOut(1000);

    @Override
    public void process(Page page) {
        Html html = page.getHtml();

        //获取昵称
        String nikeName = html.xpath("/html/body/main/div[1]/div[1]/div/a[2]/text()").toString();
        //获取主题
        String theme = html.xpath("/html/body/main/div[1]/article/text()").toString().trim();
        //获取标签
        String tags = html.getDocument().getElementsByClass("post-tags").get(0).text();
        //获取图片url
        Element elementsByClass = html.getDocument().getElementsByClass("post-content").get(0);
        Map<String,Object> map = new HashMap<>();
        //获取ImgURL
        List<String> imgUrls = elementsByClass.getElementsByTag("img").eachAttr("src");



        if (theme.equals("")){
            theme = "无主题";
        }
        try {
            if (!imgService.addInfo(new ImgInfo(theme,nikeName,tags, JSON.toJSONString(imgUrls)))){
                     System.err.println("添加失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
