package org.pfe.ameni.repositories;

import org.pfe.ameni.models.Categorie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategorieRepository extends MongoRepository<Categorie,String>{
    

    
}

