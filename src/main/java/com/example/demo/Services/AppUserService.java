package com.example.demo.Services;

import com.example.demo.Models.Category;
import com.example.demo.Models.City;
import com.example.demo.Models.AppUser;
import com.example.demo.Repos.AppUserRepository;
import com.example.demo.Utils.registration.token.ConfirmationToken;
import com.example.demo.Utils.registration.token.ConfirmationTokenService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService{

	private static final String USER_NOT_FOUND =
			"user with email %s not found";
	private final AppUserRepository appUserRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ConfirmationTokenService confirmationTokenService;

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		return appUserRepository.findByEmail(email).
				orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
	}
	public String signUpUser(AppUser appUser){
		// check ih user exists
		boolean userExists = appUserRepository.findByEmail(appUser.getEmail())
				.isPresent();
		if (userExists){
			// TODO check of attributes are the same and
			// TODO if email not confirmed send confirmation email.
			throw new IllegalStateException("email already taken");
		}
		String encodedPassword = bCryptPasswordEncoder
				.encode(appUser.getPassword());
		appUser.setPassword(encodedPassword);
		appUserRepository.save(appUser);

		String token = UUID.randomUUID().toString();
		// TODO: Send confirmation token later
		ConfirmationToken confirmationToken = new ConfirmationToken(
				token,
				LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(15),
				appUser
		);

		confirmationTokenService.saveConformationToken(confirmationToken);

		// TODO: SEND EMAIL

		return token;
	}
	public Optional<AppUser> getCurrentUser(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getName();

		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else { username = principal.toString(); }
		Optional<AppUser> user = appUserRepository.findByEmail(username);
		return user;
	}
	public int enableAppUser(String email) {
		return appUserRepository.enableAppUser(email);
	}
	public int enableHandyman(String email)
	{
		return appUserRepository.enableHandyman(email);
	}
	public Optional<AppUser> getUserById(Long id){
		return appUserRepository.findById(id);
	}
	public Optional<AppUser> getUserByEmail(String email){
		return appUserRepository.findByEmail(email);
	}
	public Set<City> getAllCitiesFromUser(String email){
		return appUserRepository.getCitiesByUser(email);
	}
	public Set<Category> getAllCategoriesFromUser(String email){
		return appUserRepository.getCategoriesByUser(email);
	}
	public Set<AppUser> getAllUsersByCategory(String categoryName){
		return appUserRepository.getUsersByCategory(categoryName);
	}
	public Set<AppUser> getAllUsersByCity(String cityName){
		return appUserRepository.getUsersByCity(cityName);
	}
	public Set<AppUser> getAllUsersByCategoryAndCity(Set<AppUser> userCat,
													 Set<AppUser> userCity){
		Set<AppUser> intersection = new HashSet<>();
		userCat.forEach((i) -> {
			if (userCity.contains(i))
				intersection.add(i);
		});
		return intersection;
	}

	public void saveUser(AppUser user) {
		appUserRepository.save(user);
	}
	public Set<AppUser> getAllUsersByContractorRole(){
		return appUserRepository.findAllByRoleHandyMan();
	}
	public Set<AppUser> getAllUsersByUserRole(){
		return appUserRepository.findAllByRoleUser();
	}
	public Set<AppUser> getALlCorrespondantsFromUser(Long id) { return appUserRepository.findAllCorrespondantsFromCurrentUser(id);}

	public void getListOfAllConversationsFromUser() {

	}
}
