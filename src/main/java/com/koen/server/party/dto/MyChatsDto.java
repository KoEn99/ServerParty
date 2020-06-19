package com.koen.server.party.dto;

public class MyChatsDto {
    private String fio;
    private String lastMessage;
    private String email_to;
    private String time;
    private String did;
    private boolean readMessage;
    private String lastEmail;
    private String avatar;

    public MyChatsDto(String fio,String lastMessage, String time, boolean readMessage,
                      String did,
                      String lastEmail, String email_to, String avatar) {
        this.fio = fio;
        this.lastMessage = lastMessage;
        this.time = time;
        this.readMessage = readMessage;
        this.did = did;
        this.lastEmail = lastEmail;
        this.email_to = email_to;
        this.avatar = avatar;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isReadMessage() {
        return readMessage;
    }

    public void setReadMessage(boolean readMessage) {
        this.readMessage = readMessage;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getLastEmail() {
        return lastEmail;
    }

    public void setLastEmail(String lastEmail) {
        this.lastEmail = lastEmail;
    }

    public String getEmail_to() {
        return email_to;
    }

    public void setEmail_to(String email_to) {
        this.email_to = email_to;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
