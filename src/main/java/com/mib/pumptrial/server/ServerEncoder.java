package com.mib.pumptrial.server;

import cn.hutool.json.JSONUtil;
import com.mib.pumptrial.common.api.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;


/**
 * @Description WebSocket编码器
 * @Author ming
 * @Date 2022/7/12 14:01
 **/
public class ServerEncoder implements Encoder.Text<CommonResult<Object>> {
    private static final Logger log = LoggerFactory.getLogger(ServerEncoder.class);

    @Override
    public String encode(CommonResult result) {
        try {
            return JSONUtil.toJsonStr(result);
        } catch (Exception e) {
            log.error("encode error {}", e.getMessage());
        }
        return null;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        //可忽略
    }

    @Override
    public void destroy() {
        //可忽略
    }
}

