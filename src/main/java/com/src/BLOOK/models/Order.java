package com.src.BLOOK.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_order")
	private Long id_order;
	
	@Column(name = "name", length = 150, nullable = false)
	private String name;
	
	@Column(name = "fullname", length = 150, nullable = false)
	private String fullname;
	
	@Column(name = "price", nullable = false)
	private Double price;
	
	@Column(name = "quantity", nullable = false)
	private Integer quantity;
	
	@Column(name = "sum_price", nullable = false)
	private Double sum_price;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false)
	private Date date = new Date();
	
	@ManyToOne
	@JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "id_product", referencedColumnName = "id_product", nullable = false)
	private Product product;
}
