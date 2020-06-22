package com.jb.xjjreptile.sendMsg;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.springframework.stereotype.Component;

import java.io.IOException;


public class serverFangTang {

    static CloseableHttpClient httpClient = HttpClients.createDefault();

    static String sckey = "SCU66323T087d0af1e6db837a43c28530aaec61095dcb038d395ee";

    public static void sendWX(String tile, String msg) {

        HttpGet httpGet = new HttpGet("https://sc.ftqq.com/" + sckey + ".send?text=" + tile + "&desp=" + msg);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    public void test() {
        sendWX("主人你有新的消息", "主人你的小姐姐搜索完毕");
    }

}
