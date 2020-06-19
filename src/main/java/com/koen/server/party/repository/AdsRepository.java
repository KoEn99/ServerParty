package com.koen.server.party.repository;

import com.koen.server.party.entity.AdsPerson;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdsRepository extends JpaRepository<AdsPerson, Long> {
   /*     List<AdsPerson> findByTitleContainingIgnoreCaseAndCity(String Title, String city, Sort sort);
        List<AdsPerson> findByTitleContainingIgnoreCaseAndPriceBetweenAndCity(String title,int price_one, int price_two, String city, Sort sort);
        List<AdsPerson> findByTitleContainingIgnoreCaseAndPriceBeforeAndCity(String title,int price_two, String City,Sort sort);
        List<AdsPerson> findByTitleContainingIgnoreCaseAndPriceAfterAndCity(String title,int price_one, String city,Sort sort);

        List<AdsPerson> findByTitleContainingIgnoreCaseAndCityAndCategory(String Title, String city, String category,Sort sort);
        List<AdsPerson> findByTitleContainingIgnoreCaseAndPriceBetweenAndCityAndCategory(String title,int price_one, int price_two, String city, String category,Sort sort);
        List<AdsPerson> findByTitleContainingIgnoreCaseAndPriceBeforeAndCityAndCategory(String title,int price_two, String City,String category,Sort sort);
        List<AdsPerson> findByTitleContainingIgnoreCaseAndPriceAfterAndCityAndCategory(String title,int price_one, String city,String category,Sort sort);
        List<AdsPerson> findByCategory(String category,Sort sort);


    */
        List<AdsPerson> findByCategory(String category,Sort sort);
        List<AdsPerson> findByTitleContainingIgnoreCase(String title, Sort sort);
        List<AdsPerson> findByTitleContainingIgnoreCaseAndCity(String title, String city, Sort sort);
        List<AdsPerson> findAllByCategoryAndCity(String category, String city, Sort sort);
        List<AdsPerson> findByAuthPerson_Email(String email);
}
