package br.com.fintech.model;

import java.time.LocalDateTime;

public class MonthlyIncome {
	private int id;
	private int user;
	private String name;
	private float value;
	private LocalDateTime creationDate;
	
	public MonthlyIncome(int user, String name, float value) {
		super();
		this.user = user;
		this.name = name;
		this.value = value;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	
}
