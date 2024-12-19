package com.ekremt.service;


import com.ekremt.entity.Component;
import com.ekremt.repository.ComponentRepository;

public class ComponentService extends ServiceManager<Component, Long> {
	private final ComponentRepository componentRepository;
	private final PcService pcService;
	
	public ComponentService(ComponentRepository componentRepository, PcService pcService) {
		super(componentRepository);
		this.componentRepository = componentRepository;
		this.pcService = pcService;
	}
}