package com.mssecurity.mssecurity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.mssecurity.mssecurity.models.Role;
import com.mssecurity.mssecurity.models.User;
import com.mssecurity.mssecurity.repositories.RoleRepository;
import com.mssecurity.mssecurity.repositories.UserRepository;
import com.mssecurity.mssecurity.services.EncryptionService;

@CrossOrigin
@RestController // permitirá hacer crud
@RequestMapping("/users") // los metodos de la clase se activaran cuando el cliente utilice esta ruta

// en la basedata los datos estan planos, el frameworkse encarga de traer estos
// datos y forma el objeto

public class UsersController {
    
    @Autowired
    private UserRepository theUserRepository;
    
    @Autowired
    private RoleRepository theRoleRepository;

    @Autowired
    private EncryptionService encryptionService;

    @GetMapping("") // es como si fuera el metodo GET
    public List<User> index() {
        // repository ayuda a conectar a la basedata, findAll() lista todo lo
        // relacionado con usuarios
        return this.theUserRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping // " " " " POST'
    public User store(@RequestBody User newUser) {
        // lee el body, crea el obj casteandolo, le dice a repository que guarde el nuevo user, pero antes se encripta la contraseña
        newUser.setPassword(encryptionService.convertirSHA256(newUser.getPassword()));
        return this.theUserRepository.save(newUser); // lo retorna como un JSON
    }

    @GetMapping("{id}") // GET uno solo
    public User show(@PathVariable String id) {
        // findById es un metodo de la interfaz. findById lo busca por id, sino retorna
        // null
        User theUser = this.theUserRepository.findById(id).orElse(null);
        return theUser;
    }

    @PutMapping("{id}") // PUT
    public User update(@PathVariable String id, @RequestBody User theNewUser) {
        // findById es un metodo de la interfaz. findById lo busca por id, sino retorna
        // null
        User theActualUser = this.theUserRepository.findById(id).orElse(null);

        if (theActualUser != null) {
            theActualUser.setName(theNewUser.getName());
            theActualUser.setEmail(theNewUser.getEmail());
            theActualUser.setPassword(encryptionService.convertirSHA256(theNewUser.getPassword()));
            theActualUser.setRole(theNewUser.getRole());
            return this.theUserRepository.save(theActualUser);
        } else {
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void destroy(@PathVariable String id) {
        User theUser = this.theUserRepository.findById(id).orElse(null);
        if (theUser != null) {
            this.theUserRepository.delete(theUser);
        }
    }

    // método que crea una relación entre el usuario y el rol
    @PutMapping("{user_id}/role/{role_id}")
    public User matchUserRole(@PathVariable String user_id, @PathVariable String role_id) {
        User theActualUser = this.theUserRepository.findById(user_id).orElse(null);
        Role theActualRole = this.theRoleRepository.findById(role_id).orElse(null);

        if (theActualUser != null && theActualRole != null) {
            theActualUser.setRole(theActualRole);
            return this.theUserRepository.save(theActualUser); // lo retorna como un JSON
        } else {
            return null;
        }
    }

    @PutMapping("{user_id}/role")
    public User unMatchUserRole(@PathVariable String user_id) {
        User theActualUser = this.theUserRepository.findById(user_id).orElse(null);

        if (theActualUser != null) {
            theActualUser.setRole(null);
            return this.theUserRepository.save(theActualUser); // lo retorna como un JSON
        } else {
            return null;
        }
    }


}
