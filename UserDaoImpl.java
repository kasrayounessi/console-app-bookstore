package com.company;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class UserDaoImpl implements UserDao {

    Connection connection;

    public UserDaoImpl() {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public void BuildAccountForUser(String username, String password) throws SQLException {
        String sql = "insert into registered_users (username, password) values (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        int count = preparedStatement.executeUpdate();
        if(count > 0)
            System.out.println("Dear " + username + ", you have successfully built your account.");
        else
            System.out.println("Oops, something went wrong.");
    }

    @Override
    public List<String> getUsernameCredentials(String username) throws SQLException {
        List<String> credentials = new ArrayList<>();
        String query = "select * from registered_users WHERE username = '"+username+"'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next()){
            credentials.add(String.valueOf(resultSet.getInt(1)));
            credentials.add(resultSet.getString(2));
            credentials.add(resultSet.getString(3));
        }
        return credentials;
    }


}
