package com.example.shdemo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({ 
	@NamedQuery(name = "archive.all", query = "Select a from Archive a"),
	@NamedQuery(name = "archive.byName", query = "Select a from Archive a where a.name = :name"),
	@NamedQuery(name = "archive.byTeamNumber", query = "Select a from Archive a where a.teamNumber = :teamNumber")
})
public class Archive {

	private Long id;
	private String name = "";
	private int teamNumber = 0;
	private String phone = "";

	private List<Resource> resources = new ArrayList<Resource>();

	public Archive() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Archive(Long archiveId, String name2, int teamNumber2,
			String phone2) {
		this.id = archiveId;
		this.name = name2;
		this.teamNumber = teamNumber2;
		this.phone = phone2;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(unique = true, nullable = false)
	public int getTeamNumber() {
		return teamNumber;
	}
	public void setTeamNumber(int teamNumber) {
		this.teamNumber = teamNumber;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	// Be careful here, both with lazy and eager fetch type
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Resource> getResources() {
		return resources;
	}
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
}
