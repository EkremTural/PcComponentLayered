package com.ekremt.repository;



import com.ekremt.utility.JPAUtility;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

public abstract class RepositoryManager<T, ID> implements ICrud<T, ID> {
	
	private EntityManager em;
	private Class<T> entityClass;
	
	public RepositoryManager(Class<T> entityClass) {
		this.em = JPAUtility.getEntityManager();
		this.entityClass = entityClass;
	}
	
	@Override
	public T save(T entity) {
		EntityTransaction transaction = null;
		try {
			transaction = em.getTransaction();
			transaction.begin();
			em.persist(entity);
			transaction.commit();
			return entity;
		}
		catch (Exception e) {
			if (transaction != null && transaction.isActive())
				em.getTransaction().rollback();
			throw e;
		}
	}
	
	@Override
	public Iterable<T> saveAll(Iterable<T> entities) {
		EntityTransaction transaction = null;
		try {
			transaction = em.getTransaction();
			transaction.begin();
			for (T entity : entities) {
				em.persist(entity);
			}
			transaction.commit();
			return entities;
		}
		catch (Exception e) {
			if (transaction != null && transaction.isActive())
				em.getTransaction().rollback();
			throw e;
		}
	}
	
	@Override
	public boolean deleteById(ID id) {
		EntityTransaction transaction = null;
		try {
			transaction = em.getTransaction();
			transaction.begin();
			T entityToRemove = em.find(entityClass, id);
			if (entityToRemove != null) {
				em.remove(entityToRemove);
				transaction.commit();
				return true;
			}
			transaction.rollback();
			return false;
		}
		catch (Exception e) {
			System.out.println("Delete işleminde hata meydana geldi..." + e.getMessage());
			if (transaction != null && transaction.isActive())
				em.getTransaction().rollback();
			return false;
		}
	}
	
	@Override
	public T update(T entity) {
		EntityTransaction transaction = null;
		try {
			transaction = em.getTransaction();
			transaction.begin();
			T updatedEntity = em.merge(entity);
			transaction.commit();
			return updatedEntity;
		}
		catch (Exception e) {
			System.out.println("Update işleminde hata meydana geldi..." + e.getMessage());
			if (transaction != null && transaction.isActive())
				em.getTransaction().rollback();
			throw e;
		}
	}
	
	@Override
	public Optional<T> findById(ID id) {
		T entity = em.find(entityClass, id);
		return Optional.ofNullable(entity);
	}
	
	@Override
	public List<T> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = cb.createQuery(entityClass);
		
		Root<T> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(root);
		
		TypedQuery<T> q = em.createQuery(criteriaQuery);
		return q.getResultList();
	}
	
	@Override
	public boolean existsById(ID id) {
		return em.find(entityClass, id) != null;
	}
}