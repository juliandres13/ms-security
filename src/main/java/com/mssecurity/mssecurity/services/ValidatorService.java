package com.mssecurity.mssecurity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mssecurity.mssecurity.models.Permission;
import com.mssecurity.mssecurity.models.Role;
import com.mssecurity.mssecurity.models.RolePermission;
import com.mssecurity.mssecurity.models.User;
import com.mssecurity.mssecurity.repositories.PermissionRepository;
import com.mssecurity.mssecurity.repositories.RolePermissionRepository;
import com.mssecurity.mssecurity.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ValidatorService {

   @Autowired
   private JWService jwtService;

   @Autowired
   private PermissionRepository thePermissionRepository;

   @Autowired
   private UserRepository theUserRepository;

   @Autowired
   private RolePermissionRepository theRolePermissionRepository;

   private static final String BEARER_PREFIX = "Bearer ";

   public boolean validationRolePermission(HttpServletRequest request, String url, String method) {
      boolean success = false;
      User theUser = this.getUser(request);
      if (theUser != null) {
         Role theRole = theUser.getRole();
         System.out.println("Antes URL " + url + " metodo " + method);
         url = url.replaceAll("[0-9a-fA-F]{24}", "?");
         System.out.println("URL " + url + " metodo " + method);
         Permission thePermission = this.thePermissionRepository.getPermission(url, method);
         if (theRole != null && thePermission != null) {
            System.out.println("Rol " + theRole.getName() + " Permission " + thePermission.getUrl());
            RolePermission theRolePermission = this.theRolePermissionRepository.getRolePermission(theRole.get_id(),
                  thePermission.get_id());
            if (theRolePermission != null) {
               success = true;
            }
         } else {
            success = false;
         }
      }
      return success;
   }

   public User getUser(final HttpServletRequest request) {
      User theUser = null;
      String authorizationHeader = request.getHeader("Authorization");
      System.out.println("Header " + authorizationHeader);
      if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
         String token = authorizationHeader.substring(BEARER_PREFIX.length());
         System.out.println("Bearer Token: " + token);
         User theUserFromToken = jwtService.getUserFromToken(token);
         if (theUserFromToken != null) {
            theUser = this.theUserRepository.findById(theUserFromToken.get_id())
                  .orElse(null);
            theUser.setPassword("");
         }
      }
      return theUser;
   }
}
