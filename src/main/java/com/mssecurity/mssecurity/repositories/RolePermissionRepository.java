package com.mssecurity.mssecurity.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mssecurity.mssecurity.models.RolePermission;

public interface RolePermissionRepository extends MongoRepository<RolePermission, String> {
    @Query("{'role.$id': ObjectId(?0), 'permission.$id': ObjectId(?1)}")
    RolePermission getRolePermission(String roleId,String permissionId);
}
