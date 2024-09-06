package com.src.BLOOK.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Role_Account")
public class Role_Account {
	@Id
	@Column(name = "role")
	private Boolean role;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "role") //FetchType.EAGER để tải dữ liệu ngay lập tức
	private Set<Account> accounts;

	public Role_Account(Boolean role) {
		super();
		this.role = role;
	}
}
