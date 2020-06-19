package com.koen.server.party.dto;

public class AdsReviewsDto {
    private String content;
    private Long adsid;
    private String rating;
    private String date;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAdsid() {
        return adsid;
    }

    public void setAdsid(Long adsid) {
        this.adsid = adsid;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
