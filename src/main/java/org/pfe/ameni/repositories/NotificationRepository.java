package org.pfe.ameni.repositories;

import org.pfe.ameni.models.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotificationRepository extends MongoRepository <Notification,String> {
    


    
}

