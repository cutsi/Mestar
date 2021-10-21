package com.example.demo.Repos;

import com.example.demo.Models.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ApplyRepository extends JpaRepository <Apply, Long> {
    @Override
    Optional<Apply> findById(Long id);
    List<Apply> findAllByAdOwnerId(Long id);
    List<Apply> findAllByContractorId(Long id);
    List<Apply> findAllByAdOwnerIdAndContractorId(Long ownerId, Long contractorId);
    List<Apply> findAllByAdIdAndContractorId(Long adId, Long contractorId);
    List<Apply> findAllByAdId(Long id);
    Optional<Apply> findByAdIdAndContractorId(Long adId, Long contractorId);

}
