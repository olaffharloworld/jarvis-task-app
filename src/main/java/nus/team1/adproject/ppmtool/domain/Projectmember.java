package nus.team1.adproject.ppmtool.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.validation.constraints.NotBlank;

@Entity
public class Projectmember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String projectIdentifier;
	@Column
	private String userName;

	public Projectmember() {
	}

	public Projectmember(String projectIdentifier, String userName) {
		super();
		this.projectIdentifier = projectIdentifier;
		this.userName = userName;
	}

	public Projectmember(String projectIdentifier) {
		super();
		this.projectIdentifier = projectIdentifier;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

}