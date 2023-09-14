package com.mssecurity.mssecurity.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mssecurity.mssecurity.models.User;

// este es el lugar donde se hacen metodos especializados/personalizados
public interface UserRepository extends MongoRepository<User, String> {
    // esto es un query/consulta en mongo
    // busca el email que se manda por parametro
    // @Query("{'email': ?0}, {'user': ?1}")
    @Query("{'email': ?0}")
    public User getUserByEmail(String email);
}
