package org.pfe.ameni.repositories;

import org.pfe.ameni.models.Produit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface ProduitRepository extends MongoRepository<Produit, String> {

 
}
