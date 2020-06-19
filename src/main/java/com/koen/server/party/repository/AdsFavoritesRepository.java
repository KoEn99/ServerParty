package com.koen.server.party.repository;

import com.koen.server.party.entity.AdsFavorites;
import com.koen.server.party.entity.AuthPerson;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdsFavoritesRepository extends JpaRepository<AdsFavorites, Long> {
    List<AdsFavorites> findByAuthPerson_Email(String email, Sort sort);
    AdsFavorites findByAdsPerson_IdAndAuthPerson_Email(Long id, String email);
}
