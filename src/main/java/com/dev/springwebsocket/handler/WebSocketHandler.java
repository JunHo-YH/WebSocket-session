package com.dev.springwebsocket.handler;

import com.dev.springwebsocket.model.Message;
import com.dev.springwebsocket.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>(); // 접속 정보 데이터 형식

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String sessionId = session.getId();
        sessions.put(sessionId, session);

        Message message = Message.builder()
                .sender(sessionId)
                .receiver("all")
                .build();
        message.newConnection();

        System.out.println(sessionId);
        System.out.println("연결 되었습니다.");

        sessions.values().forEach(s -> {
            try {
                if (!s.getId().equals(sessionId)) {
                    s.sendMessage(
                            new TextMessage(Utils.getString(message)));
                    System.out.println("연결 되었습니다.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {

        Message message = Utils.getObject(textMessage.getPayload());
        message.setSender(session.getId());

        WebSocketSession receiver = sessions.get(message.getReceiver());

        if (receiver != null && receiver.isOpen()) {
            receiver.sendMessage(new TextMessage(Utils.getString(message)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {

        String sessionId = session.getId();

        sessions.remove(sessionId);

        final Message message = new Message();
        message.closeConnection();
        message.setSender(sessionId);

        System.out.println(sessionId);
        System.out.println("연결 종료 되었습니다.");

        sessions.values().forEach(s -> {
            try {
                s.sendMessage(new TextMessage(Utils.getString(message)));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("error", session);
        exception.printStackTrace();
    }
}
