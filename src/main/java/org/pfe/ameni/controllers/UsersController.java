package org.pfe.ameni.controllers;



import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.pfe.ameni.models.Role;
import org.pfe.ameni.models.User;
import org.pfe.ameni.repositories.NotificationRepository;
import org.pfe.ameni.repositories.RoleRepository;
import org.pfe.ameni.repositories.UserRepository;
import org.pfe.ameni.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
public class UsersController {
    
    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired  
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private NotificationRepository notificationRepository;

    

    @GetMapping("/users")
    public String getAllUsers(final Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        
        model.addAttribute("role",  auth.getAuthorities());
        model.addAttribute("fullName",  user.getFullname());
        model.addAttribute("user", userRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        model.addAttribute("notif_list", notificationRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        
        
        
        return "users";
    }


    @RequestMapping("/deleteuser")
    public String deletePlatById(@RequestParam String id) {
        
        userRepository.deleteById(id);

        return "redirect:/users";
    }

    @RequestMapping("/saveuser")
    public String saveProd(@RequestParam String email,@RequestParam String role, 
    @RequestParam String fullname,@RequestParam String password,@RequestParam boolean enabled){
                    
                User userExists = userService.findUserByEmail(email);
                if (userExists != null) {
                    return "redirect:/users";
                            
                }
                else {

                    User user = new User();
                                        
                    user.setEmail(email);
                    user.setFullname(fullname);
                    user.setPassword(bCryptPasswordEncoder.encode(password));
                    user.setEnabled(enabled);
                    
                    Role userRole = roleRepository.findByRole(role);
                    user.setRoles(new HashSet<>(Arrays.asList(userRole)));
                    userRepository.save(user);

                    return "redirect:/users";

                }

    
    }

    @RequestMapping("/updateuser")
    public String updateProd(@RequestParam String id,@RequestParam String role,
    @RequestParam boolean enabled){

    Optional<User> user = userRepository.findById(id);  

    Role userRole = roleRepository.findByRole(role);
    user.get().setRoles(new HashSet<>(Arrays.asList(userRole)));
    user.get().setEnabled(enabled);
      

    userRepository.save(user.get());  

    return "redirect:/users" ;                    
                    
                        
    }         





}
