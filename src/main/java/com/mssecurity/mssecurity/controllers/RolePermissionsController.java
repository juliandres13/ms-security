package com.mssecurity.mssecurity.controllers;

import com.mssecurity.mssecurity.models.Permission;
import com.mssecurity.mssecurity.models.Role;
import com.mssecurity.mssecurity.models.RolePermission;
import com.mssecurity.mssecurity.repositories.PermissionRepository;
import com.mssecurity.mssecurity.repositories.RolePermissionRepository;
import com.mssecurity.mssecurity.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/role-permission")
public class RolePermissionsController {
    @Autowired
    private RolePermissionRepository theRolePermissionRepository;

    @Autowired
    private RoleRepository theRoleRepository;

    @Autowired
    private PermissionRepository thePermissionRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("role/{role_id}/permission/{permission_id}")
    public RolePermission store(@PathVariable String role_id, @PathVariable String permission_id) {
        Role theRole = this.theRoleRepository.findById(role_id).orElse(null);
        Permission thePermission = this.thePermissionRepository.findById(permission_id).orElse(null);

        if ((thePermission != null) && (theRole != null)) {
            RolePermission newRolePermission = new RolePermission();
            newRolePermission.setRole(theRole);
            newRolePermission.setPermission(thePermission);
            return this.theRolePermissionRepository.save(newRolePermission);
        } else {
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void destroy(@PathVariable String id){
        RolePermission theRolePermission = this.theRolePermissionRepository.findById(id).orElse(null);
        if (theRolePermission != null) {
            this.theRolePermissionRepository.delete(theRolePermission);
        }
    }

}
