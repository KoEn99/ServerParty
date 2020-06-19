package com.koen.server.party.service;

import com.koen.server.party.entity.AdsFavorites;
import com.koen.server.party.repository.AdsFavoritesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdsFavoritesService  implements  ServiceController{
    @Autowired
    AdsFavoritesRepository adsFavoritesRepository;
    @Override
    public void save(Object object) {
        AdsFavorites adsFavorites = (AdsFavorites)object;
        adsFavoritesRepository.save(adsFavorites);
    }

    @Override
    public void remove(Object object) {
        AdsFavorites adsFavorites = (AdsFavorites)object;
        adsFavoritesRepository.deleteById(adsFavorites.getId());
       //adsFavoritesRepository.delete(adsFavorites);
    }
}
