package com.koen.server.party.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

@Entity
@Table(name = "messagechat")
public class MessageChat {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "authperson_id", nullable = false)
    private AuthPerson authPerson_from;
    @Column(name = "message", nullable = false)
    private String Message;
    @Column(name = "image", nullable = true)
    private String imageMessage;
    @Column(name = "time", nullable = false)
    private String time;
    @Column(name = "did", nullable = false)
    private String did;
    @Column(name = "readMessage", nullable = false)
    private boolean readMessage = false;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    @Transient
    private String fio = "";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public boolean isReadMessage() {
        return readMessage;
    }

    public void setReadMessage(boolean readMessage) {
        this.readMessage = readMessage;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public AuthPerson getAuthPerson_from() {
        return authPerson_from;
    }

    public void setAuthPerson_from(AuthPerson authPerson_from) {
        this.authPerson_from = authPerson_from;
    }

    public String getImageMessage() {
        return imageMessage;
    }

    public void setImageMessage(String imageMessage) {
        this.imageMessage = imageMessage;
    }
}
