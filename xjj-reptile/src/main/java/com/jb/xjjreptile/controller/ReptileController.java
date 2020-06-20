package com.jb.xjjreptile.controller;

import com.jb.xjjreptile.reptile.GetPageProcessor;
import com.jb.xjjreptile.reptile.UrlProcessor;
import com.jb.xjjreptile.sendMsg.serverFangTang;
import com.jb.xjjreptile.utlis.RedisUtil;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@ResponseBody
@Controller
public class ReptileController {


    private Map<String,Integer> keywords = new HashMap<>();

    @Autowired
    GetPageProcessor getPageProcessor;

    @Autowired
    RedisUtil redisUtil;
    //请求控制爬虫开始
    @RequestMapping("/open/{keyword}")
    public String startReptile(@PathVariable("keyword") String keyword, HttpServletResponse response) throws IOException {

        //判断是否正在抓取
        String isKey = redisUtil.get(keyword);
        if (isKey == null || isKey.equals("0")){
            redisUtil.set(keyword,"1");
        }else {
            return keyword+"正在抓取";
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                Spider.create(getPageProcessor).addUrl("https://tuchong.com/tags/"+keyword).thread(1).run();
                redisUtil.set(keyword,"0");
                serverFangTang.sendWX("主人你有新的消息","主人：你的“"+keyword+"”抓取结束了");
            }
        };
        new Thread(runnable).start();
//        response.getWriter().write();
        return "关键字'"+keyword+"'开始爬取";
    }


    //获取全部关键字
    @RequestMapping("/getAll")
    @ResponseBody
    public Map<String, Object> getKeyWords(){
        Set<String> keys = redisUtil.keys("*");
        List<String> collect = keys.stream().collect(Collectors.toList());
        Map<String,Object> map = new HashMap<>();
        for (String s : collect) {
            String s1 = redisUtil.get(s);
            map.put(s,s1);
        }
        return map;
    }


    //设置Key值
    @ResponseBody
    @RequestMapping("/stop/{keyword}")
    public String stopKey(@PathVariable("keyword") String keyword){

        redisUtil.set(keyword,"0");
        serverFangTang.sendWX("主人你有新的消息","主人:你的"+keyword+"已经没有抓取了哦!");
        return keyword+"已经设置为0";
    }




}
