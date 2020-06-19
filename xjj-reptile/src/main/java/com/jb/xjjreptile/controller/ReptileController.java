package com.jb.xjjreptile.controller;

import com.jb.xjjreptile.reptile.GetPageProcessor;
import com.jb.xjjreptile.reptile.UrlProcessor;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@ResponseBody
@Controller
public class ReptileController {


    private Map<String,Integer> keywords = new HashMap<>();

    @Autowired
    GetPageProcessor getPageProcessor;

    //请求控制爬虫开始

    @RequestMapping("/open/{keyword}")
    public String startReptile(@PathVariable("keyword") String keyword, HttpServletResponse response) throws IOException {
        Integer integer = keywords.get(keyword);
        if (integer == null || integer == 0){
            keywords.put(keyword,1);
        }else {
            return keyword+"正在抓取";
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Spider.create(getPageProcessor).addUrl("https://tuchong.com/tags/"+keyword).thread(1).run();
                keywords.put(keyword,0);
            }
        };
        new Thread(runnable).start();
//        response.getWriter().write();
        return "关键字'"+keyword+"'开始爬取";
    }
}
