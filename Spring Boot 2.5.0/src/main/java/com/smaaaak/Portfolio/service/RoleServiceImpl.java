package com.smaaaak.Portfolio.service;

import com.smaaaak.Portfolio.Exception.ApiRequestException;
import com.smaaaak.Portfolio.model.Role;
import com.smaaaak.Portfolio.model.User;
import com.smaaaak.Portfolio.repository.RoleRepository;
import com.smaaaak.Portfolio.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository ;
    private UserRepository userRepository ;

    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return this.roleRepository.findAll();
    }

    @Override
    public Role addNewRole(Role newRole) {
        if(this.roleRepository.findRoleByRoleName(newRole.getRoleName()).isPresent()){
            throw new ApiRequestException("role already exists") ;
        }
        return this.roleRepository.save(newRole);
    }

    @Override
    public Role updateRole(Role role) {
        if(!this.roleRepository.findById(role.getIdRole()).isPresent()){
           throw new ApiRequestException("role doesn't exists") ;
        }
        if(this.roleRepository.findRoleByRoleName(role.getRoleName()).isPresent()){
           throw new ApiRequestException("role already exists") ;
        }
        return this.roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long idRole) {
        if(!this.roleRepository.findById(idRole).isPresent()){
            throw new ApiRequestException("role doesn't exists") ;
        }
        this.roleRepository.deleteById(idRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        Role role = this.roleRepository.findRoleByRoleName(roleName).get() ;
        User user = this.userRepository.findUserByAccount_Username(username).get()  ;

        if(role == null){
            throw new ApiRequestException("role doesn't exists") ;
        }
        if(user == null ){
            throw new ApiRequestException("user doesn't exists ") ;
        }

        user.getAccount().getRoles().add(role) ;
    }

}
