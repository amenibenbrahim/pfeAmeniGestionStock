package org.pfe.ameni.models;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    
@Document(collection = "prodComs")
@Data @NoArgsConstructor @AllArgsConstructor
public class PostProd {
    
    
    private String nomProdCom;
    
    private double prixProdCom;
        
    private int qteProdCom;
        
        
       
        
}
    
