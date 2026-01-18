/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.UserDAO;
import model.User;
import util.PasswordUtil;

/**
 *
 * @author ASUS
 */
public class AuthService {
    
    private final UserDAO userDAO;
    private static User currentUser;
    
    public AuthService() {
        this.userDAO = new UserDAO();
    }
    
    public boolean login(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        
        if (user != null) {
            // Check password (assuming password in DB is hashed)
            // If you want to use plain text for testing, use: if (user.getPassword().equals(password))
            // But we already implemented PasswordUtil, so let's use it properly.
            // Note: If the user in DB currently has plain text password, this might fail unless we update DB.
            // For safety in this prompt, I will assume we should check hash.
            // But for real world new systems, I should probably check if it matches plain text OR hash if I am migrating.
            // Let's stick to hash check.
            
            if (PasswordUtil.checkPassword(password, user.getPassword())) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }
    
    public void logout() {
        currentUser = null;
    }
    
    public static User getCurrentUser() {
        return currentUser;
    }
    
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}
