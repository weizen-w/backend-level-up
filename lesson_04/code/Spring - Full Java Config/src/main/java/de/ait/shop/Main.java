package de.ait.shop;

import de.ait.shop.config.ApplicationConfig;
import de.ait.shop.models.User;
import de.ait.shop.repositories.UsersRepository;
import de.ait.shop.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 9/25/2023
 * Spring
 *
 * @author Marsel Sidikov (AIT TR)
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        UsersService usersService = context.getBean(UsersService.class);
        UsersRepository usersRepository = context.getBean("usersRepositoryJdbcTemplateImpl", UsersRepository.class);

        User user = User.builder().email("newUser@gmail.com").password("qwerty007").build();

        System.out.println(user);

        usersRepository.save(user);

        System.out.println(user);
    }
}
