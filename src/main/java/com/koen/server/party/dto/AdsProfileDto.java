package com.koen.server.party.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.koen.server.party.entity.AdsImage;

import java.util.List;

public class AdsProfileDto {
    private long id;
    private String email;
    private String number_phone;
    private String title;
    private String description;
    private int price;
    private String city;
    private String category;
    private String rating;
    private List<AdsImage> adsImages;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Long myFavorite;

    public AdsProfileDto(long id, String email, String number_phone, String title, String description, int price, String city, String category, String rating, List<AdsImage> adsImages, Long myFavorites) {
        this.id = id;
        this.email = email;
        this.number_phone = number_phone;
        this.title = title;
        this.description = description;
        this.price = price;
        this.city = city;
        this.category = category;
        this.rating = rating;
        this.adsImages = adsImages;
        this.myFavorite = myFavorites;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber_phone() {
        return number_phone;
    }

    public void setNumber_phone(String number_phone) {
        this.number_phone = number_phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<AdsImage> getAdsImages() {
        return adsImages;
    }

    public void setAdsImages(List<AdsImage> adsImages) {
        this.adsImages = adsImages;
    }

    public Long isMyFavorites() {
        return myFavorite;
    }

    public void setMyFavorites(Long myFavorites) {
        this.myFavorite = myFavorites;
    }
}
