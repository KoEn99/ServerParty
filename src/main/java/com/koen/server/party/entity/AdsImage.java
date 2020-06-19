package com.koen.server.party.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "adsimage")
public class AdsImage {
    @JsonIgnore
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "adsPerson_id", nullable = false)
    private AdsPerson adsPerson;
    @Column(name = "filename", nullable = false, length = 100)
    private String filename;

    public AdsPerson getAdsPerson() {
        return adsPerson;
    }

    public void setAdsPerson(AdsPerson adsPerson) {
        this.adsPerson = adsPerson;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
