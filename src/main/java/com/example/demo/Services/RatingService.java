package com.example.demo.Services;

import com.example.demo.Models.Rating;
import com.example.demo.Repos.RatingRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Getter
@Setter
public class RatingService {
    private RatingRepository ratingRepository;

    public void saveRating(Rating rating){
        ratingRepository.save(rating);
    }
    public Optional<Rating> getRating(int rating){
        return ratingRepository.findByRating(rating);
    }
    public Optional<Rating> getRatingById(Long id){
        return ratingRepository.findById(id);
    }
}
