package labs.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonView;

import labs.View;


@Entity
@Table(indexes = {
  @Index(columnList="login", unique = true)
})
public class User implements UserDetails {
	private static final long serialVersionUID = -532710433531902917L;
	public enum Gender {MALE,FEMALE,UNDEFINED};
	@Id
	@GeneratedValue
	private Long id;
	
	@JsonView(View.Summary.class)
	@NotBlank
	@Size(min = 1, max = 512)
	@Column(unique = true)
	private String login;
	
	@JsonView(View.Summary.class)
	@Size(min = 0, max = 512)
	@Column(unique = false)
	private String fullName;
	
	@JsonView(View.Summary.class)
	private Gender gender=Gender.UNDEFINED;
	
	public String getGenderTextRepresentation(){
		switch (gender){
		case UNDEFINED:
			return "Не визначився";
		case MALE:
			return "Супермен";
		case FEMALE:
		return "Супервумен";
		default:
			return "Не визначився";
		}
	}
	
	@JsonView(View.Summary.class)
	@Size(min = 0, max = 512)
	private String address;
	
	@JsonView(View.Summary.class)
	@Size(min = 0, max = 10)
	private String phone;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Gender getGender() {
		return gender;
	}
	public boolean isGenderSetted() {
		return !(gender==Gender.UNDEFINED);
	}
	public String getProfileImagePath(){
		switch (gender){
		case UNDEFINED:
			return "images/alien.png";
		case MALE:
			return "images/man.png";
		case FEMALE:
		return "images/girl.png";
		default:
			return "images/alien.png";
		}
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@NotBlank
	@Size(min = 1, max = 100)
	private String password;
	
	
	@OneToMany(mappedBy = "author")
	private List<Post> posts = new ArrayList<>();
	
	
	
	public User() {
		super();
	}
	
	public User(String login,String password) {
		this.login = login;
		this.password = password;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("USER");
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public static User getCurrentUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	public static Long getCurrentUserId() {
		User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return u.getId();
	}
	
	public static boolean isAnonymous() {
		// Метод SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
		// нічого не дасть, оскільки анонімний користувач теж вважається авторизованим
		return "anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getName());
	}
	
}
