package com.example.demo.Services;

import com.example.demo.Models.Category;
import com.example.demo.Models.Ad;
import com.example.demo.Repos.AdRepository;
import com.example.demo.Models.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Getter
@Setter
public class AdService {
    private AdRepository adRepository;

    public Optional<Ad> getAdById(Long id){
        return adRepository.findById(id);
    }
    public void AdSave(Ad ad){
        adRepository.save(ad);
    }
    public Optional<Ad> getAdByTitle(String title){
        return adRepository.findByTitle(title);
    }

    public void saveAd(Ad ad) {
        adRepository.save(ad);
    }
    public List<Ad> getAdByUser(AppUser user){
        return adRepository.findByUser(user);
    }
    public List<Ad> getAdByCategory(Category category){
        return adRepository.findAllByCategory(category);
    }
    public List<Ad> getAllAds(){
        return adRepository.findAll();
    }

    public List<Ad> getAllByFinishedTrueAndUser(AppUser user){
        return adRepository.findAllByIsFinishedTrueAndUser(user);
    }
    public List<Ad> getAllByFinishedFalseAndUser(AppUser user){
        return adRepository.findAllByIsFinishedFalseAndUser(user);
    }
    public List<Ad> getAllByPickedTrueAndUser(AppUser user){
        return adRepository.findAllByIsPickedTrueAndUser(user);
    }
    public List<Ad> getAllByPickedFalseAndUser(AppUser user) {
        return adRepository.findAllByIsPickedFalseAndUser(user);
    }
    public List<Ad> getAllByUser(AppUser user) {
        return adRepository.findByUser(user);
    }
    public void deleteAd(Long id){
        adRepository.deleteById(id);
    }
}
