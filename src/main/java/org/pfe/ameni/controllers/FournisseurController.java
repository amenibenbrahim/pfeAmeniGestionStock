package org.pfe.ameni.controllers;

import javax.validation.Valid;

import org.pfe.ameni.models.Fournisseur;
import org.pfe.ameni.models.User;
import org.pfe.ameni.repositories.FournisseurRepository;
import org.pfe.ameni.repositories.NotificationRepository;
import org.pfe.ameni.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class FournisseurController {
    
    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/fournisseurs")
    public String getAllFournisseurs(final Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        
        model.addAttribute("role",  auth.getAuthorities());
        model.addAttribute("fullName",  user.getFullname());
        model.addAttribute("notif_list", notificationRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));

        model.addAttribute("fournisseurs", fournisseurRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        model.addAttribute("fr", new Fournisseur());
        
        return "fournisseurs";
    }

    @RequestMapping("/deletefournisseur")
    public String deleteFournisseurById(@RequestParam String id) {
        
        fournisseurRepository.deleteById(id);

        return "redirect:/fournisseurs";
   }


   @PostMapping("/savefr")
   public String saveFournisseur(@ModelAttribute Fournisseur fr){
        
        fournisseurRepository.save(fr);

        return "redirect:/fournisseurs";

   }           
           
   @RequestMapping(value = "/update-fr", method = RequestMethod.GET)
   public String UpdateVehicle (@Valid Fournisseur fr){
       
    fournisseurRepository.save(fr);

       return "redirect:/fournisseurs";
   }                           

                     





}
