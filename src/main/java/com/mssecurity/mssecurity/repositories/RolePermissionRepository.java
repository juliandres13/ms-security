package com.mssecurity.mssecurity.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mssecurity.mssecurity.models.RolePermission;

public interface RolePermissionRepository extends MongoRepository<RolePermission, String> {
    
}
