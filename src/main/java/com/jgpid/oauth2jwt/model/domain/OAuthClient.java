package com.jgpid.oauth2jwt.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.envers.Audited;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

@Entity
@Audited
@Table(name = "tbl_oauth_client")
@EntityListeners(AuditingEntityListener.class)
public class OAuthClient extends BaseClientDetails {
    private static final Logger logger = LoggerFactory.getLogger(OAuthClient.class);

    // versioning
    private Long version = 0L;

    // audit
    private Calendar createdDate;
    private Calendar updatedDate;
    private String createdBy;
    private String lastModifiedBy;

    @JsonIgnore
    @Id
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "client_id", unique = true)
    public String getClientIdColumn() {
        return getClientId();
    }

    public void setClientIdColumn(String clientIdColumn) {
        setClientId(clientIdColumn);
    }

    @JsonIgnore
    @Size(max = 255)
    @Column(name = "resource_ids")
    public String getResourceIdsColumn() {
        if (getResourceIds() != null)
            return StringUtils.collectionToCommaDelimitedString(getResourceIds());
        return null;
    }

    public void setResourceIdsColumn(String resourceIdsColumn) {
        if (resourceIdsColumn != null)
            setResourceIds(StringUtils.commaDelimitedListToSet(resourceIdsColumn));
    }

    @JsonIgnore
    @Size(max = 255)
    @Column(name = "client_secret")
    public String getClientSecretColumn() {
        return getClientSecret();
    }

    public void setClientSecretColumn(String clientSecretColumn) {
        setClientSecret(clientSecretColumn);
    }

    @JsonIgnore
    @Size(max = 255)
    @Column(name = "scope")
    public String getScopeColumn() {
        if (getScope() != null)
            return StringUtils.collectionToCommaDelimitedString(getScope());
        return null;
    }

    public void setScopeColumn(String scopeColumn) {
        if (scopeColumn != null)
            setScope(StringUtils.commaDelimitedListToSet(scopeColumn));
    }


    @JsonIgnore
    @Size(max = 255)
    @Column(name = "authorized_grant_types")
    public String getAuthorizedGrantTypesColumn() {
        if (getAuthorizedGrantTypes() != null)
            return StringUtils.collectionToCommaDelimitedString(getAuthorizedGrantTypes());
        return null;
    }

    public void setAuthorizedGrantTypesColumn(String authorizedGrantTypesColumn) {
        if (authorizedGrantTypesColumn != null)
            setAuthorizedGrantTypes(StringUtils.commaDelimitedListToSet(authorizedGrantTypesColumn));
    }

    @JsonIgnore
    @Size(max = 255)
    @Column(name = "web_server_redirect_uri")
    public String getWebServerRedirectUriColumn() {
        if (getRegisteredRedirectUri() != null)
            return StringUtils.collectionToCommaDelimitedString(getRegisteredRedirectUri());
        return null;
    }

    public void setWebServerRedirectUriColumn(String webServerRedirectUriColumn) {
        if (webServerRedirectUriColumn != null)
            setRegisteredRedirectUri(StringUtils.commaDelimitedListToSet(webServerRedirectUriColumn));
    }

    @JsonIgnore
    @Size(max = 255)
    @Column(name = "authorities")
    public String getAuthoritiesColumn() {
        if (getAuthorities() != null)
            return StringUtils.collectionToCommaDelimitedString(new ArrayList<>(
                    AuthorityUtils.authorityListToSet(getAuthorities())));
        return null;
    }

    public void setAuthoritiesColumn(String authoritiesColumn) {
        if (authoritiesColumn != null)
            setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesColumn));
    }

    @JsonIgnore
    @Column(name = "access_token_validity")
    public Integer getAccessTokenValidityColumn() {
        return super.getAccessTokenValiditySeconds();
    }

    public void setAccessTokenValidityColumn(Integer accessTokenValidityColumn) {
        super.setAccessTokenValiditySeconds(accessTokenValidityColumn);
    }

    @JsonIgnore
    @Column(name = "refresh_token_validity")
    public Integer getRefreshTokenValidityColumn() {
        return getRefreshTokenValiditySeconds();
    }

    public void setRefreshTokenValidityColumn(Integer refreshTokenValidityColumn) {
        setRefreshTokenValiditySeconds(refreshTokenValidityColumn);
    }

    @JsonIgnore
    @Size(max = 4096)
    @Column(name = "additional_information")
    public String getAdditionalInformationColumn() {
        String json = null;
        try {
            if (getAdditionalInformation() != null)
                json = new ObjectMapper().writeValueAsString(getAdditionalInformation());
        } catch (JsonProcessingException e) {
            logger.error("Could not serialize additional information", e);
        }
        return json;
    }

    public void setAdditionalInformationColumn(String additionalInformationStr) {
        try {
            if (additionalInformationStr != null)
                super.setAdditionalInformation(new ObjectMapper().readValue(additionalInformationStr, Map.class));
        } catch (IOException e) {
            logger.error("Could not decode JSON for additional information: " + additionalInformationStr, e);
        }
    }

    @JsonIgnore
    @Size(max = 255)
    @Column(name = "auto_approve")
    public String getAutoApproveColumn() {
        if (getAutoApproveScopes() != null)
            return StringUtils.collectionToCommaDelimitedString(getAutoApproveScopes());
        return null;
    }

    public void setAutoApproveColumn(String autoApproveColumn) {
        if (autoApproveColumn != null)
            setAutoApproveScopes(StringUtils.commaDelimitedListToSet(autoApproveColumn));
    }

    @Version
    @Column(name = "version", precision = 20, nullable = false, columnDefinition = "bigint default 0")
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    @CreatedDate
    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    @LastModifiedDate
    public Calendar getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Calendar updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Column(name = "created_by")
    @CreatedBy
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "updated_by")
    @LastModifiedBy
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
