package org.pfe.ameni.repositories;

import org.pfe.ameni.models.Fournisseur;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FournisseurRepository extends MongoRepository<Fournisseur, String> {

 
}

    

