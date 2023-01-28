package com.dev.springwebsocket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String status;
    private String sender;
    private String receiver;
    private Object data;

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void newConnection() {
        this.status = "new";
    }

    public void closeConnection() {
        this.status = "close";
    }
}
