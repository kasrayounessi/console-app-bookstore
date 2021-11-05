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
        double totalPrice = 0;

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
                        System.out.println("This username already exists! Exiting...");
                        menuFlag = false;
                        return;
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
            //System.out.println("Welcome!");
            while (menuFlag) {

                for (int i = 1; i <= STARCOUNTER; i++) System.out.print("*");
                System.out.println();
                System.out.println("Enter 1 to surf the bookstore");
                System.out.println("Enter 2 to go to your shopping cart");
                System.out.println("Enter 3 to exit ");
                for (int i = 1; i <= STARCOUNTER; i++) System.out.print("*");
                System.out.println();
                switch (scanner.next()) {
                    case "1":
                        List<String> categories = bookDao.listCategories();
                        for (int i = 1; i <= SPACECOUNTER; i++) System.out.println();
                        System.out.println("Genres: ");
                        for (int i = 1; i <= STARCOUNTER; i++) System.out.print("-");
                        System.out.println();
                        for (int i = 0; i < categories.size(); i++) {
                            System.out.println(categories.get(i));
                        }
                        for (int i = 1; i <= STARCOUNTER; i++) System.out.print("-");
                        System.out.println();
                        System.out.println("type the genres to view its books");
                        for (int i = 1; i <= STARCOUNTER; i++) System.out.print("*");
                        System.out.println();
                        String category = scanner.next();
                        if (!(bookDao.categoryExists(category))) {
                            System.out.println("the category you selected does not exist");
                            return;
                        }
                        List<String> bookTitle = bookDao.retrieveThisCategoryBooks(category);
                        for (int i = 1; i <= SPACECOUNTER; i++) System.out.println();

                        if (bookTitle.size() == 1)
                            System.out.println("There is " + bookTitle.size() + " book in the " + category + " section");
                        else
                            System.out.println("There are " + bookTitle.size() + " books in the " + category + " section");

                        for (String b : bookTitle) {
                            System.out.println(b);
                        }
                        System.out.print("Type the name of the book: ");
                        String bookName = scanner.next() + " " + scanner.next();

                        if (!(bookDao.bookExists(bookName))) {
                            System.out.println("the category you selected does not exist");
                            return;
                        }
                        Book retrievedBook = bookDao.retrieveBook(bookName);

                        System.out.println(retrievedBook.getTitle());
                        System.out.println("Category: " + retrievedBook.getCategory());
                        System.out.println("Author: " + retrievedBook.getAuthor());
                        System.out.println("Price: " + retrievedBook.getPrice());
                        System.out.println("Do you wish to buy this book?");
                        System.out.println("Enter 1  to buy");
                        System.out.println("Enter 2 to return");
                        switch (scanner.next()) {
                            case "1":
                                System.out.println("Added to the cart");
                                booksToPurchase.add(retrievedBook);
                                break;
                            case "2":
                                break;
                            default:
                                System.out.println("Enter a valid number");
                        }
                        break;
                    case "2":
                        if (booksToPurchase.isEmpty()) {
                            System.out.println("You do not have any books in your cart.");
                            break;
                        }

                        for (Book book : booksToPurchase) {
                            System.out.println(book.getTitle() + "               price: $" + book.getPrice());
                            totalPrice += book.getPrice();
                        }
                        double taxAmount = totalPrice * TAX_PERCENTAGE;
                        System.out.println("Tax: $" + taxAmount);
                        System.out.println("Total: $" + (totalPrice + taxAmount));
                        System.out.println("Press any button to check out");
                        System.out.println("Press 'q' to return");
                        switch (scanner.next()) {
                            case "q":
                                break;
                            default:
                                System.out.println("Checking out...");
                                menuFlag = false;
                                break;
                        }

                        break;
                    case "3":
                        System.out.println("Returning to main menu");
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
