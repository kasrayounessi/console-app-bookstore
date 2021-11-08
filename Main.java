package com.company;

import java.sql.SQLException;
import java.util.ArrayList;
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
        final double TAX_PERCENTAGE = 0.1;
        int indicator;
        boolean menuFlag = true;
        boolean firstFlag = true;
        String username;
        String password;
        List<String> credentials;
        List<Book> booksToPurchase = new ArrayList<>();


        while(firstFlag) {
            for (int i = 1; i <= STARCOUNTER; i++) System.out.print("*");
            System.out.println();
            System.out.println("Welcome to Revature bookstore");
            System.out.println("Press 1 if you already have an account");
            System.out.println("Press 2 if you are a new customer");
            System.out.println("Press 'q' to exit");
            for (int i = 1; i <= STARCOUNTER; i++) System.out.print("*");
            System.out.println();
            switch (scanner.next()) {
                case "1":
                    for (int i = 1; i <= STARCOUNTER; i++) System.out.print("-");
                    System.out.println();
                    System.out.print("username: ");
                    username = scanner.next();
                    credentials = userDao.getUsernameCredentials(username);

                    if (credentials.isEmpty()) {
                        System.out.println("Such username does not exist. Returning to main menu!");
                        menuFlag = false;
                        break;
                    }

                    boolean userEnteredCorrectPassword = false;
                    int numberOfTimesUserPassedWrongPassword = 0;
                    while (!(userEnteredCorrectPassword)) {
                        if (numberOfTimesUserPassedWrongPassword >= MAXIMUMERRORS) {
                            System.out.println("You have inserted the password wrong too many times. Returning to main menu!");
                            menuFlag = false;
                        }
                        System.out.print("Password: ");
                        password = scanner.next();
                        if (password.equals(credentials.get(2))) {
                            userEnteredCorrectPassword = true;
                            menuFlag = true;
                        } else {
                            numberOfTimesUserPassedWrongPassword++;
                            menuFlag = false;
                            System.out.println("You have " + (MAXIMUMERRORS - numberOfTimesUserPassedWrongPassword) + " attempts remaining!");
                        }

                    }
                    break;
                case "2":
                    System.out.print("Enter your desired username: ");
                    username = scanner.next();
                    credentials = userDao.getUsernameCredentials(username);
                    if (credentials.isEmpty()) {
                        System.out.print("Enter your Password: ");
                        password = scanner.next();
                        userDao.BuildAccountForUser(username, password);
                        menuFlag = true;

                    } else {
                        System.out.println("This username already exists! Returning to the main menu.");
                        menuFlag = false;
                        //return;
                    }
                    break;
                case "q":
                    System.out.println("Exiting, thank you!");
                    firstFlag = false;
                    menuFlag = false;
                    break;
                default:
                    System.out.println("Please enter a valid input");

            }

            for (int i = 1; i <= SPACECOUNTER; i++) System.out.println();
            while (menuFlag) {

                for (int i = 1; i <= STARCOUNTER; i++) System.out.print("*");
                System.out.println();
                System.out.println("Enter 1 to surf the bookstore");
                System.out.println("Enter 2 to go to your shopping cart");
                System.out.println("Enter 3 to return");
                for (int i = 1; i <= STARCOUNTER; i++) System.out.print("*");
                System.out.println();
                switch (scanner.next()) {
                    case "1":
                        List<String> categories = bookDao.listCategories();
                        boolean genreFlag = true;
                        boolean continueWithGenre = true;
                        String category = null;
                        while(genreFlag){
                            for (int i = 1; i <= SPACECOUNTER; i++) System.out.println();
                            System.out.println("Genres: ");
                            for (int i = 1; i <= STARCOUNTER; i++) System.out.print("-");
                            System.out.println();
                            for (int i = 0; i < categories.size(); i++) {
                                System.out.println(categories.get(i));
                            }
                            for (int i = 1; i <= STARCOUNTER; i++) System.out.print("-");
                            System.out.println();
                            System.out.println("type your desired genre to view its books");
                            System.out.println("type 'q' to return");
                            for (int i = 1; i <= STARCOUNTER; i++) System.out.print("*");
                            System.out.println();
                            category = scanner.next();
                            if(category.equals("q")) {
                                genreFlag = false;
                                continueWithGenre = false;
                                for(int i = 1; i <= SPACECOUNTER;i++) System.out.println();
                            }
                            else if (!(bookDao.categoryExists(category))) {
                                System.out.println("the category you selected does not exist");
                            } else{
                                genreFlag = false;
                            }
                        }
                        if(continueWithGenre){
                            List<String> bookTitle = bookDao.retrieveThisCategoryBooks(category);
                            for (int i = 1; i <= SPACECOUNTER; i++) System.out.println();
                            boolean bookFlag = true;
                            boolean continueWithBook = true;
                            Book retrievedBook = null;

                            while(bookFlag){
                                for (int i = 1; i <= STARCOUNTER; i++) System.out.print("*");
                                System.out.println();
                                if (bookTitle.size() == 1)
                                    System.out.println("There is " + bookTitle.size() + " book in the " + category + " section");
                                else
                                    System.out.println("There are " + bookTitle.size() + " books in the " + category + " section");
                                for (int i = 1; i <= STARCOUNTER; i++) System.out.print("-");
                                System.out.println();

                                for (String b : bookTitle) {
                                    System.out.println(b);
                                }

                                for (int i = 1; i <= STARCOUNTER; i++) System.out.print("-");
                                System.out.println();
                                System.out.println("Type the name of the book: ");
                                for (int i = 1; i <= STARCOUNTER; i++) System.out.print("*");
                                System.out.println();
                                String bookName = scanner.next();
                                if(bookName.equals("q")){
                                    continueWithBook = false;
                                    bookFlag = false;
                                } else{
                                    bookName = bookName + " " + scanner.next();
                                    if(!(bookDao.bookExists(bookName, category))){
                                        System.out.println("the book you selected does not exist");
                                    } else{
                                        retrievedBook = bookDao.retrieveBook(bookName);
                                        bookFlag = false;
                                    }

                                }
                                for (int i = 1; i <= SPACECOUNTER; i++) System.out.println();

                            }

                            if(continueWithBook){
                                for (int i = 1; i <= SPACECOUNTER; i++) System.out.println();
                                for (int i = 1; i <= STARCOUNTER; i++) System.out.print("*");
                                System.out.println();
                                System.out.println(retrievedBook.getTitle());
                                for (int i = 1; i <= STARCOUNTER; i++) System.out.print("-");
                                System.out.println();
                                System.out.println("Category: " + retrievedBook.getCategory());
                                System.out.println("Author: " + retrievedBook.getAuthor());
                                System.out.println("Price: $" + retrievedBook.getPrice());
                                for (int i = 1; i <= STARCOUNTER; i++) System.out.print("-");
                                System.out.println();
                                System.out.println("Do you wish to buy this book?");
                                System.out.println("Enter 1  to buy");
                                System.out.println("Enter 2 to return");
                                for (int i = 1; i <= STARCOUNTER; i++) System.out.print("*");
                                System.out.println();
                                switch (scanner.next()) {
                                    case "1":
                                        for (int i = 1; i <= SPACECOUNTER; i++) System.out.println();
                                        System.out.println("Added to the cart");
                                        booksToPurchase.add(retrievedBook);
                                        break;
                                    case "2":
                                        for (int i = 1; i <= SPACECOUNTER; i++) System.out.println();
                                        break;
                                    default:
                                        System.out.println("Enter a valid number");
                                }
                            }
                        }
                        break;
                    case "2":
                        double totalPrice = 0;

                        if (booksToPurchase.isEmpty()) {
                            System.out.println("You do not have any books in your cart.");
                            for (int i = 1; i <= SPACECOUNTER; i++) System.out.println();
                            break;
                        }
                        for (int i = 1; i <= SPACECOUNTER; i++) System.out.println();
                        for (int i = 1; i <= STARCOUNTER; i++) System.out.print("*");
                        System.out.println();


                        for (Book book : booksToPurchase) {
                            System.out.println(book.getTitle() + "               price: $" + book.getPrice());
                            for (int i = 1; i <= STARCOUNTER; i++) System.out.print("-");
                            System.out.println();
                            totalPrice += book.getPrice();
                        }
                        double taxAmount = totalPrice * TAX_PERCENTAGE;
                        System.out.println("Tax: $" + taxAmount);
                        System.out.println("Total: $" + (totalPrice + taxAmount));
                        for (int i = 1; i <= STARCOUNTER; i++) System.out.print("-");
                        System.out.println();
                        System.out.println("Press any button to check out");
                        System.out.println("Press 'q' to return");
                        for (int i = 1; i <= STARCOUNTER; i++) System.out.print("*");
                        System.out.println();
                        switch (scanner.next()) {
                            case "q":
                                for (int i = 1; i <= SPACECOUNTER; i++) System.out.println();
                                break;
                            default:
                                System.out.println("Checking out...");
                                booksToPurchase.clear();
                                for (int i = 1; i <= SPACECOUNTER; i++) System.out.println();
                                break;
                        }

                        break;
                    case "3":
                        System.out.println("Returning to main menu");
                        booksToPurchase.clear();
                        for(int i = 1; i <= SPACECOUNTER; i++) System.out.println();

                        menuFlag = false;
                        break;
                    default:
                        System.out.println("Press 1, 2, or 3");


                }


            }
        }







    }
}

