package com.example.demo.Services;

import com.example.demo.Models.City;
import com.example.demo.Repos.CityRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Getter
@Setter
public class CityService {
    private final CityRepository cityRepository;
    public void save(City city) {
        cityRepository.save(city);
    }
    public City getCityById(Long id){
        return cityRepository.getById(id);
    }
    public Optional<City> getCityByName(String name){
        return cityRepository.findByCityName(name);
    }
    public List<String> getAllCities(){
        return cityRepository.getAllCityName();
    }
}
