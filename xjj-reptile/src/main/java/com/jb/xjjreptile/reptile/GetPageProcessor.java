package com.jb.xjjreptile.reptile;

import com.jb.xjjreptile.sendMsg.serverFangTang;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

@Component
public class GetPageProcessor implements PageProcessor {



    @Autowired
    UrlProcessor urlProcessor;

    private static Site site = new Site()
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36 Edg/83.0.478.50")
            .setSleepTime(1000)
            .setCycleRetryTimes(5)
            .setCharset("utf-8")
            .setTimeOut(10000);

    @Override
    public void process(Page page) {

        String url = page.getUrl().toString();
        String[] split = url.split("/");
        String keyword = split[split.length-1];


        Html html = page.getHtml();//获取图片总数
        String count = html.xpath("/html/body/main/div[1]/span/text()").toString().replaceAll("组作品","");
        int i = Integer.parseInt(count);//转为Int
        int pageCount = 0;
        if ( i % 20 != 0){
            pageCount = i / 20 + 1;
        }else {
            pageCount = i / 20;
        }

        String[] urls = new String[pageCount];
        serverFangTang.sendWX("主人你有新的消息","主人：你的“"+keyword+"”开始抓取了,一共检测到有"+count+"条数据,预计"+pageCount+"页");

        for (int pageI = 0; pageI < urls.length; pageI++) {//循环当前页数
            urls[pageI] = "https://tuchong.com/rest/tags/"+keyword+"/posts?page="+((int)(pageI+1))+"&count=20&order=weekly";
        }

        //启动线程爬取页数
        Spider.create(urlProcessor).addUrl(urls).thread(1).run();
    }
    @Override
    public Site getSite() {
        return site;
    }
    public static void main(String[] args) {
        PropertyConfigurator.configure("xjj-reptile/src/log4j.properties");

    }

}
