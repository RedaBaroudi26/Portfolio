package com.smaaaak.Portfolio.service;

import com.smaaaak.Portfolio.Exception.ApiRequestException;
import com.smaaaak.Portfolio.model.Role;
import com.smaaaak.Portfolio.model.User;
import com.smaaaak.Portfolio.repository.RoleRepository;
import com.smaaaak.Portfolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository ;
    private final UserRepository userRepository ;

    @Override
    public List<Role> getAllRoles() {
        return this.roleRepository.findAll();
    }

    @Override
    public Role addNewRole(Role newRole) {
        this.roleRepository.findRoleByRoleName(newRole.getRoleName()).ifPresent(
                (role) -> { throw new ApiRequestException("role already exists") ; }
        );
        return this.roleRepository.save(newRole);
    }

    @Override
    public Role updateRole(Role role) {
        this.roleRepository.findById(role.getIdRole()).orElseThrow(
                () -> new ApiRequestException("role doesn't exists")
        ) ;
        this.roleRepository.findRoleByRoleName(role.getRoleName()).ifPresent(
                (r) -> { throw new ApiRequestException("role already exists"); }
        );
        return this.roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long idRole) {
        this.roleRepository.findById(idRole).orElseThrow(
                () -> new ApiRequestException("role doesn't exists")
        );
        this.roleRepository.deleteById(idRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        Role role = this.roleRepository.findRoleByRoleName(roleName).orElseThrow(
                () -> new ApiRequestException("role doesn't exists")
        ) ;
        User user = this.userRepository.findUserByAccount_Username(username).orElseThrow(
                () -> new ApiRequestException("user doesn't exists ")
        )  ;
        user.getAccount().getRoles().add(role) ;
    }

}
