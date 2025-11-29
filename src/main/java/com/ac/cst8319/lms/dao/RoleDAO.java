package com.ac.cst8319.lms.dao;

import com.ac.cst8319.lms.model.Role;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for Role entity.
 */
public interface RoleDAO {

    /**
     * Find a role by its ID.
     * @param roleId the role ID
     * @return Optional containing the role if found, empty otherwise
     */
    Optional<Role> findById(int roleId);

    /**
     * Find a role by its name.
     * @param name the role name
     * @return Optional containing the role if found, empty otherwise
     */
    Optional<Role> findByName(String name);

    /**
     * Find all roles in the system.
     * @return List of all roles
     */
    List<Role> findAll();
}
