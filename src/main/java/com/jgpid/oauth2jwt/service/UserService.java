package com.jgpid.oauth2jwt.service;

import com.jgpid.oauth2jwt.exception.ItemNotFoundException;
import com.jgpid.oauth2jwt.exception.ItemNotFoundStatusOKException;
import com.jgpid.oauth2jwt.exception.PreConditionFailedException;
import com.jgpid.oauth2jwt.model.domain.Role;
import com.jgpid.oauth2jwt.model.domain.User;
import com.jgpid.oauth2jwt.model.form.UserForm;
import com.jgpid.oauth2jwt.repository.RoleRepository;
import com.jgpid.oauth2jwt.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by hendr on 02/07/2017.
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User create(UserForm userForm) {
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setUsername(userForm.getUsername());
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        user.setName(userForm.getName());
        user.setEnabled(!userForm.isDisabled());

        if (userForm.getRoles() != null && !userForm.getRoles().isEmpty()) {
            List<Role> roles = roleRepository.findAll(userForm.getRoles());
            if (roles == null || roles.isEmpty()) {
                logger.error("Cannot create User. Role with id(s)=" + Arrays.toString(userForm.getRoles().toArray())
                        + " was not found");
                throw new EmptyResultDataAccessException(
                        "Roles not found" + Arrays.toString(userForm.getRoles().toArray()), userForm.getRoles().size());
            }
            user.setRoles(new HashSet<>(roles));

            //
            if (roles.size() != userForm.getRoles().size()) {
                List<Long> missingRoleIds = getMissingRoleIds(userForm.getRoles(), roles);
                logger.warn("Not all roles was added to the User since roles was not found: { username: "
                        + userForm.getUsername() + ", rolesNotFound: [" + Arrays.toString(missingRoleIds.toArray()) + "] }");
            }
        }
        return userRepository.save(user);
    }

    private List<Long> getMissingRoleIds(List<Long> roleIds, List<Role> roles) {
        List<Long> items = new ArrayList<Long>();
        for (int i = 0; i < roleIds.size(); i++) {
            boolean found = false;
            for (Role role : roles) {
                if (role.getId() == roleIds.get(i)) {
                    found = true;
                    break;
                }
            }
            if (!found)
                items.add(roleIds.get(i));
        }
        return items;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User update(Long userId, UserForm userForm) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            logger.error("Cannot update User. User with id=" + userId + " was not found");
            throw new EmptyResultDataAccessException("User with id=" + userId + " was not found", 1);
        }
        if (user.getVersion() != userForm.getVersion()) {
            logger.warn("Precondition failed. Expected version=" + user.getVersion() + " but get version="
                    + userForm.getVersion() + " instead.");
            PreConditionFailedException preConditionFailedException = new PreConditionFailedException(
                    "Invalid Version");
            preConditionFailedException.setLastModifiedBy(user.getLastModifiedBy());
            preConditionFailedException.setUpdatedDate(user.getUpdatedDate());
            preConditionFailedException.addError("version", "INVALID");
            throw preConditionFailedException;
        }

        user.setEmail(userForm.getEmail());
        user.setUsername(userForm.getUsername());
        if (userForm.getPassword() != null && !userForm.getPassword().isEmpty())
            user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        user.setName(userForm.getName());
        user.setEnabled(!userForm.isDisabled());

        if (userForm.getRoles() != null && !userForm.getRoles().isEmpty()) {
            List<Role> roles = roleRepository.findAll(userForm.getRoles());
            if (roles == null || roles.isEmpty()) {
                logger.error("Cannot update User. Role with id(s)=" + Arrays.toString(userForm.getRoles().toArray())
                        + " was not found");
                throw new EmptyResultDataAccessException(
                        "Roles not found " + Arrays.toString(userForm.getRoles().toArray()),
                        userForm.getRoles().size());
            }
            user.mergeRoles(roles);

            //
            if (roles.size() != userForm.getRoles().size()) {
                List<Long> missingRoleIds = getMissingRoleIds(userForm.getRoles(), roles);
                logger.warn("Not all  roles was added to the User since roles was not found: { userId: " + userId
                        + ", username: " + user.getUsername() + ", rolesNotFound: [" + Arrays.toString(missingRoleIds.toArray())
                        + "] }");
            }
        } else
            user.setRoles(null);
        return userRepository.save(user);
    }

    public Page<User> getUsers(int page, int size, String sortBy, String sortDirection) {
        if (sortBy != null && !sortBy.isEmpty()) {
            try {
                return userRepository
                        .findDistinctUsers(new PageRequest(page, size, Direction.fromString(sortDirection), sortBy));
            } catch (IllegalArgumentException ex) {
                logger.warn("Invalid sortDirection: " + sortDirection + ". Exception message's: " + ex.getMessage());
                return userRepository.findDistinctUsers(new PageRequest(page, size, Direction.ASC, sortBy));
            }
        } else {
            return userRepository.findAll(new PageRequest(page, size));
        }
    }

    public Page<User> searchUsers(int page, int size, String sortBy, String sortDirection, String keyword) {
        logger.debug("Search user by " + keyword);
            try {
                if (keyword.equalsIgnoreCase("enable") || keyword.equalsIgnoreCase("enabled"))
                    return userRepository.searchAny(
                            new PageRequest(page, size, Direction.fromString(sortDirection), sortBy), keyword,
                            Boolean.TRUE);
                else if (keyword.equalsIgnoreCase("disable") || keyword.equalsIgnoreCase("disabled"))
                    return userRepository.searchAny(
                            new PageRequest(page, size, Direction.fromString(sortDirection), sortBy), keyword,
                            Boolean.FALSE);
                return userRepository
                        .searchAny(new PageRequest(page, size, Direction.fromString(sortDirection), sortBy), keyword);

            } catch (IllegalArgumentException ex) {
                logger.warn("Invalid sortDirection: " + sortDirection + ". Exception message's: " + ex.getMessage());
                return userRepository.searchAny(new PageRequest(page, size, Direction.ASC, sortBy), keyword);
            }
    }

    public User getUser(Long id) {
        User user = userRepository.findOne(id);
        if (user == null) {
            logger.warn("Get User with id=" + id + " was not found");
            throw new EmptyResultDataAccessException("User not found", 1);
        }
        return user;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = ItemNotFoundStatusOKException.class)
    public void deleteUsers(List<Long> userIds) {
        List<User> users = userRepository.findAll(userIds);

        if (users != null && !users.isEmpty()) {
            // userRepository.deleteInBatch(users);
            for (User user : users) {
                userRepository.delete(user);
            }
        }

        if (users == null || users.isEmpty()) {
            logger.error(
                    "Cannot delete User. User with id(s)=" + Arrays.toString(userIds.toArray()) + " was not found.");
            throw new ItemNotFoundException("Item not found for " + Arrays.toString(userIds.toArray()));
        } else if (users != null && users.size() != userIds.size()) {
            List<Long> items = new ArrayList<Long>();
            for (int i = 0; i < userIds.size(); i++) {
                boolean found = false;
                for (User user : users) {
                    if (user.getId() == userIds.get(i)) {
                        found = true;
                        break;
                    }
                }
                if (!found)
                    items.add(userIds.get(i));
            }
            logger.warn("Partial delete Users occured, missing Users: " + Arrays.toString(items.toArray()));
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("itemsNotFound", items);
            throw new ItemNotFoundStatusOKException(response);
        }
    }

}
