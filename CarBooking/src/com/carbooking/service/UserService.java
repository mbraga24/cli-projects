package com.carbooking.service;

import com.carbooking.domain.model.User;
import com.carbooking.repository.UserDAO;

import java.util.List;

public class UserService {

    private UserDAO userDao;

    public UserService() {
        this.userDao = new UserDAO();
    }

    public void createUser(User user) {
        userDao.saveUser(user);
    }

    /**
     * Return all users.
     * @return users
     */
    public List<User> returnUsers() {
        return userDao.getAllUsers();
    }
}