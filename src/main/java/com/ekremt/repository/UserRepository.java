package com.ekremt.repository;


import com.ekremt.entity.User;

public class UserRepository extends RepositoryManager<User, Long> {
	public UserRepository() {
		super(User.class);
	}
	
	
}