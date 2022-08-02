package com.mib.pumptrial.server;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mib.pumptrial.common.api.CommonResult;
import com.mib.pumptrial.enums.OpEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket的处理类。作用相当于 HTTP请求中的 controller
 *
 * @author rb.ming
 */
@Component
@ServerEndpoint(value = "/ws/pump-trial/{userId}", encoders = {ServerEncoder.class})
public class WebSocketServer {

    private final static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收userId
     */
    private String userId = "";

    /**
     * 发送自定
     * 义消息
     **/
    public static void sendInfo(String userId, String message) {
        log.info("发送消息到:" + userId + "，报文:" + message);
        if (StrUtil.isNotBlank(userId) && webSocketMap.containsKey(userId)) {
            webSocketMap.get(userId).sendMessage(message);
        } else {
            log.error("用户" + userId + ",不在线！");
        }
    }

    /**
     * 获得此时的在线人数
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 在线人数加1
     */
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    /**
     * 在线人数减1
     */
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    /**
     * 连接建立成
     * 功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            //加入set中
            webSocketMap.put(userId, this);
        } else {
            //加入set中
            webSocketMap.put(userId, this);
            //在线数加1
            addOnlineCount();
        }
        log.info("用户连接:" + userId + ",当前在线人数为:" + getOnlineCount());
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("opcode", 10000);
        jsonObject.set("data", "连接成功");
        this.send(this.userId, jsonObject);
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出:" + userId + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     **/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户消息:" + userId + ",报文:" + message);
        if (StrUtil.isNotBlank(message)) {
            try {
                JSONObject data = JSONUtil.parseObj(message);
                int opcode = data.getInt("opcode");
                String toId = data.getStr("toId");
                //测试链接
                if (OpEnum.TEST.getCode().equals(opcode)) {
                    this.send(this.userId, data);
                }
                //获取在线设备
                if (OpEnum.LIST.getCode().equals(opcode)) {
                    List<String> list = new ArrayList<>(webSocketMap.keySet());
                    list.removeIf(i -> i.equals(this.userId));
                    data.set("deviceList", list);
                    this.send(this.userId, data);
                }
                data.set("fromId", this.userId);
                //输注大剂量
                if (OpEnum.INFUSION.getCode().equals(opcode)) {
                    this.send(toId, data);
                }
                //输注基础率
                if (OpEnum.BASE.getCode().equals(opcode)) {
                    this.send(toId, data);
                }
                //临时基础率
                if (OpEnum.TEMP_BASE.getCode().equals(opcode)) {
                    this.send(toId, data);
                }
                //临时基础率
                if (OpEnum.LARGE.getCode().equals(opcode)) {
                    this.send(toId, data);
                }
                //胰岛素泵回显
                if (OpEnum.ECHO.getCode().equals(opcode)) {
                    this.send(toId, data);
                }
                //停止临时基础率
                if (OpEnum.STOP_TEMP_BASE.getCode().equals(opcode)) {
                    this.send(toId, data);
                }
                //停止大剂量
                if (OpEnum.STOP_LARGE.getCode().equals(opcode)) {
                    this.send(toId, data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void send(String toId, JSONObject data) {
        if (StrUtil.isNotBlank(toId) && webSocketMap.containsKey(toId)) {
            webSocketMap.get(toId).sendMessage(JSONUtil.toJsonStr(data));
        } else {
            //否则不在这个服务器上，发送到mysql或者redis
            webSocketMap.get(this.userId).sendMessage(toId + "：不在线");
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务
     * 器主动推送
     */
    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendObject(CommonResult.success(message));
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

}

