package com.koen.server.party.service;

import com.koen.server.party.entity.AdsImage;
import com.koen.server.party.entity.AdsPerson;
import com.koen.server.party.repository.AdsImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdsImageService implements ServiceController  {
    @Autowired
    AdsImageRepository adsImageRepository;
    @Override
    public void save(Object object) {
        AdsImage adsImage = (AdsImage) object;
        adsImageRepository.save(adsImage);
    }

    @Override
    public void remove(Object object) {
        AdsImage adsImage = (AdsImage) object;
        adsImageRepository.delete(adsImage);
    }
}
