package org.pfe.ameni.models;


import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Document(collection = "notifications")
@Data @AllArgsConstructor @NoArgsConstructor
public class Notification {

    @Id
    private String id;
    private String msg_notif;
    private String nom_prod;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date_notif;

    
}
