package com.smaaaak.Portfolio.controller;

import com.smaaaak.Portfolio.model.Role;
import com.smaaaak.Portfolio.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/role")
public class RoleController {

    private RoleService roleService ;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Role>> getAllRoles(){
        return new ResponseEntity<>(this.roleService.getAllRoles() , HttpStatus.OK) ;
    }

    @PostMapping("/add")
    public ResponseEntity<Role> createRole(@RequestBody Role newRole){
        return new ResponseEntity<>(this.roleService.addNewRole(newRole) , HttpStatus.CREATED) ;
    }

    @PutMapping("/update")
    public ResponseEntity<Role> updateRole(@RequestBody Role role){
        return new ResponseEntity<>(this.roleService.updateRole(role) , HttpStatus.OK) ;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable("id") Long idRole){
        this.roleService.deleteRole(idRole); ;
        return new ResponseEntity<>(HttpStatus.OK) ;
    }

    @GetMapping("/addRoleToUser/{username}/rolename")
    public ResponseEntity<?> addRoleToUser(@PathVariable("username") String username ,@PathVariable("rolename") String rolename ){
        this.roleService.addRoleToUser(username, rolename);
        return  new ResponseEntity(HttpStatus.OK) ;
    }



}
