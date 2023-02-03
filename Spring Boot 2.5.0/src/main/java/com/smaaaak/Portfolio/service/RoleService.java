package com.smaaaak.Portfolio.service;


import com.smaaaak.Portfolio.model.Role;

import java.util.List;


public interface RoleService {

    List<Role> getAllRoles() ;

    Role addNewRole(Role newRole) ;

    Role updateRole(Role role) ;

    void deleteRole(Long idRole) ;

    void addRoleToUser(String username , String roleName) ;

}
