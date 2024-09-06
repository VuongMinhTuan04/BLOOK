package com.src.BLOOK.models;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "Product")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_product")
	private Long id_product;
	
	@Column(name = "name", length = 150, nullable = false)
	private String name;
	
	@Column(name = "price", nullable = false)
	private Double price;
	
	@Column(name = "quantity", nullable = false)
	private Integer quantity = 1;
	
	@Column(name = "image", columnDefinition = "TEXT", nullable = false)
	private String image;
	
	@Column(name = "status", nullable = false)
	private Boolean status;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date = new Date();

	@ManyToOne
	@JoinColumn(name = "id_categories", referencedColumnName = "id_categories", nullable = false)
	private Categories categories;
	
	@OneToMany(mappedBy = "product")
	private Set<Order> orders;

	@OneToMany(mappedBy = "product")
	private Set<OrderDetail> orderDetails;
	
	@OneToMany(mappedBy = "product")
    private Set<Cart> carts;
}
