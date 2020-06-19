package com.koen.server.party.dto;

import com.koen.server.party.entity.AdsReviews;

import java.util.ArrayList;
import java.util.List;

public class AdsReviewDto {
    List<AdsReviews> adsReviewList = new ArrayList<>();

    public List<AdsReviews> getAdsReviewList() {
        return adsReviewList;
    }

    public void setAdsReviewList(List<AdsReviews> adsReviewList) {
        this.adsReviewList = adsReviewList;
    }
}