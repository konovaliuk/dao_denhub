package ua.kpi.pm_system.dao.interfaces;

import ua.kpi.pm_system.entities.User;

import java.sql.SQLException;

public interface IUserDao {

    User findById(long id) throws SQLException;
    User findByUsername(String username) throws SQLException;
    User create(String email, String username, String passwordhash, String firstname, String lastname, String bio) throws SQLException;
    User updateEmail(User user, String newEmail) throws SQLException;
    User updatePasswordHash(User user, String newPasswordHash) throws SQLException;
    User updateFirstName(User user, String newFirstName) throws SQLException;
    User updateLastName(User user, String newLastName) throws SQLException;
    User updateBio(User user, String newBio) throws SQLException;
    void delete(User user) throws SQLException;

}
