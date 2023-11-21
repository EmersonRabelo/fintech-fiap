package br.com.fintech.model;


public class Goal {
	private int id;
	private int user;
	private String name;
	private String deadLine;
	private float completion;
	
	public Goal(int user, String name, String deadLine, float completion) {
		super();
		this.user = user;
		this.name = name;
		this.deadLine = deadLine;
		this.completion = completion;
	}
	
	public Goal() {
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

	public String getDeadline() {
		return deadLine;
	}

	public void setDeadline(String deadline) {
		this.deadLine = deadline;
	}

	public float getCompletion() {
		return completion;
	}

	public void setCompletion(float completion) {
		this.completion = completion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
