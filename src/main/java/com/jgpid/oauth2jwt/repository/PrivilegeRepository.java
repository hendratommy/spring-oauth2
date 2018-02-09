package com.jgpid.oauth2jwt.repository;

import com.jgpid.oauth2jwt.model.domain.Privilege;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by hendr on 02/07/2017.
 */
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
	@Query("SELECT DISTINCT p FROM Privilege p WHERE p.name LIKE CONCAT('%', :keyword, '%')")
	Page<Privilege> searchAny(Pageable pageable, @Param("keyword") String keyword);
}
