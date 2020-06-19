package com.koen.server.party.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "adsreviews")
public class AdsReviews {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content", nullable = false, length = 1000)
    private String content;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "adsPerson_id", nullable = false)
    private AdsPerson adsPerson;
    @ManyToOne
    @JoinColumn(name = "authperson_id", nullable = false)
    private AuthPerson authPerson;
    @Column(name = "rating", nullable = false, length = 1)
    private String rating;
    @Column(name = "date", nullable = false, length = 25)
    private String date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public AuthPerson getAuthPerson() {
        return authPerson;
    }

    public void setAuthPerson(AuthPerson authPerson) {
        this.authPerson = authPerson;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public AdsPerson getAdsPerson() {
        return adsPerson;
    }

    public void setAdsPerson(AdsPerson adsPerson) {
        this.adsPerson = adsPerson;
    }
}
