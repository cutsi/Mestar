package com.example.demo.Repos;

import com.example.demo.Models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findById(Long id);
    Optional<City> findByCityName(String cityName);
    @Query("select c.cityName from City c")
    List<String> getAllCityName();
}
