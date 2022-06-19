package org.pfe.ameni.controllers;

import javax.validation.Valid;

import org.pfe.ameni.models.Categorie;
import org.pfe.ameni.models.User;
import org.pfe.ameni.repositories.CategorieRepository;
import org.pfe.ameni.repositories.NotificationRepository;

import org.pfe.ameni.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class CategorieController {

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/categories")
    public String getAllCat(final Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        
        model.addAttribute("role",  auth.getAuthorities());
        model.addAttribute("fullName",  user.getFullname());

        model.addAttribute("notif_list", notificationRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));

        model.addAttribute("cat", categorieRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        model.addAttribute("categorie", new Categorie());
        
        return "categories";
    }

    @RequestMapping("/deletecat")
    public String deleteCatById(@RequestParam String id) {
        
        categorieRepository.deleteById(id);

        return "redirect:/categories";
   }


   @PostMapping("/savecat")
   public String savecategorie(@Valid @ModelAttribute Categorie categorie){
        
    categorieRepository.save(categorie);

        return "redirect:/categories";

   }        


     @RequestMapping(value = "/updatecat", method = RequestMethod.GET)
     public String UpdateCat (@Valid Categorie categorie){
         
        categorieRepository.save(categorie);
 
         return "redirect:/categories";
    }
 
    
}
