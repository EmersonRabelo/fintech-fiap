package br.com.fintech.model;

public class Report {
	private int id;
	private int user;
	private String month;
	private Float incomesValue;
	private Float expensesValue;
	
	public Report(int user, String month, Float incomesValue, Float expensesValue) {
		super();
		this.user = user;
		this.month = month;
		this.incomesValue = incomesValue;
		this.expensesValue = expensesValue;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Float getIncomesValue() {
		return incomesValue;
	}

	public void setIncomesValue(Float incomesValue) {
		this.incomesValue = incomesValue;
	}

	public Float getExpensesValue() {
		return expensesValue;
	}

	public void setExpensesValue(Float expensesValue) {
		this.expensesValue = expensesValue;
	}
	
	
}
