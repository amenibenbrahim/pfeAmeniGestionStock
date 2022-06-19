package org.pfe.ameni.controllers;

import java.util.List;

import org.pfe.ameni.models.Notification;
import org.pfe.ameni.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class NotifController {

    @Autowired
    private NotificationRepository notificationRepository;


    
    
    
    @ResponseBody // The response payload for this request will be rendered in JSON, not HTML
    @RequestMapping( value = "/notif/stream",  produces="application/json")
    public List<Notification> notif () {
           
        List <Notification> notif_list = notificationRepository.findAll();   
         System.out.println(notif_list);

        return notif_list;
   
    }



    



    
}
