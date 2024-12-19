package com.ekremt.service;



import com.ekremt.entity.Pc;
import com.ekremt.repository.PcRepository;

import java.util.List;

public class PcService extends ServiceManager<Pc, Long> {
	private final PcRepository pcRepository;
	private final ComponentService componentService;
	
	public PcService(PcRepository pcRepository, ComponentService componentService) {
		super(pcRepository);
		this.pcRepository = pcRepository;
		this.componentService = componentService;
	}
	
	public List<Pc> findAllPcByUserId(Long userId) {
		return pcRepository.findAllPcByUserId(userId);
	}
	
}