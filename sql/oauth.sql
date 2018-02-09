SET @currtime = NOW();
-- insert default web client
INSERT INTO oauth2jwt.tbl_oauth_client (
  client_id, client_secret, `scope`,
  authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, additional_information,
  auto_approve, created_by, created_date)
VALUES
  (
    'oauth2jwt', 'aece87dd8c142135d1060cb30861204d', 'all',
                 'authorization_code,implicit,refresh_token,client_credentials,password', NULL, NULL,
                 36000, 36000, NULL,
                 'true', 'SYSTEM', @currtime
  );
-- insert into audit_log
INSERT INTO oauth2jwt_audit_log.revinfo
(REVTSTMP) VALUES
  (UNIX_TIMESTAMP(@currtime));
-- set audit rev id
SET @revId = last_insert_id();
INSERT INTO oauth2jwt_audit_log.tbl_oauth_client_audit_log
(REVTYPE, REV, client_id, client_secret, `scope`,
 authorized_grant_types, web_server_redirect_uri, authorities,
 access_token_validity, refresh_token_validity, additional_information,
 auto_approve, created_by, created_date)
  SELECT
    0      AS REVTYPE,
    @revId AS REV,
    client_id,
    client_secret,
    `scope`,
    authorized_grant_types,
    web_server_redirect_uri,
    authorities,
    access_token_validity,
    refresh_token_validity,
    additional_information,
    auto_approve,
    created_by,
    created_date
  FROM oauth2jwt.tbl_oauth_client
  WHERE client_id = 'oauth2jwt';

-- insert default/built-in admin
-- CREATE DEFAULT USER AS ADMINISTRATOR
INSERT INTO oauth2jwt.tbl_user
(id, created_by, created_date, email, enabled, locked, name, password, username) VALUES
  (1, 'SYSTEM', @currtime, 'admin@admin.com', TRUE, FALSE, 'admin',
   '$2a$10$DY.n0Rs.o3HlJFQBIrf5NulbhAd3JjJeUddzmyeBAonF52YiVUs/.', 'admin');
INSERT INTO oauth2jwt_audit_log.revinfo
(REVTSTMP) VALUES
  (UNIX_TIMESTAMP(@currtime));
SET @revId = last_insert_id();
INSERT INTO oauth2jwt_audit_log.tbl_user_audit_log
(REVTYPE, REV, id, created_by, created_date, email, enabled, locked, name, password, username)
  SELECT
    0      AS REVTYPE,
    @revId AS REV,
    id,
    created_by,
    created_date,
    email,
    enabled,
    locked,
    name,
    password,
    username
  FROM oauth2jwt.tbl_user
  WHERE id = 1;

-- CREATE CORE PRIVILEGES
INSERT INTO oauth2jwt.tbl_privilege
(id, name) VALUES
  (1, 'CREATE_USERS');
INSERT INTO oauth2jwt.tbl_privilege
(id, name) VALUES
  (2, 'READ_USERS');
INSERT INTO oauth2jwt.tbl_privilege
(id, name) VALUES
  (3, 'UPDATE_USERS');
INSERT INTO oauth2jwt.tbl_privilege
(id, name) VALUES
  (4, 'DELETE_USERS');

INSERT INTO oauth2jwt.tbl_privilege
(id, name) VALUES
  (5, 'CREATE_ROLES');
INSERT INTO oauth2jwt.tbl_privilege
(id, name) VALUES
  (6, 'READ_ROLES');
INSERT INTO oauth2jwt.tbl_privilege
(id, name) VALUES
  (7, 'UPDATE_ROLES');
INSERT INTO oauth2jwt.tbl_privilege
(id, name) VALUES
  (8, 'DELETE_ROLES');

INSERT INTO oauth2jwt.tbl_privilege
(id, name) VALUES
  (9, 'CREATE_CLIENTS');
INSERT INTO oauth2jwt.tbl_privilege
(id, name) VALUES
  (10, 'READ_CLIENTS');
INSERT INTO oauth2jwt.tbl_privilege
(id, name) VALUES
  (11, 'UPDATE_CLIENTS');
INSERT INTO oauth2jwt.tbl_privilege
(id, name) VALUES
  (12, 'DELETE_CLIENTS');

-- CREATE ADMINISTRATORS ROLE
INSERT INTO oauth2jwt.tbl_role
(id, created_by, created_date, description, name) VALUES
  (1, 'SYSTEM', @currtime, 'Built-in System Administrators', 'ROLE_ADMINISTRATORS');
INSERT INTO oauth2jwt_audit_log.revinfo
(REVTSTMP) VALUES
  (UNIX_TIMESTAMP(@currtime));
