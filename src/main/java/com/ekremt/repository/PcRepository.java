package com.ekremt.repository;


import com.ekremt.entity.Pc;
import com.ekremt.utility.JPAUtility;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PcRepository extends RepositoryManager<Pc, Long> {
	private EntityManager entityManager;
	
	public PcRepository() {
		super(Pc.class);
		entityManager = JPAUtility.getEntityManager();
	}
	
	
	public List<Pc> findAllPcByUserId(Long userId) {
		String jpql = "SELECT p FROM Pc p WHERE p.userId = :userId";
		return entityManager
							.createQuery(jpql, Pc.class)
							.setParameter("userId", userId)
							.getResultList();
	}
}