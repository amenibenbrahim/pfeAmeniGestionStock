package org.pfe.ameni.controllers;


import java.util.Iterator;
import java.util.List;

import org.pfe.ameni.models.Commande;

import org.pfe.ameni.models.User;
import org.pfe.ameni.repositories.CommandeRepository;
import org.pfe.ameni.repositories.FournisseurRepository;
import org.pfe.ameni.repositories.NotificationRepository;
import org.pfe.ameni.repositories.ProduitRepository;
import org.pfe.ameni.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class AuthController {

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView createNewUser(User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("signup");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("login");

        }
        return modelAndView;
    }

    @RequestMapping(value = {"/dashboard","/"}, method = RequestMethod.GET)
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        

        modelAndView.addObject("notif_list", notificationRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));


        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("fullName",  user.getFullname());
        modelAndView.addObject("role",  auth.getAuthorities().toString());

     
        
        modelAndView.addObject("fr",fournisseurRepository.count());
        modelAndView.addObject("pr",produitRepository.count());
        modelAndView.addObject("cm",commandeRepository.count());

        List <Commande> commande = commandeRepository.findAll();
        double total = 0;
        Iterator<Commande> it = commande.iterator(); 
        while(it.hasNext()){
            total = total + it.next().getTotal() ;
        }
    
        
    
        modelAndView.addObject("total",total);
        


        
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("dashboard");

        return modelAndView;
    }

    @GetMapping("/403")
    public String error403(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        
        model.addAttribute("role",  auth.getAuthorities());
        model.addAttribute("fullName",  user.getFullname());
        model.addAttribute("notif_list", notificationRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));

        return "/403";
    }

    @GetMapping("/repture-stock")
    public String repture(final Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        
        model.addAttribute("role",  auth.getAuthorities());
        model.addAttribute("fullName",  user.getFullname());
        model.addAttribute("notif_list", notificationRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));

        return "repture-stock";
    }

    

}


