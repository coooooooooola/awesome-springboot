package com.zeburan.springbootsocket.config;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@EnableScheduling
@Slf4j
public class TimeTask {
    @Scheduled(cron = "0/1 * * * * ?")   //每分钟执行一次
    public void test() {
        CopyOnWriteArraySet<MyWebSocket> webSocketSet =
                MyWebSocket.getWebSocketSet();
        webSocketSet.forEach(c -> {
            try {
                ArrayList list = new ArrayList();
                list.add("aaa");
                c.sendMessage(list);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                log.error(e.getMessage());
            }
        });
    }
}

