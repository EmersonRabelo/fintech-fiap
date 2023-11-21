package br.com.fintech.model;

import java.time.LocalDate;

import br.com.fintech.utils.PasswordStorage;
import br.com.fintech.utils.PasswordStorage.CannotPerformOperationException;

public class User {
	private int id;
	private String name;
	private String email;
	private String pwd;
	private LocalDate dtBirth;
	private String occupation;
	
	public User( String name, String email, String pwd, LocalDate dtBirth, String occupation) {
		super();
		this.name = name;
		this.email = email;
		this.pwd = pwd;
		this.dtBirth = dtBirth;
		this.occupation = occupation;
	}
	
	public User() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = createHashPwd(pwd);
	}
	
	private String createHashPwd(String password) {
		
		String hash = null;
		
		try {
			
			hash = PasswordStorage.createHash(password);
			
		} catch(CannotPerformOperationException e) {
			System.out.println("Problema ocorrido na criação da hash: " + e.getMessage());
			e.getStackTrace();
		}
		
		return hash;
	}
	

	public LocalDate getDtBirth() {
		return dtBirth;
	}

	public void setDtBirth(LocalDate dtBirth) {
		this.dtBirth = dtBirth;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	
}
