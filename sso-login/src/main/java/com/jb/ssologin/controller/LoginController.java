package com.jb.ssologin.controller;


import cn.hutool.db.Db;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jb.ssologin.service.DbUserService;
import com.jb.ssologin.utils.RedisUtil;
import com.jb.ssologin.vo.DbUser;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangbo
 */
@CrossOrigin
@Controller
public class LoginController {

    @Resource
    private DbUserService dbUserService;

    @Autowired
    RedisUtil redisUtil;


    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map<String, Object> login(String username, String password,String uuid){

        //初始化返回数据
       Map<String,Object> map = new HashMap<>();
       DbUser user = null;
       //返回状态 0是账号密码错误  1是登陆成功 3是UUID失效
       Integer code = 0;
        System.out.println(uuid);


       //判断请求是否携带UUID
       if (uuid != ""  || uuid.equals("")==false){
           //如果携带就去redis里面查
           JSONObject jsonObject = JSONObject.parseObject(redisUtil.get(uuid));
           //如果在redis里面没有查到那么就返回状态 3
           if (jsonObject == null){
               code = 3;
           }else {
               //如果在redis查到那么就返回状态 1
               user = JSON.toJavaObject(jsonObject, DbUser.class);
               code =1;
           }

       }else {//如果没有携带UUID那么就去数据库查
           user = dbUserService.findUser(new DbUser(username,password));

           if (user !=null){
               uuid = UUID.randomUUID().toString();
               redisUtil.set(uuid,JSON.toJSONString(user));
               code = 1;
           }else {
               code = 0;
           }
       }

        map.put("user",user);
        map.put("uuid",uuid);
        map.put("code",code);
        return map;
    }


}
