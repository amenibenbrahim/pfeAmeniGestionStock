package org.pfe.ameni.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document(collection = "categorie")
@Data @AllArgsConstructor @NoArgsConstructor
public class Categorie {
    
    @Id
    private String id;
    private String cat_name;

}
