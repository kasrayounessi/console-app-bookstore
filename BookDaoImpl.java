package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao{

    Connection connection;
    public BookDaoImpl(){
        this.connection = ConnectionFactory.getConnection();
    }
    /*
    @Override
    public int findNumberOfCategories() throws SQLException {
        String sql = "select count(DISTINCT category) from books";
        Statement statement = connection.createStatement() ;
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()){
            return resultSet.getInt(1);
        } else{
            return -1;
        }
    }

     */

    public List<String> listCategories() throws SQLException {
        List<String> categories = new ArrayList<>();
        String sql = "select DISTINCT category from books";
        Statement statement = connection.createStatement() ;
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            categories.add(resultSet.getString(1));
        }
        return categories;
    }

    @Override
    public boolean categoryExists(String category) throws SQLException {
        String sql = "select * from books where category = '" + category +"'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next())
            return true;
        else
            return false;
    }

    @Override
    public List<String> retrieveThisCategoryBooks(String category) throws SQLException {
        List<String> books = new ArrayList<>();
        String sql = "select title from books WHERE category = '"+category+"'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            books.add(resultSet.getString(1));
        }
        return books;
    }

    @Override
    public boolean bookExists(String bookTitle) throws SQLException {
        String sql = "select * from books where title = '" + bookTitle +"'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next())
            return true;
        else
            return false;
    }

    @Override
    public Book retrieveBook(String bookTitle) throws SQLException {
        String sql = "select * from books WHERE title = '"+bookTitle+"' LIMIT 1";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()){
            Book retrievedBook = new Book(resultSet.getDouble(5), resultSet.getString(2), resultSet.getString(3), resultSet.getString(7));
            retrievedBook.setBookId(resultSet.getInt(1));
            return retrievedBook;
        } else{
            return null;
        }

    }
}



