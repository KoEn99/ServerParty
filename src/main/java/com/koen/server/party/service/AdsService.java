package com.koen.server.party.service;

import com.koen.server.party.entity.AdsPerson;
import com.koen.server.party.entity.AdsProfile;
import com.koen.server.party.repository.AdsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdsService implements ServiceController{
    @Autowired
    AdsRepository adsRepository;

    @Override
    public void save(Object object) {
        AdsPerson adsPerson = (AdsPerson)object;
        adsRepository.save(adsPerson);
    }

    @Override
    public void remove(Object object) {
        AdsPerson adsPerson = (AdsPerson)object;
        adsRepository.delete(adsPerson);
    }
}
