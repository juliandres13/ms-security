package com.mssecurity.mssecurity.controllers;

import com.mssecurity.mssecurity.models.Permission;
import com.mssecurity.mssecurity.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/permissions")

public class PermissionsController {
    @Autowired
    private PermissionRepository thePermissionRepository;

    @GetMapping("")
    public List<Permission> index() {
        return this.thePermissionRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Permission store(@RequestBody Permission newPermission) {
        return this.thePermissionRepository.save(newPermission);
    }

    @GetMapping("{id}")
    public Permission show(@PathVariable String id) {
        return this.thePermissionRepository.findById(id).orElse(null);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void destroy(@PathVariable String id) {
        Permission thePermission = this.thePermissionRepository.findById(id).orElse(null);
        if (thePermission != null) {
            this.thePermissionRepository.delete(thePermission);
        }
    }
}
// public String convertirSHA256(String password) {
// MessageDigest md = null;
// try {
// md = MessageDigest.getInstance("SHA-256");
// }
// catch (NoSuchAlgorithmException e) {
// e.printStackTrace();
// return null;
// }
// byte[] hash = md.digest(password.getBytes());
// StringBuffer sb = new StringBuffer();
// for(byte b : hash) {
// sb.append(String.format("%02x", b));
// }
// return sb.toString();
// }