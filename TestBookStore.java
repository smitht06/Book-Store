import java.sql.*;
import java.util.Scanner;

public class TestBookStore {
    public static void main (String []args) {
        //loop to keep program running
        while(true) {
            //create bookstore object
            BookStore book = new BookStore();
            //execute menu methods
            book.menu();
        }
    }
}
