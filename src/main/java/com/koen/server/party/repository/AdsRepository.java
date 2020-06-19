package com.koen.server.party.repository;

import com.koen.server.party.entity.AdsPerson;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdsRepository extends JpaRepository<AdsPerson, Long> {
        List<AdsPerson> findByCategory(String category,Sort sort);
        List<AdsPerson> findByTitleContainingIgnoreCase(String title, Sort sort);
        List<AdsPerson> findByTitleContainingIgnoreCaseAndCity(String title, String city, Sort sort);
        List<AdsPerson> findAllByCategoryAndCity(String category, String city, Sort sort);
        List<AdsPerson> findByAuthPerson_Email(String email);
}
