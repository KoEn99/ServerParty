package com.koen.server.party.dto;

public class MessageChatDto {
    private String myEmail;
    private String authPerson_to;
    private String did;
    private String message;
    private String time;
    private String imageMessage;

    public String getAuthPerson_to() {
        return authPerson_to;
    }

    public void setAuthPerson_to(String authPerson_to) {
        this.authPerson_to = authPerson_to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMyEmail() {
        return myEmail;
    }

    public void setMyEmail(String myEmail) {
        this.myEmail = myEmail;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getImageMessage() {
        return imageMessage;
    }

    public void setImageMessage(String imageMessage) {
        this.imageMessage = imageMessage;
    }
}
