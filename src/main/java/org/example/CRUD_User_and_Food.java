package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CRUD_User_and_Food {

    String createUser(User user, boolean isadmin){
        String message=null;
        int val=0;

        //connect to DB with driver
        final String URLDB = "jdbc:postgresql://localhost:5432/labusersfood";
        final String USERNAMEDB ="postgres";
        final String PASSWORDDB ="123456";

        try {
            Connection connection = DriverManager.getConnection(URLDB,USERNAMEDB,PASSWORDDB);

            //run SQL
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO USERS (username, password, isadmin) VALUES (?,?,?)");

            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBoolean(3,isadmin);

            val=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            String m = e.getMessage();
            if(m.contains("authentication failed for"))
                message="There must be a connection problem, our team is trying to fix it as soon as possible! Thanks for your patience!";
            else if(m.contains("ERROR: value too long for type character"))
                message="The username or password too long!! (max 50 characters) ";
            else message="General error, our team is trying to fix it as soon as possible. ";
        }

        System.out.println(message);
        System.out.println(val + " user successfully created ");
        return message;
    }

    public List<User> read(){
        List<User> listOfUsers = new ArrayList<>();

        //connect to DB
        final String URLDB = "jdbc:postgresql://localhost:5432/labusersfood";
        final String USERNAMEDB ="postgres";
        final String PASSWORDDB ="123456";

        try {
            Connection connection = DriverManager.getConnection(URLDB,USERNAMEDB,PASSWORDDB);

            //run SQL
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users order by id asc");

            while(resultSet.next()){
                String user = resultSet.getString("username").trim();
                String password = resultSet.getString("password").trim();
                boolean isadmin = resultSet.getBoolean("isadmin");
                int id = resultSet.getInt("id");
                User u = new User(user,password);
                u.setId(id);
                u.setIsadmin(isadmin);
                listOfUsers.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(listOfUsers);
        return listOfUsers;
    }

    String  update(User u) {
        String message=null;
        int val = 0;

            //connect to DB
        final String URLDB ="jdbc:postgresql://localhost:5432/labusersfood";
        final String USERNAMEDB ="postgres";
        final String PASSWORDDB ="123456";

        try {
            Connection conection = DriverManager.getConnection(URLDB,USERNAMEDB,PASSWORDDB);

            // run SQL
            PreparedStatement preparedStatement = conection.prepareStatement("update users set password = ? where username = ?");
            preparedStatement.setString(1,u.getPassword());
            preparedStatement.setString(2,u.getUsername());
            val = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            String m = e.getMessage();
            if(m.contains("authentication failed for"))
                message="There must be a connection problem, our team is trying to fix it as soon as possible! Thanks for your patience!";
            else if(m.contains("ERROR: value too long for type character"))
                message="The username or password you want to update is too long. Please try again using maximum 50 characters for each field!! ";
            else message="General error, our team is trying to fix it as soon as possible. ";
        }

        System.out.println(message);
        System.out.println(val + " user/s successfully updated");
        return message;

    }

    String delete(User u){
        String message = null;
        int val = 0;

        //connect to DB
        final String URLDB ="jdbc:postgresql://localhost:5432/labusersfood";
        final String USERNAMEDB ="postgres";
        final String PASSWORDDB ="123456";

        try {
            Connection connection = DriverManager.getConnection(URLDB,USERNAMEDB,PASSWORDDB);

            //run SQL
            PreparedStatement preparedStatement = connection.prepareStatement("delete from users where username = ?");

            preparedStatement.setString(1,u.getUsername());

            val = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            String m = e.getMessage();
            if(m.contains(" authentication failed for"))
                message= "There must be a connection problem, our team is trying to fix it as soon as possible! Thanks for your patience! ";
            else if(m.contains("violates foreign key constraint"))
                message= "You cannot delete this user because there is an food registered for him";
            else if(m.contains("is not present"))
                message="The user you want to delete does not exist! Please try again using an existing username";
            else message= "General error. Our team is trying to fix it!";
        }

        System.out.println(message);
        System.out.println(val + " user/s successfully deleted ");
        return message;
    }




    public String createFood(Food food) {
        String message=null;
        int val = 0;

        //connect to DB

        final String URLDB = "jdbc:postgresql://localhost:5432/labusersfood";
        final String USERNAMEDB ="postgres";
        final String PASSWORDDB ="123456";


        try {
            Connection connection = DriverManager.getConnection(URLDB,USERNAMEDB,PASSWORDDB);

            //run SQL

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO LOGGEDFOOD (foodname,iduser) VALUES (?,?)");
            preparedStatement.setString(1,food.getFoodname());
            preparedStatement.setLong(2,food.getIduser());

            val = preparedStatement.executeUpdate();

        } catch (SQLException | InputMismatchException e) {
            e.printStackTrace();
            String m = e.getMessage();
            if(m.contains(" authentication failed for"))
                message= "There must be a connection problem, our team is trying to fix it as soon as possible! Thanks for your patience! ";
            else if(m.contains("violates foreign key constraint"))
                message= "You cannot create this food, because the user for who you want to create the food, is not present in the table users";
            else if(m.contains("ERROR: value too long for type characte"))
                message= "The food name is too long. Please try again using maximum 80 characters. ";
            else message="General error, our team is trying to fix it";
        }
        System.out.println(message);
        System.out.println(val + " food/s successfully created");

        return message;
    }


    public List<Food> readFoodsOfAnUser() {
        List<Food> listOfFood = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        //connect to DB
        final String URLDB = "jdbc:postgresql://localhost:5432/labusersfood";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="123456";
        try {
            Connection connection = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

            //run SQL
            PreparedStatement preparedStatement = connection.prepareStatement("select  foodname, iduser from loggedfood,users where users.username=? and users.id=loggedfood.iduser");
            System.out.println("enter the name of the user you want to read the foods for: ");
            String username = scanner.nextLine();
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();


            while(resultSet.next()) {
                String foodname = resultSet.getString("foodname").trim();
                int iduser = resultSet.getInt("iduser");
                Food food = new Food(foodname,iduser);
                listOfFood.add(food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            String message=null;
            String m = e.getMessage();
            if(m.contains(" authentication failed for"))
                message= "There must be a connection problem, our team is trying to fix it as soon as possible! Thanks for your patience! ";
            else message="General error, our team is trying to fix it";
            System.out.println(message);
        }
        if(listOfFood.isEmpty())
            System.out.println("This user has no registered any food yet");
        else
        System.out.println( "The user you selected has the following foods: ");
        System.out.println(listOfFood);
        System.out.println("");
        return listOfFood;
    }


     String updateFood(Food food) {
        String message= null;
        int val=0;

        //connect to DB
        final String URLDB ="jdbc:postgresql://localhost:5432/labusersfood";
        final String USERNAMEDB ="postgres";
        final String PASSWORDDB ="123456";

        try {
            Connection conection = DriverManager.getConnection(URLDB,USERNAMEDB,PASSWORDDB);

            //run SQL
            PreparedStatement preparedStatement = conection.prepareStatement("update loggedfood set foodname = ? where iduser = ?");
            preparedStatement.setString(1,food.getFoodname());
            preparedStatement.setInt(2,food.getIduser());

            val = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            String m = e.getMessage();
            if(m.contains("authentication failed for"))
                message="There must be a connection problem. Our team is trying to fix it. Thanks for your patience. ";
            else if(m.contains("ERROR: value too long for type character"))
                message=" The food name you want to update is too long. Please try again using maximum 80 characters";
            else if(m.contains("does not exist")&&val == 0)
                message="You cannot update this food because the user for who you want to update the food, does not exist. ";
            else message="General error. Our team is trying to fix it.";

        }

        System.out.println(message);
        System.out.println(val + " food/s successfully updated ");
        return message;
    }


    String deleteFood(Food food){
        String message = null;
        int val = 0;

        //connect to DB
        final String URLDB ="jdbc:postgresql://localhost:5432/labusersfood";
        final String USERNAMEDB ="postgres";
        final String PASSWORDDB ="123456";

        try {
            Connection conn = DriverManager.getConnection(URLDB,USERNAMEDB,PASSWORDDB);

            //run SQL
            PreparedStatement preparedStatement = conn.prepareStatement("delete from loggedfood where foodname = ? and iduser=?");
            preparedStatement.setString(1,food.getFoodname());
            preparedStatement.setInt(2,food.getIduser());

            val = preparedStatement.executeUpdate();


            message=String.valueOf(val + " food/s succesfully deleted");
        } catch (SQLException e) {
            e.printStackTrace();
            String m = e.getMessage();
            if(m.contains(" authentication failed for"))
                message= "There must be a connection problem, our team is trying to fix it as soon as possible! Thanks for your patience! ";
            else if(m.contains("violates foreign key constraint"))
                message= "You cannot delete this food because there is an user registered for it";
            else  message="General error, our team is trying to fix it.";
        }

        if(val<=0) System.out.println("The food you want to delete does not exist or is not alocated for the presented iduser. \nPlease try again using an existing food or make sure the event is alocated for the iduser you presented.");
        if(message!=null) System.out.println(message);
        return message;
    }


    long login (User user)  {

        // -1 daca nu exista , si id-ul usaerului daca exista
        long id = -1;

        //connect to DB
        final String URLDB = "jdbc:postgresql://localhost:5432/labusersfood";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="123456";

        try {
            Connection connection = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

            //run SQL
            PreparedStatement preparedStatement = connection.prepareStatement("select id from users where username=? and password=? ");

            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();


               while(resultSet.next()) {
                    id = resultSet.getLong("id");
                    return id;
                }

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(id);
        return id;
    }

    boolean isAdmin (User user)  {

        // -1 daca nu exista , si id-ul usaerului daca exista


        boolean isAdmin=false;

        //connect to DB
        final String URLDB = "jdbc:postgresql://localhost:5432/labusersfood";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="123456";

        try {
            Connection connection = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

           //run SQL
            PreparedStatement preparedStatement = connection.prepareStatement("select isadmin from users where username=? and password=? ");

            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();


            while(resultSet.next()) {

                isAdmin = resultSet.getBoolean("isadmin");
                return isAdmin;

            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isAdmin;
    }

}
