package com.mssecurity.mssecurity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.mssecurity.mssecurity.models.Role;
import com.mssecurity.mssecurity.repositories.RoleRepository;

@CrossOrigin
@RestController
@RequestMapping("api/roles")
public class RolesController {
    @Autowired
    private RoleRepository theRoleRepository;

    @GetMapping("") // es como si fuera e metodo GET
    public List<Role> index() {
        // repository ayuda a conectar a la basedata, findAll() lista todo lo relacionado con usuarios
        return this.theRoleRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Role store(@RequestBody Role newRole) {
        return this.theRoleRepository.save(newRole);
    }

    @GetMapping("{id}")
    public Role show(@PathVariable String id) {
        Role theRole = this.theRoleRepository.findById(id).orElse(null);
        return theRole;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{id}")
    public Role update(@PathVariable String id, @RequestBody Role theNewRole) {

        Role theActualRole = this.theRoleRepository.findById(id).orElse(null);

        if (theActualRole != null) {
            theActualRole.setName(theNewRole.getName());
            theActualRole.setDescription(theNewRole.getDescription());
            return this.theRoleRepository.save(theActualRole);
        } else {
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void destroy(@PathVariable String id){
        Role theRole = this.theRoleRepository.findById(id).orElse(null);
        if (theRole != null) {
            this.theRoleRepository.delete(theRole);
        }
    }
}
