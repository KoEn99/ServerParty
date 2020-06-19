package com.koen.server.party.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ads")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdsPerson {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false, length = 30)
    private String title;
    @Column(name = "rating", nullable = true, length = 30)
    private String rating;
    @Column(name = "price", nullable = false)
    private int price;
    @Column(name = "city", nullable = false, length = 30)
    private String city;
    @Column(name = "category", nullable = false, length = 30)
    private String category;
    @OneToOne(mappedBy = "adsPerson", cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private AdsProfile adsProfile;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "authperson_id", nullable = false)
    private AuthPerson authPerson;
    @OneToMany(mappedBy = "adsPerson", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<AdsImage> attachments = new ArrayList<>();
    @OneToMany(mappedBy = "adsPerson", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<AdsReviews> adsReviewSet = new ArrayList<>();
    @OneToMany(mappedBy = "adsPerson", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<AdsFavorites> attachments1 = new HashSet<AdsFavorites>();
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    @Transient
    private Long myFavorite = Long.parseLong("0");
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }



    public AuthPerson getAuthPerson() {
        return authPerson;
    }

    public void setAuthPerson(AuthPerson authPerson) {
        this.authPerson = authPerson;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public AdsProfile getAdsProfile() {
        return adsProfile;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAdsProfile(AdsProfile adsProfile) {
        this.adsProfile = adsProfile;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<AdsImage> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AdsImage> attachments) {
        this.attachments = attachments;
    }

    public Long isMyFavorite() {
        return myFavorite;
    }

    public void setMyFavorite(Long myFavorite) {
        this.myFavorite = myFavorite;
    }
}
