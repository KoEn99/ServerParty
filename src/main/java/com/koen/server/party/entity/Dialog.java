package com.koen.server.party.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dialog")
public class Dialog {
    @Id
    @Column(name = "did", unique = true, nullable = false)
    private String did;
    @OneToMany(mappedBy = "dialog", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<RoomChat> roomChats = new HashSet<RoomChat>();
    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public Set<RoomChat> getRoomChats() {
        return roomChats;
    }

    public void setRoomChats(Set<RoomChat> roomChats) {
        this.roomChats = roomChats;
    }
}
