package com.ekremt.service;


import com.ekremt.entity.User;
import com.ekremt.repository.UserRepository;

public class UserService extends ServiceManager<User, Long> {
	private final UserRepository userRepository;
	private final PcService pcService;
	private final ComponentService componentService;
	
	public UserService(UserRepository userRepository,PcService pcService,ComponentService componentService) {
		super(userRepository);
		this.userRepository = userRepository;
		this.pcService = pcService;
		this.componentService = componentService;
	}
	
	
}