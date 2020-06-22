package com.jb.xjjreptile.controller;


import com.alibaba.fastjson.JSON;
import com.jb.xjjreptile.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
//请求处理
@CrossOrigin
@Controller
public class PageController {


    @Autowired
    ImgService imgService;


    @ResponseBody
    @GetMapping("/search/{keyword}/{pageNo}/{pageSiz}")
    public String search(@PathVariable("keyword") String keyword,
                         @PathVariable("pageNo") int pageNo,
                         @PathVariable("pageSiz") int pageSize) throws IOException {

        System.out.println(keyword);

        List<Map<String, Object>> maps = imgService.searchPage(keyword, pageNo, pageSize);
        String s = JSON.toJSONString(maps);
        System.out.println(s);

        return s;
    }


}
