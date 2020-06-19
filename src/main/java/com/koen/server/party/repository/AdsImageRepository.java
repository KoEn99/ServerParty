package com.koen.server.party.repository;

import com.koen.server.party.entity.AdsImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdsImageRepository extends JpaRepository<AdsImage, Long> {
    AdsImage findByFilename(String filename);
    List<AdsImage> findByAdsPerson_Id(Long id);
}
