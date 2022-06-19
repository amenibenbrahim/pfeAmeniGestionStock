package org.pfe.ameni.services;



import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.pfe.ameni.models.Notification;
import org.pfe.ameni.models.Produit;
import org.pfe.ameni.repositories.NotificationRepository;
import org.pfe.ameni.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class NotificationService {


    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    
    

    @Transactional
    @Scheduled(fixedRate = 30000) // every 30 seconds
    public void NotifStock() throws NoSuchElementException, InterruptedException {

        notificationRepository.deleteAll();
        

        final List <Produit>  verif_stock = produitRepository.findAll();
       
        int verif = 0;
        String msg = null;
        
        for (Produit p : verif_stock) {

            verif = p.getQte();
            msg = p.getNomProd();

            if ( verif > 10 ) 
            {
                System.out.println(msg + "  stockQte : " + verif);
            } 
            else 
    		{
                System.out.println(msg + " Repture  " + verif);
                Notification not = new Notification();
                Date date = new Date();
                not.setDate_notif(date);
                not.setNom_prod(msg);
                not.setMsg_notif(" Repture de STOCK  / Quantité = " + verif);
                notificationRepository.save(not);
    		}
            System.out.println("############################################");
        
        }

        verif_stock.clear();
       
       
       
      

    }




}
    	
    	







    

