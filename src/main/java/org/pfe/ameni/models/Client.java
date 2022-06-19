package org.pfe.ameni.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "client")
@Data @AllArgsConstructor @NoArgsConstructor
public class Client {

    @Id
    private String id;
    private String fullname;
    
    
}
