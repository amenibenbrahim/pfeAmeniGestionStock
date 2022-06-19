package org.pfe.ameni.repositories;

import org.pfe.ameni.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
    User findByEmail(String email);
    
}
