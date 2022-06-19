package org.pfe.ameni.controllers;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import org.pfe.ameni.models.Produit;
import org.pfe.ameni.models.User;
import org.pfe.ameni.repositories.CategorieRepository;
import org.pfe.ameni.repositories.NotificationRepository;
import org.pfe.ameni.repositories.ProduitRepository;
import org.pfe.ameni.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class ProduitController {
    
    @Autowired
    private ProduitRepository produitRepository ;
    
    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private NotificationRepository notificationRepository;
  
    


    @GetMapping("/produits")
    public String getAllProds(final Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        
        model.addAttribute("role",  auth.getAuthorities());
        model.addAttribute("fullName",  user.getFullname());

        model.addAttribute("produit", produitRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        
        model.addAttribute("cat",categorieRepository.findAll());
        model.addAttribute("notif_list", notificationRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));

        model.addAttribute("localDate", LocalDate.now());
        
        return "produits";
    }


    //     Supprimer un produit selon l'id et redirection vers le tableau produits
    @RequestMapping("/deleteproduit")
    public String deleteProdById(@RequestParam String id) {
        
    produitRepository.deleteById(id);

        return "redirect:/produits";
    }



    @RequestMapping("/saveproduit")
    public String saveProd(@RequestParam String nomProd,@RequestParam double prixProd,
                        @RequestParam String categoryProd,@RequestParam String img_url_prod,
                        @RequestParam Integer Qte,
                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateLimiteProd
                        ){
                        
            
    Produit p = new Produit();
                            
    p.setNomProd(nomProd);
    p.setPrixProd(prixProd);
    p.setQte(Qte);
    p.setCategoryProd(categoryProd);     
    p.setDateLimiteProd(dateLimiteProd);
    p.setImg_url_prod(img_url_prod);
    

    produitRepository.save(p); 

    return "redirect:/produits";
    }


                                
    @RequestMapping("/updateproduit")
    public String updateProd(@RequestParam String id,@RequestParam String nomProd,@RequestParam double prixProd,
                        @RequestParam Integer Qte,
                        @RequestParam String categoryProd,@RequestParam String img_url_prod,
                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateLimiteProd
                        ){
    
    Optional<Produit> p = produitRepository.findById(id);  

    p.get().setNomProd(nomProd);
    p.get().setPrixProd(prixProd);
    p.get().setQte(Qte);
    p.get().setCategoryProd(categoryProd);     
    p.get().setDateLimiteProd(dateLimiteProd);
    p.get().setImg_url_prod(img_url_prod);      

    produitRepository.save(p.get());                 
    return "redirect:/produits" ;                    
                    
                        
    }                     

                        
                        
          



    
}
