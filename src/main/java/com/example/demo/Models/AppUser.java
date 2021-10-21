package com.example.demo.Models;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.example.demo.Utils.appuser.AppUserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class AppUser implements UserDetails {

	@SequenceGenerator(
			name = "student_sequence",
			sequenceName = "student_sequence",
			allocationSize = 1
	)
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "student_sequence"
	)
	private Long id;
	@JsonIgnore
	@ManyToMany
	@JoinTable(
			name = "user_category",
			joinColumns = @JoinColumn(name = "app_user_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<>();

	@JsonIgnore
	@ManyToMany
	@JoinTable(
			name = "user_city",
			joinColumns = {
					@JoinColumn(name = "app_user_id",referencedColumnName = "id",
					nullable = false, updatable = false)},
			inverseJoinColumns = {
					@JoinColumn(name = "city_id",referencedColumnName = "id",
					nullable = false, updatable = false)})
	private Set<City> cities = new HashSet<>();

	/*
	@JsonIgnore
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
			name = "user_message",
			joinColumns = @JoinColumn(name = "app_user_id"),
			inverseJoinColumns = @JoinColumn(name = "message_id"))
	private Set<Message> messages = new HashSet<>();
	*/
	@OneToMany(
			mappedBy = "user",
			cascade = CascadeType.ALL,
			orphanRemoval = true) //ukoliko se ukloni user uklone se i ads koji su vezani za njega
	private Set<Message> messages = new HashSet<>();

	@OneToMany(
			mappedBy = "user",
			cascade = CascadeType.ALL,
			orphanRemoval = true) //ukoliko se ukloni user uklone se i ads koji su vezani za njega
	private Set<Ad> ads = new HashSet<>();

	public String firstName;
	private String lastName;
	private String email;
	private String password;
	private String address;
	private String phone;
	@Enumerated(EnumType.STRING)
	private AppUserRole appUserRole;
	private Boolean locked = false;
	private Boolean enabled = false;
	@Column(nullable = true)
	private double rating;
	@Column(nullable = true)
	private int numberOfRatings;
	
	
	public AppUser(String firstName, String lastName, String email, String password, String phone,
				   AppUserRole appUserRole) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.appUserRole = appUserRole;
		this.phone = phone;
	}

	public AppUser(String firstName, String lastName, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.appUserRole = AppUserRole.USER;
	}
	public AppUser(String firstName, String lastName, String email, String password,
				   String address, String phone) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.address = address;
		this.phone = phone;
		this.appUserRole = AppUserRole.USER;
		this.rating = 0.0;
		this.numberOfRatings=0;
	}

	public AppUser(String user) {
		this.firstName = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = 
				new SimpleGrantedAuthority(appUserRole.name());
		return Collections.singleton(authority);
	}

	@Override
	public String getPassword() {
		return password;
	}

	public String getUsername(){
		return email;
	}

	public String getFirstName(){
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getAddress(){return address;}
	public String getPhone(){return phone;}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}
	public void addCityToUser(City city){
		cities.add(city);
	}
	public void addCategoryToUser(Category c){
		categories.add(c);
	}
	public void addMessageToUser(Message m) { messages.add(m);}
	public String getRole(){
		return appUserRole.name();
	}
}
