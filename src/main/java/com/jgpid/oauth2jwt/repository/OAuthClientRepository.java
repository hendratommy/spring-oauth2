package com.jgpid.oauth2jwt.repository;

import com.jgpid.oauth2jwt.model.domain.OAuthClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuthClientRepository extends JpaRepository<OAuthClient, String> {
}
