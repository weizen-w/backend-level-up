package de.ait.shop;

import de.ait.shop.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 9/25/2023
 * Spring
 *
 * @author Marsel Sidikov (AIT TR)
 */
public class Main2 {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        UsersService usersService = context.getBean(UsersService.class);

        usersService.addUser("userFromService@gmail.com", "alphabeth1");
    }
}
