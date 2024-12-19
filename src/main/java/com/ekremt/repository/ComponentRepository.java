package com.ekremt.repository;


import com.ekremt.entity.Component;

public class ComponentRepository extends RepositoryManager<Component, Long> {
	public ComponentRepository() {
		super(Component.class);
	}
	
	
}