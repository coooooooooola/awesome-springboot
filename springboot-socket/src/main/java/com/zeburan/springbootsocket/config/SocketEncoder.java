package com.zeburan.springbootsocket.config;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class SocketEncoder  implements Encoder.Text<JSONObject> {
    @Override
    public String encode(JSONObject baseResponseMessage) throws EncodeException {
        try {
            JsonMapper jsonMapper = new JsonMapper();
            return jsonMapper.writeValueAsString(baseResponseMessage);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}