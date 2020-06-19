package com.koen.server.party.repository;

import com.koen.server.party.entity.AdsPerson;
import com.koen.server.party.entity.AdsReviews;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdsReviewRepository extends JpaRepository<AdsReviews, Long> {
    List<AdsReviews> findAllByAdsPerson_Id(long id);
}
