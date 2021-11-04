package com.company;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void BuildAccountForUser(String username, String password) throws SQLException;
    List<String> getUsernameCredentials(String username) throws SQLException;

}
