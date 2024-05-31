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
import lombok.Data;

@Data
@Entity
@Table(name = "OrderDetails")
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_OrderDetails")
	private Integer id_OrderDetails;
	
	@Column(name = "fullname", length = 100, nullable = false)
	private String fullname;
	
	@Column(name = "address", length = 250, nullable = false)
	private String address;
	
	@Column(name = "price", nullable = false)
	private Double price;
	
	@Column(name = "quantity", nullable = false)
	private Integer quantity;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false)
	private Date date = new Date();
	
	@ManyToOne
	@JoinColumn(name = "id_order", referencedColumnName = "id_order", nullable = false)
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "id_product", referencedColumnName = "id_product", nullable = false)
	private Product product;
}
