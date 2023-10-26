package com.mssecurity.mssecurity.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mssecurity.mssecurity.models.Permission;
import com.mssecurity.mssecurity.models.User;
import com.mssecurity.mssecurity.repositories.UserRepository;
import com.mssecurity.mssecurity.services.EncryptionService;
import com.mssecurity.mssecurity.services.JWService;
import com.mssecurity.mssecurity.services.ValidatorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("api/public/security")
// metodos login, logout, reset password
public class SecurityController {

    @Autowired
    private UserRepository theUserRepository;

    @Autowired
    private JWService jwtService;

    @Autowired
    private EncryptionService encryptionService;
    
    @Autowired
    private ValidatorService validatorService;

    // Método login
    @PostMapping("login")
    public String login(@RequestBody User theUser, final HttpServletResponse response) throws IOException {
        String token = "";
        User actualUser = this.theUserRepository.getUserByEmail(theUser.getEmail());
        if (actualUser != null
                && actualUser.getPassword().equals(encryptionService.convertirSHA256(theUser.getPassword()))) {
            // Generar token
            token = jwtService.generateToken(actualUser);
        } else {
            // manejar el problema
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return token;
    }

    @GetMapping("token-validation")
    public User tokenValidation(final HttpServletRequest request) {
        User theUser = this.validatorService.getUser(request);
        return theUser;
    }

    @PostMapping("permissions-validation")
    public boolean permissionsValidation(final HttpServletRequest request, @RequestBody Permission thePermission) {
        boolean success = this.validatorService.validationRolePermission(request, thePermission.getUrl(),
                thePermission.getMethod());
        return success;
    }
}
