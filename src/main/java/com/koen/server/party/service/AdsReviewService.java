package com.koen.server.party.service;

import com.koen.server.party.entity.AdsPerson;
import com.koen.server.party.entity.AdsReviews;
import com.koen.server.party.repository.AdsRepository;
import com.koen.server.party.repository.AdsReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdsReviewService implements ServiceController {
    @Autowired
    AdsReviewRepository adsReviewRepository;

    @Override
    public void save(Object object) {
        AdsReviews adsReviews = (AdsReviews) object;
        adsReviewRepository.save(adsReviews);
    }

    @Override
    public void remove(Object object) {

    }
}
