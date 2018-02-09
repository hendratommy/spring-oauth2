package com.jgpid.oauth2jwt.repository;

import com.jgpid.oauth2jwt.model.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by hendr on 02/07/2017.
 */
public interface RoleRepository extends JpaRepository<Role, Long>, RoleRepositoryCustom {
	@Query("SELECT r FROM Role r WHERE r.name = :name")
	Role findByName(@Param("name") String name);
}
