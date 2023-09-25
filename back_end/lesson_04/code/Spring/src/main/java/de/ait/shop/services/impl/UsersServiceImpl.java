package de.ait.shop.services.impl;

import de.ait.shop.models.User;
import de.ait.shop.repositories.UsersRepository;
import de.ait.shop.services.UsersService;

import java.util.List;

/**
 * 8/23/2023
 * Introduction
 *
 * @author Marsel Sidikov (AIT TR)
 */
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository; // зависимость на хранилище пользователей

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public User addUser(String email, String password) { // метод добавления пользователя

        User existedUser = usersRepository.findOneByEmail(email); // находим пользователя по email

        if (existedUser != null) { // если такой пользователь уже есть
            throw new IllegalArgumentException("Пользователь с таким email уже есть");
        }

        User user = User.builder()
                .email(email)
                .password(password)
                .build();

        usersRepository.save(user); // сохраняем пользователя в хранилище

        return user; // возвращаем пользователя как результат
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll(); // никакой дополнительной логики нет, просто запрашиваем у репозитория
    }

    @Override
    public void updateUser(Long id, String newEmail, String newPassword) {
        // сначала нужно получить этого пользователя по его id

        // вариант примитивный - воспользоваться уже существующим методом findAll
        List<User> users = usersRepository.findAll(); // получаем всех пользователей из базы

        // находим в этом списке нужного нам пользователя
        User userForUpdate = null;

        // пробегаем весь список
        for (User user : users) {
            // если у пользователя из списка id совпал с тем, который мы хотим обновить
            if (user.getId().equals(id)) {
                // запоминаем этого пользователя
                userForUpdate = user;
                // останавливаем цикл
                break;
            }
        }

        // если пользователя так и не нашли, нужно выбросить ошибку
        if (userForUpdate == null) {
            throw new IllegalArgumentException("User with id <" + id + "> not found");
        }

        // обновить данные в файле
        usersRepository.update(userForUpdate); // просто передаем обновленного пользователя в репозиторий

    }
}
