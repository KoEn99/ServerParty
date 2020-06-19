package com.koen.server.party.dto;

public class AdsReviewRequest {
    private String content;
    private Long adsid;
    private String author;
    private String rating;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
