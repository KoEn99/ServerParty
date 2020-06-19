package com.koen.server.party.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "roomchat")
public class RoomChat {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "authperson_id", nullable = false)
    private AuthPerson emailFrom;
    @ManyToOne
    @JoinColumn(name = "dialog_did", nullable = false)
    private Dialog dialog;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthPerson getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(AuthPerson emailFrom) {
        this.emailFrom = emailFrom;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }
}
