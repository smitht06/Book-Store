import java.sql.*;
import java.util.Scanner;

//testing commit

public class BookStore {
    //create scanner objects
    Scanner scan2 = new Scanner(System.in);
    Scanner scan = new Scanner(System.in);
    Scanner scan3 = new Scanner(System.in);

    //menu to print out to the user
    void menu(){
        System.out.println("Please make a choice below: ");
        System.out.println("1. Enter book\n" +
                "2. Remove book \n" +
                "3. Update book\n" +
                "4. Search book\n" +
                "0. Exit ");
        int choice = scan2.nextInt();

        //switch statment to use method that the user selects
        switch(choice){
            case 1: enterBook();
            break;
            case 2: deleteBook();
            break;
            case 3: updateBook();
            break;
            case 4: searchBook();
            break;
            case 0: System.exit(0);
            break;
        }

    }

    //enter book method
    private void enterBook(){
        //access database
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/bookstore?useSSL=false", "myuser",
                        "password");
                Statement stmt = conn.createStatement();
        ){
            //output to the user and take input
            System.out.println("Enter all the information of the book you would like to add.");
            String value = scan.nextLine();
            String sqlEnterBook = "insert into bookstock "
                    + "values" + "("+ value + ")";
            //execute sql command
            int countInserted = stmt.executeUpdate(sqlEnterBook);
            System.out.println(countInserted + " records inserted.\n");
        }catch(SQLException e){
            e.printStackTrace();
        }
}
    //delete book method
    private void deleteBook(){
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/bookstore?useSSL=false", "myuser",
                        "password");
                Statement stmt = conn.createStatement();
        ){
            //take input from user and execute the sql command
            System.out.println("Enter the id of the book you want to delete: ");
            String primaryKey = scan.nextLine();
            String sqlDeletebook = "delete from bookstock where id = " +primaryKey;
            int countDeleted = stmt.executeUpdate(sqlDeletebook);
            System.out.println(countDeleted + "records deleted");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //update book method
    private void updateBook(){
        //access database
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/bookstore?useSSL=false", "myuser",
                        "password");
                Statement stmt = conn.createStatement();
        ){
            //take input from the user and execute sql command
            System.out.println("Enter the id of the book you need to update: ");
            String enterId = scan.nextLine();
            System.out.println("Enter the information you want to change: ");
            String enterInfo = scan3.nextLine();
            String sqlUpdateBook = "update bookstock set " +enterInfo+ " where id = " +enterId;
            int countUpdated = stmt.executeUpdate(sqlUpdateBook);
            System.out.println(countUpdated + " record updated");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //search book method
    private void searchBook(){
        //access databsse
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/bookstore?useSSL=false", "myuser",
                        "password");
                Statement stmt = conn.createStatement();
        ){
            //print out menu
            int choice= scan2.nextInt();
            String sqlSearchBook = "";
            String enterinfo;
            System.out.println("1. Search by title\n 2.Search by ID\n 3.Search by author");

            //switch statement to create alternate sql commands
            switch(choice){

                case 1:
                    System.out.println("Enter title: ");
                    enterinfo = scan.nextLine();
                    sqlSearchBook = "select * from bookstock where title = " +"'"+ enterinfo+ "'";
                    break;
                case 2:
                    System.out.println("Enter id: ");
                    enterinfo = scan2.nextLine();
                    sqlSearchBook = "select * from bookstock where id = " +enterinfo;
                    break;
                case 3:
                    System.out.println("Enter author: ");
                    enterinfo = scan3.nextLine();
                    sqlSearchBook = "select * from bookstock where author = " +"'"+ enterinfo+ "'";
                    break;
            }

            //execute sql statement
            ResultSet rset = stmt.executeQuery(sqlSearchBook);
            System.out.println("The records selected are: ");
            int rowCount=0;

            //print out records
            while(rset.next()) {
                String title = rset.getString("title");
                int qty = rset.getInt("qty");
                int id = rset.getInt("id");
                String author = rset.getString("author");
                System.out.println(id+", "+title + ", "+author+ "," + qty);
                ++rowCount;
            }
            //catch exception if database not found
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
