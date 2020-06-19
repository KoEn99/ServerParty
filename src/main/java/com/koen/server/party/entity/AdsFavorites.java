package com.koen.server.party.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

@Entity
@Table(name = "favorites")
public class AdsFavorites {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "adsPerson_id", nullable = false)
    private AdsPerson adsPerson;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "authperson_id", nullable = false)
    private AuthPerson authPerson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdsPerson getAdsPerson() {
        return adsPerson;
    }

    public void setAdsPerson(AdsPerson adsPerson) {
        this.adsPerson = adsPerson;
    }

    public AuthPerson getAuthPerson() {
        return authPerson;
    }

    public void setAuthPerson(AuthPerson authPerson) {
        this.authPerson = authPerson;
    }
}
