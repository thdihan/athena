package mypack.project;

import java.sql.*;

public class DbUtilities {

    //connection function for database
    private Connection connectToDB(String dbName, String user, String pass) {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbName, user, pass);
            if (conn != null) {
                System.out.println("Connection Established");
            }
            else{
                System.out.println("Connection Failed");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }

    public void initiateUserTable() throws SQLException {
        Statement statement=null;
//        ResultSet resultSet=null;
        String dropQuery="drop table if exists users";
        String createTableQuery="create table users(\n" +
                "\temail text,\n" +
                "\tpassword text,\n" +
                "\ttype varchar(10),\n" +
                "\tconstraint pk_users primary key (email)\n" +
                ");";
        String userQuery1="insert into users values ('shakun650@gmail.com', 'tukasl', 's')";
        String userQuery2="insert into users values ('hasan123@gmail.com', 'tukas', 's')";
        String userQuery3="insert into users values ('jamal234@gmail.com', 'tuka', 't')";
        try{
            Connection connection=connectToDB("projectDb", "postgres", "tukasl");
            statement=connection.createStatement();

            statement.executeUpdate(dropQuery);
            statement.executeUpdate(createTableQuery);

            statement.executeUpdate(userQuery1);
            statement.executeUpdate(userQuery2);
            statement.executeUpdate(userQuery3);
            statement.close();
        } catch (Exception e) {
            System.out.println("Error creating user table");
            throw new RuntimeException(e);
        }
    }

    //this function queries the userinput and checks the password with database
    public boolean loginNow(String user, String pass) throws SQLException{
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        String query="select * from users where email=? and password=?";
        try {
            Connection connection=connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,user);
            preparedStatement.setString(2,pass);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else {
                return false;
            }
        }catch (Exception e){
            System.out.println(e);
            System.out.println("Exception thrown in login");
            return false;
        }
        finally {
            preparedStatement.close();
            resultSet.close();
        }
    }



}
