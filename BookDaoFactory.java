package com.company;

public class BookDaoFactory {
    private static BookDao bookDao;

    private BookDaoFactory(){}

    public static BookDao getBookDao(){
        if(bookDao==null)
            bookDao = new BookDaoImpl();
        return bookDao;
    }
}
