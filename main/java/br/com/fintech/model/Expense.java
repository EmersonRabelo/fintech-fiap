package br.com.fintech.model;

import java.time.LocalDateTime;

public class Expense {
	private int id;
	private int user;
	private String name;
	private float value;
	private LocalDateTime dt_created;
	
	public Expense(int user, String name, float value) {
		super();
		this.user = user;
		this.name = name;
		this.value = value;
	}
	
	public Expense() {
		super();
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDt_created() {
		return dt_created;
	}

	public void setDt_created(LocalDateTime dt_created) {
		this.dt_created = dt_created;
	}
}
