package com.jb.xjjreptile;

import com.jb.xjjreptile.pojo.Admin;
import com.jb.xjjreptile.reptile.GetPageProcessor;
import com.jb.xjjreptile.utlis.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import us.codecraft.webmagic.Spider;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class XjjReptileApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    GetPageProcessor getPageProcessor;

    @Test
    void contextLoads() {

        Admin admin = new Admin("root", "root", 3);
        ValueOperations<String, Admin> operations = redisTemplate.opsForValue();
        operations.set("fdd2", admin);

        Boolean fdd2 = redisTemplate.hasKey("fdd2");

        Admin fdd21 = (Admin) redisTemplate.opsForValue().get("fdd2");
        System.out.println(fdd21.toString());

//        Spider.create(getPageProcessor).addUrl("https://tuchong.com/tags/"+"可爱").thread(1).run();
    }

}
