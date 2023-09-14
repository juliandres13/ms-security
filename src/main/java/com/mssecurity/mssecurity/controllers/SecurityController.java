package com.mssecurity.mssecurity.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mssecurity.mssecurity.models.User;
import com.mssecurity.mssecurity.repositories.UserRepository;
import com.mssecurity.mssecurity.services.EncryptionService;
import com.mssecurity.mssecurity.services.JWService;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("security")
//metodos login, logout, reset password
public class SecurityController {

    @Autowired
    private UserRepository theUserRepository;
    
    //no creamos el objeto, sino que lo hace spring boot. CUIDADO AQUI
    @Autowired
    private JWService jwtService;

    @Autowired
    private EncryptionService encryptionService;

    @PostMapping("login")
    public String login(@RequestBody User theUser, final HttpServletResponse response) throws IOException {
        String token = "";
        User actualUser = this.theUserRepository.getUserByEmail(theUser.getEmail());
        if (actualUser != null && actualUser.getPassword().equals(encryptionService.convertirSHA256(theUser.getPassword()))) {
            //se genera el token
            // JWService myJWT = new JWService(); NO porque hay que recordar que el repositorio se encarga de crear el objeto
            token = jwtService.generateToken(actualUser);
        } else {
            //maneja el error 401 (el user no está autorizado)
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return token;
    }
}
