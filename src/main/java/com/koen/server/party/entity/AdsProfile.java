package com.koen.server.party.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "adsprofile")
public class AdsProfile {
    @JsonIgnore
    @Id
    @Column(name = "adsperson_id", unique = true, nullable = true)
    private Long id;
    @MapsId
    @OneToOne
    private AdsPerson adsPerson;
    @Column(name = "description", nullable = false, length = 2000)
    private String description;
    @JsonIgnore
    public Long getId() {
        return id;
    }
    @JsonIgnore
    public AdsPerson getAdsPerson() {
        return adsPerson;
    }

    public void setAdsPerson(AdsPerson adsPerson) {
        this.adsPerson = adsPerson;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
