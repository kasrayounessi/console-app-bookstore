package com.company;

import java.sql.SQLException;
import java.util.List;

public interface BookDao {
    //int findNumberOfCategories() throws SQLException;
    List<String> listCategories() throws SQLException;
    boolean categoryExists(String category) throws SQLException;
    List<String> retrieveThisCategoryBooks(String category) throws SQLException;
    boolean bookExists(String bookTitle) throws SQLException;
    Book retrieveBook(String bookTitle) throws SQLException;

}
