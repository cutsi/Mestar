package com.example.demo.Repos;

import com.example.demo.Models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Override
    Optional<Rating> findById(Long ratingId);
    Optional<Rating> findByRating(int rating);
    @Transactional
    @Modifying
    @Query("UPDATE Rating r " +
            "SET r.ratingEnabled = TRUE WHERE r.id = ?1")
    int enableAppUser(Long id);
}