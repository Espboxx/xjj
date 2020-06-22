package com.jb.ssologin.controller;

import com.jb.ssologin.vo.DbUser;
import com.jb.ssologin.service.DbUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (DbUser)表控制层
 *
 * @author makejava
 * @since 2020-06-22 20:48:28
 */
@RestController
@RequestMapping("dbUser")
public class DbUserController {
    /**
     * 服务对象
     */
    @Resource
    private DbUserService dbUserService;
    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public DbUser selectOne(Integer id) {
        return this.dbUserService.queryById(id);
    }

}