SET @revId = last_insert_id();
INSERT INTO oauth2jwt_audit_log.tbl_role_audit_log
(REVTYPE, REV, id, created_by, created_date, description, name)
  SELECT
    0      AS REVTYPE,
    @revId AS REV,
    id,
    created_by,
    created_date,
    description,
    name
  FROM oauth2jwt.tbl_role
  WHERE id = 1;

-- GRANT ROLE_ADMINISTRATORS THEIR DEFAULT PRIVILEGES
INSERT INTO oauth2jwt.tbl_role_privileges
(role_id, privilege_id) VALUES
  (1, 1);
INSERT INTO oauth2jwt_audit_log.revinfo
(REVTSTMP) VALUES
  (UNIX_TIMESTAMP(@currtime));
SET @revId = last_insert_id();
INSERT INTO oauth2jwt_audit_log.tbl_role_privileges_audit_log
(REVTYPE, REV, role_id, privilege_id)
  SELECT
    0      AS REVTYPE,
    @revId AS REV,
    role_id,
    privilege_id
  FROM oauth2jwt.tbl_role_privileges
  WHERE role_id = 1 AND privilege_id = 1;

INSERT INTO oauth2jwt.tbl_role_privileges
(role_id, privilege_id) VALUES
  (1, 2);
INSERT INTO oauth2jwt_audit_log.revinfo
(REVTSTMP) VALUES
  (UNIX_TIMESTAMP(@currtime));
SET @revId = last_insert_id();
INSERT INTO oauth2jwt_audit_log.tbl_role_privileges_audit_log
(REVTYPE, REV, role_id, privilege_id)
  SELECT
    0      AS REVTYPE,
    @revId AS REV,
    role_id,
    privilege_id
  FROM oauth2jwt.tbl_role_privileges
  WHERE role_id = 1 AND privilege_id = 2;

INSERT INTO oauth2jwt.tbl_role_privileges
(role_id, privilege_id) VALUES
  (1, 3);
INSERT INTO oauth2jwt_audit_log.revinfo
(REVTSTMP) VALUES
  (UNIX_TIMESTAMP(@currtime));
SET @revId = last_insert_id();
INSERT INTO oauth2jwt_audit_log.tbl_role_privileges_audit_log
(REVTYPE, REV, role_id, privilege_id)
  SELECT
    0      AS REVTYPE,
    @revId AS REV,
    role_id,
    privilege_id
  FROM oauth2jwt.tbl_role_privileges
  WHERE role_id = 1 AND privilege_id = 3;

INSERT INTO oauth2jwt.tbl_role_privileges
(role_id, privilege_id) VALUES
  (1, 4);
INSERT INTO oauth2jwt_audit_log.revinfo
(REVTSTMP) VALUES
  (UNIX_TIMESTAMP(@currtime));
SET @revId = last_insert_id();
INSERT INTO oauth2jwt_audit_log.tbl_role_privileges_audit_log
(REVTYPE, REV, role_id, privilege_id)
  SELECT
    0      AS REVTYPE,
    @revId AS REV,
    role_id,
    privilege_id
  FROM oauth2jwt.tbl_role_privileges
  WHERE role_id = 1 AND privilege_id = 4;

INSERT INTO oauth2jwt.tbl_role_privileges
(role_id, privilege_id) VALUES
  (1, 5);
INSERT INTO oauth2jwt_audit_log.revinfo
(REVTSTMP) VALUES
  (UNIX_TIMESTAMP(@currtime));
SET @revId = last_insert_id();
INSERT INTO oauth2jwt_audit_log.tbl_role_privileges_audit_log
(REVTYPE, REV, role_id, privilege_id)
  SELECT
    0      AS REVTYPE,
    @revId AS REV,
    role_id,
    privilege_id
  FROM oauth2jwt.tbl_role_privileges
  WHERE role_id = 1 AND privilege_id = 5;

INSERT INTO oauth2jwt.tbl_role_privileges
(role_id, privilege_id) VALUES
  (1, 6);
INSERT INTO oauth2jwt_audit_log.revinfo
(REVTSTMP) VALUES
  (UNIX_TIMESTAMP(@currtime));
SET @revId = last_insert_id();
INSERT INTO oauth2jwt_audit_log.tbl_role_privileges_audit_log
(REVTYPE, REV, role_id, privilege_id)
  SELECT
    0      AS REVTYPE,
    @revId AS REV,
    role_id,
    privilege_id
  FROM oauth2jwt.tbl_role_privileges
  WHERE role_id = 1 AND privilege_id = 6;

INSERT INTO oauth2jwt.tbl_role_privileges
(role_id, privilege_id) VALUES
  (1, 7);
