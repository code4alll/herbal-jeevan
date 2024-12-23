package com.ecommerce.HerbalJeevan.DTO;


import java.util.Set;

public class AdminFilters {
	private Set<Long> id;
	private Set<String> name;
	private Set<String> country;
	public Set<Long> getId() {
		return id;
	}
	public void setId(Set<Long> id) {
		this.id = id;
	}
	public Set<String> getName() {
		return name;
	}
	public void setName(Set<String> name) {
		this.name = name;
	}
	public Set<String> getCountry() {
		return country;
	}
	public void setCountry(Set<String> country) {
		this.country = country;
	}
	public AdminFilters() {
		super();
		// TODO Auto-generated constructor stub
	}

}
