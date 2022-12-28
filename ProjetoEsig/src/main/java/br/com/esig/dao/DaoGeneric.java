package br.com.esig.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.esig.jpautil.JPAutil;

public class DaoGeneric<E> {

	public void salvar(E entidade) {

		EntityManager entityManager = JPAutil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		entityManager.persist(entidade);

		entityTransaction.commit();
		entityManager.close();
	}

	public E merge(E entidade) {

		EntityManager entityManager = JPAutil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		E retorno = entityManager.merge(entidade);

		entityTransaction.commit();
		entityManager.close();

		return retorno;
	}

	public List<E> getListEntity(Class<E> entidade) {

		EntityManager entityManager = JPAutil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		List<E> retorno = entityManager.createQuery("from " + entidade.getName()).getResultList();

		entityTransaction.commit();
		entityManager.close();

		return retorno;
	}

	public void excluir(E entidade) {

		EntityManager entityManager = JPAutil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		Object id = JPAutil.getPrimaryKey(entidade);
		entityManager.createQuery("delete from " + entidade.getClass().getCanonicalName() + " where id = " + id)
				.executeUpdate();

		entityTransaction.commit();
		entityManager.close();
	}

}
