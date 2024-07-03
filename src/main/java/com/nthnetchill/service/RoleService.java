package com.nthnetchill.service;

import com.nthnetchill.model.Role;
import com.nthnetchill.model.User;
import com.nthnetchill.repository.RoleRepository;
import com.nthnetchill.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    @Transactional
    public Role getOneRole(Long roleId){
        Optional<Role> roleOptional = roleRepository.findById(roleId);
        if (roleOptional.isPresent()) {
            return roleOptional.get();
        } else {
            throw new RuntimeException("Role not found with id " + roleId);
        }
    }

    @Transactional
    public Role createRole(Role role){
        String roleName = "ROLE_" + role.getName().toUpperCase();
        Role newRole = new Role(roleName);
        if (roleRepository.existsByName(roleName)) {
            throw new RuntimeException(role.getName() + " role already exists");
        }
        return roleRepository.save(newRole);
    }

    @Transactional
    public void deleteRole(Long roleId) {
        this.removeAllUsersFromRole(roleId);
        roleRepository.deleteById(roleId);
    }

    @Transactional
    public User assignRoleToUser(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if (user.isPresent() && user.get().getRoles().contains(role.get())) {
            throw new RuntimeException(
                    " is already assigned to the" + role.get().getName() + " role");
        }
        if (role.isPresent()) {
            role.get().assignRoleToUser(user.get());
            roleRepository.save(role.get());
        }
        return user.get();
    }

    @Transactional
    public User removeUserFromRole(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent() && role.get().getUsers().contains(user.get())) {
            role.get().removeUserFromRole(user.get());
            roleRepository.save(role.get());
            return user.get();
        }
        throw new RuntimeException("User not found");
    }

    @Transactional
    public Role removeAllUsersFromRole(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        role.ifPresent(Role::removeAllUsersFromRole);
        return roleRepository.save(role.get());
    }
}
