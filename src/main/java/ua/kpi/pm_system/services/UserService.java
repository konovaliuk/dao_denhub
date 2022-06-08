package ua.kpi.pm_system.services;

import ua.kpi.pm_system.dao.DaoFactory;
import ua.kpi.pm_system.dao.interfaces.IUserDao;
import ua.kpi.pm_system.entities.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserService {
    private final IUserDao userDao = DaoFactory.getUserDao();

    public boolean signUpUser(String email, String username,  String password, String firstName, String lastName, String bio) throws SQLException, NoSuchAlgorithmException {
        User currentUser = userDao.findByUsername(username);
        if (currentUser != null) {
            return false;
        }
        userDao.create(email, username, mdEncrypt(password), firstName, lastName, bio);
        return true;
    }

    private String mdEncrypt(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        StringBuilder hashedPassword = new StringBuilder(new BigInteger(1, md.digest(password.getBytes())).toString(16));
        while (hashedPassword.length() < 32) {
            hashedPassword.insert(0, "0");
        }
        return hashedPassword.toString();
    }

}
