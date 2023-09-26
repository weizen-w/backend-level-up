package de.ait.shop;

import de.ait.shop.config.ApplicationConfig;
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

        usersService.addUser("userFromService@gmail.com", "alphabeth1");
    }
}
