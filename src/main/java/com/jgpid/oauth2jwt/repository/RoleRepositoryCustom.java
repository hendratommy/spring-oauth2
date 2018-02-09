package com.jgpid.oauth2jwt.repository;

import com.jgpid.oauth2jwt.model.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleRepositoryCustom {
	Page<Role> findDistinctRoles(Pageable pageable);

	Page<Role> searchAny(Pageable pageable, String keyword);
}
