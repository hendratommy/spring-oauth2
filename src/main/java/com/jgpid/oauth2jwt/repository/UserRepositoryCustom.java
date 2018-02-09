package com.jgpid.oauth2jwt.repository;

import com.jgpid.oauth2jwt.model.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {
	public Page<User> findDistinctUsers(Pageable pageable);

	// @Query("SELECT DISTINCT u FROM User u LEFT JOIN u.roles r WHERE ((u.username LIKE CONCAT('%', :keyword, '%')) OR (u.name LIKE CONCAT('%', :keyword, '%')) OR (r.name LIKE CONCAT('%', :keyword, '%')))")
	public Page<User> searchAny(Pageable pageable, String keyword);

	// @Query("SELECT DISTINCT u FROM User u LEFT JOIN u.roles r WHERE ((u.username LIKE CONCAT('%', :keyword, '%')) OR (u.name LIKE CONCAT('%', :keyword, '%')) OR (u.enabled = :enabled) OR (r.name LIKE CONCAT('%', :keyword, '%')))")
	public Page<User> searchAny(Pageable pageable, String keyword, Boolean enabled);
}
