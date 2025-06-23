package com.example.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
	@Column(name = "LIBRARY_ID")
	private Integer library_id;

	@Column(name = "USER_ID")
	private Integer user_id;

	@Column(name = "RENT_DATE")
	private Date rent_date;

	@Column(name = "RETURN_DATE")
	private Date return_date;

	@Column(name = "RETURN_DUE_DATE")
	private Date return_due_date;
}
