package com.mssecurity.mssecurity.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mssecurity.mssecurity.models.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
    
}
