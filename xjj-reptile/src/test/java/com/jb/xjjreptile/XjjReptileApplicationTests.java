package com.jb.xjjreptile;

import com.jb.xjjreptile.reptile.GetPageProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import us.codecraft.webmagic.Spider;

@SpringBootTest
class XjjReptileApplicationTests {


    @Autowired
    GetPageProcessor getPageProcessor;

    @Test
    void contextLoads() {
//        Spider.create(getPageProcessor).addUrl("https://tuchong.com/tags/"+"可爱").thread(1).run();
    }

}
