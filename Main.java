package com.company;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        UserDao userDao = UserDaoFactory.getUserDao() ;
        BookDao bookDao = BookDaoFactory.getBookDao();
        Scanner scanner = new Scanner(System.in);
        final int STARCOUNTER = 40;
        final int SPACECOUNTER = 5;
        final int MAXIMUMERRORS = 5;
        int indicator;
        boolean menuFlag = false;
        String username;
        String password;
        List<String> credentials;


        for(int i = 1; i <= STARCOUNTER; i++) System.out.print("*");
        System.out.println();
        System.out.println("Welcome to Revature bookstore");
        System.out.println("Press 1 if you already have an account");
        System.out.println("Press 2 if you are a new customer");

        switch(scanner.next()){
            case "1":
                System.out.print("username: ");
                username = scanner.next();
                credentials = userDao.getUsernameCredentials(username);

                if(credentials.isEmpty()){
                    System.out.println("Such username does not exist");
                    break;
                }

                boolean userEnteredCorrectPassword = false;
                int numberOfTimesUserPassedWrongPassword = 0;
                while(!(userEnteredCorrectPassword)){
                    if(numberOfTimesUserPassedWrongPassword >= MAXIMUMERRORS) {
                        System.out.println("You have inserted the password wrong too many times. Exiting...");
                        return;
                    }
                    System.out.print("Password: ");
                    password = scanner.next();
                    if(password.equals(credentials.get(2))){
                        menuFlag = true;
                        userEnteredCorrectPassword = true;
                    }
                    else{
                        numberOfTimesUserPassedWrongPassword++;
                        System.out.println("You have " + (MAXIMUMERRORS-numberOfTimesUserPassedWrongPassword)+ " attempts remaining!");
                    }

                }
                break;
            case "2":
                System.out.print("Enter your desired username: ");
                username = scanner.next();
                credentials = userDao.getUsernameCredentials(username);
                if(credentials.isEmpty()){
                    System.out.print("Enter your Password: ");
                    password = scanner.next();
                    userDao.BuildAccountForUser(username, password);
                    menuFlag = true;
                } else{
                    System.out.println("This username already exists! Exiting...");
                    return;
                }
                break;
            default:
                System.out.println("Please enter a valid number");

        }

        //while(menuFlag){
            System.out.println("Welcome!");
            for(int i = 1; i <= STARCOUNTER; i++) System.out.print("*");
            System.out.println();
            List<String> categories =  bookDao.listCategories();
            System.out.println("Genres: ");
            for(int i = 0; i < categories.size(); i++){
                System.out.println(categories.get(i));
            }
            System.out.println("type the genres to view its books");
            String category = scanner.next();
            if(!(bookDao.categoryExists(category))){
                System.out.println("the category you selected does not exist");
                return;
            }
            List<String> bookTitle = bookDao.retrieveThisCategoryBooks(category);
            System.out.println("There are " + bookTitle.size() + " in the " + category + " section");
            for(String b:bookTitle){
                System.out.println(b);
            }
            System.out.print("Type the name of the book: ");
            String bookName = scanner.next();
            if(!(bookDao.bookExists(bookName))){
                System.out.println("the category you selected does not exist");
                return;
            }
            Book retrievedBook = bookDao.retrieveBook(bookName);
            System.out.println(retrievedBook);




    }
}
