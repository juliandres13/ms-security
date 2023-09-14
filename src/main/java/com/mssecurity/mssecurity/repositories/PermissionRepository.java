package com.mssecurity.mssecurity.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mssecurity.mssecurity.models.Permission;

public interface PermissionRepository extends MongoRepository<Permission, String> {
    
}
