package com.zeburan.springbootsocket.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * java实现websocket的五种方式：https://www.jianshu.com/p/cfd77316546a
 * 在线websocket测试工具：http://coolaf.com/tool/chattest
 */
@Slf4j
@ServerEndpoint(value = "/wsdemo", encoders = {SocketEncoder.class})
@Component
public class MyWebSocket {
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     * 在外部可以获取此连接的所有websocket对象，并能对其触发消息发送功能，我们的定时发送核心功能的实现在与此变量
     */
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;


    /**
     * 连接建立成功调用的方法
     * <p>
     * 类似dwr的onpage方法，参考之前文章中demo有
     */
    @OnOpen
    public void onOpen(Session session) {

        try {
            log.info("----------------");
            this.session = session;
            webSocketSet.add(this);     //加入set中
            addOnlineCount();           //在线数加1
            log.info("有新连接加入！当前在线人数为" + getOnlineCount());
            String ip = Inet4Address.getLocalHost().getHostAddress();
            if (getWebSocketSet().size() > 1) {
                //拒绝连接
                sendErrorMessage("设备" + ip + "已在其他设备连接");
                log.error("已有设备建立连接，本次请求ip{},已有socket信息", ip);
                session.close();
                return;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 连接关闭调用的方法
     * <p>
     * 参考dwrsession摧毁方法
     */
    @OnClose
    public void onClose() throws UnknownHostException {
        try {
            webSocketSet.remove(this);  //连接关闭后，将此websocket从set中删除
            subOnlineCount();           //在线数减1
            log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);
    }

    // 错误提示
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    // 发送消息，在定时任务中会调用此方法
    public void sendMessage(List message) throws IOException, EncodeException {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", 200);
        resultJson.put("msg", "success");
        resultJson.put("data", message);
        this.session.getBasicRemote().sendObject(resultJson);

    }

    public void sendErrorMessage(String message) throws IOException, EncodeException {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", 200);
        resultJson.put("msg", "success");
        resultJson.put("data", message);
        this.session.getBasicRemote().sendObject(resultJson);

    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }


    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public static CopyOnWriteArraySet<MyWebSocket> getWebSocketSet() {
        return webSocketSet;
    }

    public static void setWebSocketSet(CopyOnWriteArraySet<MyWebSocket> webSocketSet) {
        MyWebSocket.webSocketSet = webSocketSet;
    }

    /**
     * 判断vnc端口是否被占用
     *
     * @param ip
     * @return
     * @throws UnknownHostException
     */
    public static boolean isVNCPortInUse(String ip) throws UnknownHostException {

        try {
            Socket socket = new Socket(ip, 5900);
            socket.close();
            return true;
        } catch (Exception e) {
            //空闲
            return false;
        }
    }
}