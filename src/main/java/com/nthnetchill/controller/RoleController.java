package com.nthnetchill.controller;

import com.nthnetchill.model.Role;
import com.nthnetchill.model.User;
import com.nthnetchill.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getOneRole(@PathVariable Long roleId) {
        Role role = roleService.getOneRole(roleId);
        return ResponseEntity.ok(role);
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role createdRole = roleService.createRole(role);
        return ResponseEntity.ok(createdRole);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{roleId}/assign/{userId}")
    public ResponseEntity<User> assignRoleToUser(@PathVariable Long roleId, @PathVariable Long userId) {
        User user = roleService.assignRoleToUser(userId, roleId);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{roleId}/remove/{userId}")
    public ResponseEntity<User> removeUserFromRole(@PathVariable Long roleId, @PathVariable Long userId) {
        User user = roleService.removeUserFromRole(userId, roleId);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/remove-all-users-from-role/{roleId}")
    public Role removeAllUsersFromRole(@PathVariable("roleId") Long roleId) {
        return roleService.removeAllUsersFromRole(roleId);
    }
}
