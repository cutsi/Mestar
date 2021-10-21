package com.example.demo.Repos;

import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;

import com.example.demo.Models.Category;
import com.example.demo.Models.City;
import com.example.demo.Models.AppUser;
import com.example.demo.Utils.appuser.AppUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
@Transactional
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	AppUserRole role = AppUserRole.CONTRACTOR;
	//AppUser findById(Long id);
	Optional<AppUser> findByEmail(String email);
	Optional<AppUser> findByFirstName(String firstName);
	@Transactional
	@Modifying
	@Query("UPDATE AppUser a " +
			"SET a.enabled = TRUE WHERE a.email = ?1")
	int enableAppUser(String email);
	@Transactional
	@Modifying
	@Query("UPDATE AppUser a " +
			"SET a.appUserRole = 'CONTRACTOR' WHERE a.email = ?1")
	int enableHandyman(String email);

	@Query("SELECT a FROM City a INNER JOIN a.userCity c WHERE c.email IN (?1)")
	Set<City> getCitiesByUser(String email);
	@Query("SELECT a FROM Category a INNER JOIN a.users c WHERE c.email IN (?1)")
	Set<Category> getCategoriesByUser(String email);
	@Query("SELECT a FROM AppUser a INNER JOIN a.categories c WHERE c.categoryName IN (?1)")
	Set<AppUser> getUsersByCategory(String categoryName);
	@Query("SELECT a FROM AppUser a INNER JOIN a.cities c WHERE c.cityName IN (?1)")
	Set<AppUser> getUsersByCity(String cityName);
	@Query("SELECT a FROM AppUser a WHERE a.appUserRole = 'CONTRACTOR'")
	Set<AppUser> findAllByRoleHandyMan();
	@Query("SELECT a FROM AppUser a WHERE a.appUserRole = 'USER'")
	Set<AppUser> findAllByRoleUser();
	@Query("SELECT a FROM AppUser a INNER JOIN a.messages c WHERE c.user.id IN (?1)")
	Set<AppUser> findAllCorrespondantsFromCurrentUser(Long id);
}
