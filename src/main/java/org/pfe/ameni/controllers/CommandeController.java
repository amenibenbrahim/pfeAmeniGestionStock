package org.pfe.ameni.controllers;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.pfe.ameni.models.Commande;
import org.pfe.ameni.models.PostProd;
import org.pfe.ameni.models.Produit;
import org.pfe.ameni.models.User;
import org.pfe.ameni.repositories.CommandeRepository;
import org.pfe.ameni.repositories.NotificationRepository;
import org.pfe.ameni.repositories.ProduitRepository;
import org.pfe.ameni.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CommandeController {

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private NotificationRepository notificationRepository;

 

    @GetMapping("/commandes")
    public String getAllCom(final Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        
        model.addAttribute("role",  auth.getAuthorities());
        model.addAttribute("fullName",  user.getFullname());

        model.addAttribute("commande", commandeRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        model.addAttribute("produit", produitRepository.findAll());
        model.addAttribute("notif_list", notificationRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        
        return "commandes";
    }

    @RequestMapping("/deletecommande")
    public String deleteComById(@RequestParam String id) {
        
        commandeRepository.deleteById(id);

        return "redirect:/commandes";
    }


    @GetMapping("/gestion-caisse")
    public String getCaisse(final Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        
        model.addAttribute("role",  auth.getAuthorities());
        model.addAttribute("fullName",  user.getFullname());
        model.addAttribute("notif_list", notificationRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));

        model.addAttribute("new_commande", new Commande());
        model.addAttribute("produit", produitRepository.findAll());
        
        return "gestion-caisse";
    }




    @RequestMapping(value = "/ajout-com", method = RequestMethod.GET)
    public String addCmd(@RequestBody @ModelAttribute Commande new_commande,
    @RequestParam String nom_client) {

        List <PostProd> list =  new_commande.getProds();
      
        
        
        double px = 0;
        double wrap = 0;
        int b = 0;

        

    
    Iterator<PostProd> it = list.iterator(); 
    while(it.hasNext())
    {
        b = it.next().getQteProdCom();
        
        if(b <= 0){
            it.remove();
        }
        
    }
    

        for (int i = 0; i < list.size() ; i++) {
                wrap = list.get(i).getPrixProdCom() * list.get(i).getQteProdCom() ;
                px = wrap + px ;

                Optional<Produit> prd = produitRepository.findById(list.get(i).getNomProdCom());
                int f = list.get(i).getQteProdCom();
                int m = prd.get().getQte();

                if(m < f)
                {
                    return "redirect:/repture-stock";
                   
                }
                else {
                    prd.get().setQte(m - f);
                    produitRepository.save(prd.get()); 
                    System.out.println(prd); 

                }
             
   
        }

       
        new_commande.setTotal(px);
        
        Date date = new Date();
        
        new_commande.setNom_client(nom_client);
        new_commande.setDate(date);
        commandeRepository.insert(new_commande);

        

        return "redirect:/commandes";

      
        

    }




     
    @RequestMapping("/view-facture/{id}")
    public String show(@PathVariable String id, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        
        model.addAttribute("role",  auth.getAuthorities());
        model.addAttribute("fullName",  user.getFullname());
        model.addAttribute("notif_list", notificationRepository.findAll());
        
        model.addAttribute("commande", commandeRepository.findById(id).get());
        model.addAttribute("produit", produitRepository.findAll());
        return "view-facture";
    }




    
}
