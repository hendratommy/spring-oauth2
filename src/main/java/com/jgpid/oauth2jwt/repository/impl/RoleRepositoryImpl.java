package com.jgpid.oauth2jwt.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.jgpid.oauth2jwt.model.domain.Role;
import com.jgpid.oauth2jwt.repository.RoleRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly=true)
public class RoleRepositoryImpl implements RoleRepositoryCustom {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(RoleRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public Page<Role> findDistinctRoles(Pageable pageable) {
		String queryStr = "SELECT DISTINCT r FROM Role r LEFT JOIN r.privileges rp";
		String orderBy = " ORDER BY %s %s";

		Sort sort = pageable.getSort();
		Order order = sort != null ? sort.iterator().next() : null;
		if (order != null) {
			if (order.getProperty().equals("privileges")) {
				queryStr = queryStr + String.format(orderBy, "rp.name", order.isAscending() ? "ASC" : "DESC");
			}
			else {
				queryStr = queryStr + String.format(orderBy, "r." + order.getProperty(), order.isAscending() ? "ASC" : "DESC");
			}
		}

		Query query = entityManager.createQuery(queryStr, Role.class);

		// get total rows for page
		int totalRows = query.getResultList().size();

		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		return new PageImpl<Role>(query.getResultList(), pageable, totalRows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Role> searchAny(Pageable pageable, String keyword) {
		String queryStr = "SELECT DISTINCT r FROM Role r LEFT JOIN r.privileges rp "
				+ "WHERE ((r.name LIKE CONCAT('%', :keyword, '%')) OR (r.description LIKE CONCAT('%', :keyword, '%')) "
				+ "OR (rp.name LIKE CONCAT('%', :keyword, '%')))";
		String orderBy = " ORDER BY %s %s";

		Sort sort = pageable.getSort();
		Order order = sort != null ? sort.iterator().next() : null;
		if (order != null) {
			if (order.getProperty().equals("privileges")) {
				queryStr = queryStr + String.format(orderBy, "rp.name", order.isAscending() ? "ASC" : "DESC");
			}
			else {
				queryStr = queryStr + String.format(orderBy, "r." + order.getProperty(), order.isAscending() ? "ASC" : "DESC");
			}
		}

		Query query = entityManager.createQuery(queryStr, Role.class);
		query.setParameter("keyword", keyword);

		// get total rows for page
		int totalRows = query.getResultList().size();

		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		return new PageImpl<Role>(query.getResultList(), pageable, totalRows);
	}

}
