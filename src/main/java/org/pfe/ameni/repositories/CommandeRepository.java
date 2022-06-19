package org.pfe.ameni.repositories;

import org.pfe.ameni.models.Commande;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepository extends MongoRepository<Commande,String>{
    

    
}
