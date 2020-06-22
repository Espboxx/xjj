package com.jb.ssologin.controller;


import com.jb.ssologin.service.DbUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@Controller
public class LoginController {

    @Resource
    private DbUserService dbUserService;


    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String username,String password){





        return "1";
    }


}
