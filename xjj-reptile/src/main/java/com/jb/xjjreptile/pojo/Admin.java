package com.jb.xjjreptile.pojo;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Admin{
    private String username;//管理员账号
    private String password;//管理员密码
    private Integer live;//管理员等级
}
