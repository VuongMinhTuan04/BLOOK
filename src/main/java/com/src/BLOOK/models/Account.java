package com.src.BLOOK.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Account")
public class Account {
	@Id
	@Column(name = "username", length = 50)
	private String username;
	
	@Column(name = "password", length = 50, nullable = false)
	private String password;
	
	@Column(name = "fullname", length = 100, nullable = false)
	private String fullname;
	
	@Column(name = "email", length = 100, nullable = false)
	private String email;
	
	@Column(name = "phone_number", length = 12, nullable = false)
	private String phone_number;
	
	@Column(name = "avatar", columnDefinition = "TEXT")
	private String avatar;
	
	@ManyToOne
	@JoinColumn(name = "role", referencedColumnName = "role", nullable = false)
	private Role_Account role;
	
	@OneToMany(mappedBy = "account")
	private Set<Order> orders;
	
	@OneToMany(mappedBy = "account")
	private Set<Cart> carts;
}
