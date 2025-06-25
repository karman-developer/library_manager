package com.example.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "LOGS")
@Data
public class Log {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "LIBRARY_ID", referencedColumnName = "id")
	private Library library;

	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "id")
	private User user;

	@Column(name = "RENT_DATE")
	private Date rentDate;

	@Column(name = "RETURN_DATE")
	private Date returnDate;

	@Column(name = "RETURN_DUE_DATE")
	private Date returnDueDate;
}
