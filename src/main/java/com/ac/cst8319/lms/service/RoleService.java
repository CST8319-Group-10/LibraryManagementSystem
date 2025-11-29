package com.ac.cst8319.lms.service;

import com.ac.cst8319.lms.dao.RoleDAO;
import com.ac.cst8319.lms.dao.impl.RoleDAOImpl;
import com.ac.cst8319.lms.model.Role;

import java.util.List;
import java.util.Optional;

/**
 * Service for role management operations.
 */
public class RoleService {

    private final RoleDAO roleDAO;

    public RoleService() {
        this.roleDAO = new RoleDAOImpl();
    }

    /**
     * Get all roles in the system.
     * @return list of all roles
     */
    public List<Role> getAllRoles() {
        return roleDAO.findAll();
    }

    /**
     * Get a role by ID.
     * @param roleId role ID
     * @return Optional containing the role if found
     */
    public Optional<Role> getRoleById(int roleId) {
        return roleDAO.findById(roleId);
    }

    /**
     * Get a role by name.
     * @param name role name
     * @return Optional containing the role if found
     */
    public Optional<Role> getRoleByName(String name) {
        return roleDAO.findByName(name);
    }
}
