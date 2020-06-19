package com.koen.server.party.dto;

import com.koen.server.party.entity.AdsImage;
import com.koen.server.party.entity.AdsProfile;
import com.koen.server.party.entity.AuthPerson;

import java.util.List;

public class AdsPersonRequestDto {
    private Long id;
    private String title;
    private int price;
    private String city;
    private String category;
    private AdsProfile adsProfile;
    private AuthPerson authPerson;
    private List<String> adsImages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public AdsProfile getAdsProfile() {
        return adsProfile;
    }

    public void setAdsProfile(AdsProfile adsProfile) {
        this.adsProfile = adsProfile;
    }

    public AuthPerson getAuthPerson() {
        return authPerson;
    }

    public void setAuthPerson(AuthPerson authPerson) {
        this.authPerson = authPerson;
    }

    public List<String> getAdsImages() {
        return adsImages;
    }

    public void setAdsImages(List<String> adsImages) {
        this.adsImages = adsImages;
    }
}
