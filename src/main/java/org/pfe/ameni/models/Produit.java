package org.pfe.ameni.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "produit")
@Data @NoArgsConstructor @AllArgsConstructor
public class Produit {
    
    
    @Id
    private String id;

    private String nomProd;

    
    
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateLimiteProd;
  
    
    private double prixProd;
    private int Qte;
    private String categoryProd;
    private String img_url_prod;



}
