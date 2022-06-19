package org.pfe.ameni;

import org.pfe.ameni.models.Role;
import org.pfe.ameni.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AmeniApplication implements CommandLineRunner{


	@Override
	public void run(String... args) throws Exception {
		System.out.println("==========It s working...==========");

	}
	public static void main(String[] args) {
		SpringApplication.run(AmeniApplication.class, args);
	}



	@Bean
    CommandLineRunner init(RoleRepository roleRepository) {

        return args -> {

            Role adminRole = roleRepository.findByRole("ADMIN");
            if (adminRole == null) {
                Role newAdminRole = new Role();
                newAdminRole.setRole("ADMIN");
                roleRepository.save(newAdminRole);
            }

            Role Caissier = roleRepository.findByRole("CAISSIER");
            if (Caissier == null) {
                Role newUserRole = new Role();
                newUserRole.setRole("CAISSIER");
                roleRepository.save(newUserRole);
            }

            Role ChefStockRole = roleRepository.findByRole("CHEF");
            if (ChefStockRole == null) {
                Role newUserRole = new Role();
                newUserRole.setRole("CHEF");
                roleRepository.save(newUserRole);
            }
        };

    }

}
