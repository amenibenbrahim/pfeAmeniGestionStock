package org.pfe.ameni.models;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "commande")
@Data @NoArgsConstructor @AllArgsConstructor
public class Commande {

    @Id
    private String id;
  
    private String nom_client;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private double Total;
    
    private List <PostProd> prods ;
    
}
