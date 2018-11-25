package com.ynov.dap.google.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.ynov.dap.data.AppUser;

@Entity
public class GoogleAccount {

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	private AppUser owner;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public AppUser getOwner() {
		return owner;
	}

	public void setOwner(final AppUser owner) {
		this.owner = owner;
	}
}