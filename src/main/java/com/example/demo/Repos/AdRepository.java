package com.example.demo.Repos;

import com.example.demo.Models.Category;
import com.example.demo.Models.Ad;
import com.example.demo.Models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface AdRepository extends JpaRepository<Ad, Long> {
    @Override
    Optional<Ad> findById(Long ad_id);
    @Override
    void deleteById(Long id);
    Optional<Ad> findByTitle(String title);
    List<Ad> findByUser(AppUser user);
    List<Ad> findAllByCategory(Category category);
    List<Ad> findAllByUser(AppUser user);

    List<Ad> findAllByIsPickedTrueAndUser(AppUser user);
    List<Ad> findAllByIsPickedFalseAndUser(AppUser user);
    List<Ad> findAllByIsFinishedTrueAndUser(AppUser user);
    List<Ad> findAllByIsFinishedFalseAndUser(AppUser user);
    List<Ad> findAllByUserIdNotIn(List<Long> ids);
    //List<Ad> findLast10();
    //@Query(value="select ad from Ad ad")
    //public List<Ad> findWithPageable(Pageable pageable);
    //public List<Ad> findFirst10ByIdOrderByIdDesc(Long id);
    /*
    @Query("SELECT a FROM Ad a INNER JOIN a.user c WHERE c.email IN (?1)")
    Set<Ad> getAllAdsFromUserEmail(String email);
    @Query("SELECT a FROM Ad a INNER JOIN a.user c WHERE c.id IN (?1)")
    Set<Ad> getAllAdsFromUserId(Long id);
    @Query("SELECT a FROM Ad a INNER JOIN a.category c WHERE c.id IN (?1)")
    Set<Ad> getAllAdsByCategoryId(Long id);
    @Query("SELECT a FROM Ad a INNER JOIN a.category c WHERE c.categoryName IN (?1)")
    Set<Ad> getAllAdsByCategoryName(String categoryName);*/


}
