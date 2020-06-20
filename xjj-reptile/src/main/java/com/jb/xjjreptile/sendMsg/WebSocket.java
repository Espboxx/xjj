package com.jb.xjjreptile.sendMsg;

import java.io.IOException;

import java.util.Map;

import java.util.concurrent.ConcurrentHashMap;



import javax.websocket.OnClose;

import javax.websocket.OnError;

import javax.websocket.OnMessage;

import javax.websocket.OnOpen;

import javax.websocket.Session;

import javax.websocket.server.PathParam;

import javax.websocket.server.ServerEndpoint;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jb.xjjreptile.pojo.Admin;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Service;
import com.google.gson.JsonObject;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.selector.PlainText;

@Component
@ServerEndpoint("/webSocket")
public class WebSocket {

    private static int onlineCount = 0;
    private static Map<String, WebSocket> clients = new ConcurrentHashMap<String, WebSocket>();
    private Session session;


    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        onlineCount++;
        System.out.println("已连接");
    }



    @OnClose
    public void onClose() throws IOException {
        subOnlineCount();
    }



    @OnMessage

    public void onMessage(String message) throws IOException {



        String[] split = message.split("---");
        String qit;//操作标记
        qit = split[0];

        if (qit.equals("login")){
            String user;
            String pass;
            user = split[1].split(":")[1];
            pass = split[2].split(":")[1];
            System.out.println(user);
            System.out.println(pass);
            sendMessageTo("登录成功",session);
            //login---user:xxxxx---pass:xxxxx
        }




    }



    @OnError

    public void onError(Session session, Throwable error) {

        error.printStackTrace();

    }



    public void sendMessageTo(String message, Session session) throws IOException {


        session.getAsyncRemote().sendText(message);
        // session.getBasicRemote().sendText(message);

        //session.getAsyncRemote().sendText(message);

//        for (WebSocket item : clients.values()) {
//
//            if (item.username.equals(To) )
//
//                item.session.getAsyncRemote().sendText(message);
//
//        }

    }



    public void sendMessageAll(String message) throws IOException {

        for (WebSocket item : clients.values()) {

            item.session.getAsyncRemote().sendText(message);

        }

    }



    public static synchronized int getOnlineCount() {

        return onlineCount;

    }



    public static synchronized void addOnlineCount() {

        WebSocket.onlineCount++;

    }



    public static synchronized void subOnlineCount() {

        WebSocket.onlineCount--;

    }



    public static synchronized Map<String, WebSocket> getClients() {

        return clients;

    }

}