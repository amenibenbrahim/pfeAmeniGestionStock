package org.pfe.ameni.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "fournisseur")
@Data @NoArgsConstructor @AllArgsConstructor
public class Fournisseur {
    
    
    @Id
    private String id;
  
    private String matricule_fisc;
    private String fullname;
    private String adresse;
    private int num_phone;



}
