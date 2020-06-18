package com.jb.xjjreptile.reptile;

import org.apache.log4j.PropertyConfigurator;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class getPageProcessor implements PageProcessor {

    private static String keyword = "小清新";
    private static Site site = new Site()
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36 Edg/83.0.478.50")
            .setSleepTime(500)
            .setCharset("utf-8")
            .setTimeOut(10000);

    @Override
    public void process(Page page) {
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

        for (int pageI = 0; pageI < urls.length; pageI++) {//循环当前页数
            urls[pageI] = "https://tuchong.com/rest/tags/"+keyword+"/posts?page="+((int)(pageI+1))+"&count=20&order=weekly";
            System.out.println(urls[pageI]);
        }

        Spider.create(new urlProcessor()).addUrl(urls).thread(1).run();
    }
    @Override
    public Site getSite() {
        return site;
    }
    public static void main(String[] args) {
        PropertyConfigurator.configure("D:\\代码\\xjj-master\\xjj-reptile\\src\\main\\resources\\log4j.properties");

        Spider.create(new getPageProcessor()).addUrl("https://tuchong.com/tags/"+keyword).thread(1).run();
    }

}