INSERT INTO oauth2jwt_audit_log.revinfo
(REVTSTMP) VALUES
  (UNIX_TIMESTAMP(@currtime));
SET @revId = last_insert_id();
INSERT INTO oauth2jwt_audit_log.tbl_role_privileges_audit_log
(REVTYPE, REV, role_id, privilege_id)
  SELECT
    0      AS REVTYPE,
    @revId AS REV,
    role_id,
    privilege_id
  FROM oauth2jwt.tbl_role_privileges
  WHERE role_id = 1 AND privilege_id = 7;

INSERT INTO oauth2jwt.tbl_role_privileges
(role_id, privilege_id) VALUES
  (1, 8);
INSERT INTO oauth2jwt_audit_log.revinfo
(REVTSTMP) VALUES
  (UNIX_TIMESTAMP(@currtime));
SET @revId = last_insert_id();
INSERT INTO oauth2jwt_audit_log.tbl_role_privileges_audit_log
(REVTYPE, REV, role_id, privilege_id)
  SELECT
    0      AS REVTYPE,
    @revId AS REV,
    role_id,
    privilege_id
  FROM oauth2jwt.tbl_role_privileges
  WHERE role_id = 1 AND privilege_id = 8;

INSERT INTO oauth2jwt.tbl_role_privileges
(role_id, privilege_id) VALUES
  (1, 9);
INSERT INTO oauth2jwt_audit_log.revinfo
(REVTSTMP) VALUES
  (UNIX_TIMESTAMP(@currtime));
SET @revId = last_insert_id();
INSERT INTO oauth2jwt_audit_log.tbl_role_privileges_audit_log
(REVTYPE, REV, role_id, privilege_id)
  SELECT
    0      AS REVTYPE,
    @revId AS REV,
    role_id,
    privilege_id
  FROM oauth2jwt.tbl_role_privileges
  WHERE role_id = 1 AND privilege_id = 9;

INSERT INTO oauth2jwt.tbl_role_privileges
(role_id, privilege_id) VALUES
  (1, 10);
INSERT INTO oauth2jwt_audit_log.revinfo
(REVTSTMP) VALUES
  (UNIX_TIMESTAMP(@currtime));
SET @revId = last_insert_id();
INSERT INTO oauth2jwt_audit_log.tbl_role_privileges_audit_log
(REVTYPE, REV, role_id, privilege_id)
  SELECT
    0      AS REVTYPE,
    @revId AS REV,
    role_id,
    privilege_id
  FROM oauth2jwt.tbl_role_privileges
  WHERE role_id = 1 AND privilege_id = 10;

INSERT INTO oauth2jwt.tbl_role_privileges
(role_id, privilege_id) VALUES
  (1, 11);
INSERT INTO oauth2jwt_audit_log.revinfo
(REVTSTMP) VALUES
  (UNIX_TIMESTAMP(@currtime));
SET @revId = last_insert_id();
INSERT INTO oauth2jwt_audit_log.tbl_role_privileges_audit_log
(REVTYPE, REV, role_id, privilege_id)
  SELECT
    0      AS REVTYPE,
    @revId AS REV,
    role_id,
    privilege_id
  FROM oauth2jwt.tbl_role_privileges
  WHERE role_id = 1 AND privilege_id = 11;

INSERT INTO oauth2jwt.tbl_role_privileges
(role_id, privilege_id) VALUES
  (1, 12);
INSERT INTO oauth2jwt_audit_log.revinfo
(REVTSTMP) VALUES
  (UNIX_TIMESTAMP(@currtime));
SET @revId = last_insert_id();
INSERT INTO oauth2jwt_audit_log.tbl_role_privileges_audit_log
(REVTYPE, REV, role_id, privilege_id)
  SELECT
    0      AS REVTYPE,
    @revId AS REV,
    role_id,
    privilege_id
  FROM oauth2jwt.tbl_role_privileges
  WHERE role_id = 1 AND privilege_id = 12;

-- ASSIGN USER TO ROLE
INSERT INTO oauth2jwt.tbl_user_roles
(user_id, role_id)
VALUES (1, 1);
INSERT INTO oauth2jwt_audit_log.revinfo
(REVTSTMP) VALUES
  (UNIX_TIMESTAMP(@currtime));
SET @revId = last_insert_id();
INSERT INTO oauth2jwt_audit_log.tbl_user_roles_audit_log
(REVTYPE, REV, user_id, role_id)
  SELECT
    0      AS REVTYPE,
    @revId AS REV,
    user_id,
    role_id
  FROM oauth2jwt.tbl_user_roles
  WHERE user_id = 1 AND role_id = 1;
