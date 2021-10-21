package com.example.demo.Services;

import com.example.demo.Models.Apply;
import com.example.demo.Repos.ApplyRepository;
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
public class ApplyService {
    private ApplyRepository applyRepository;

    public void saveApply(Apply apply){
        applyRepository.save(apply);
    }
    public Optional<Apply> getApplyById(Long id){
        return applyRepository.findById(id);
    }
    public List<Apply> getAllByAdOwnerId(Long id){
        return applyRepository.findAllByAdOwnerId(id);
    }
    public List<Apply> getAllByContractorId(Long id){
        return applyRepository.findAllByContractorId(id);
    }
    public List<Apply> getAllByOwnerIdAndContractorId(Long ownerId, Long contractorId){
        return applyRepository.findAllByAdOwnerIdAndContractorId(ownerId,contractorId);
    }
    public List<Apply> getAllByAdIdAndContractorId(Long adId, Long contractorId){
        return applyRepository.findAllByAdIdAndContractorId(adId, contractorId);
    }
    public List<Apply> getAllByAdId(Long id){
        return applyRepository.findAllByAdId(id);
    }
    public Optional<Apply> getByAdIdAndContractorId(Long adId, Long contractorId){
        return applyRepository.findByAdIdAndContractorId(adId,contractorId);
    }
}
