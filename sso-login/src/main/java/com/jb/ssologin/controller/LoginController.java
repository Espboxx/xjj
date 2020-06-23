package com.jb.ssologin.controller;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.jb.ssologin.service.DbUserService;
import com.jb.ssologin.utils.RedisUtil;
import com.jb.ssologin.vo.DbUser;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

        Map<String,Object> map = new HashMap<>();
        //标识符 成功返回1 失败返回0
        int code = 0;
        String user = null ;
        //判断用户提交是否携带UUID
        if (uuid != null){//如果携带就从redis里取出
            user = redisUtil.get(uuid+":"+username);
        }else {
            //没有的话就查找账号密码
            user = JSON.toJSONString(dbUserService.findUser(new DbUser(username, password)));
            if (user!= null){
                code = 1;
                uuid = UUID.randomUUID().toString();
                redisUtil.set(uuid+":"+username, user );
                redisUtil.expire(uuid,1000, TimeUnit.SECONDS);
            }
        }


        map.put("user",user);
        map.put("uuid",uuid);
        map.put("code",code);
        return map;
    }


}
