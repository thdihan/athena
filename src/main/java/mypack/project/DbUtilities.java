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
            } else {
                System.out.println("Connection Failed");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }

//    public void initiateUserTable() throws SQLException {
//        Statement statement = null;
////        ResultSet resultSet=null;
//        String dropQuery = "drop table if exists users";
//        String createTableQuery = "create table users(\n" +
//                "\temail text,\n" +
//                "\tpassword text,\n" +
//                "\ttype varchar(10),\n" +
//                "\tconstraint pk_users primary key (email)\n" +
//                ");";
//        String userQuery1 = "insert into users values ('shakun650@gmail.com', 'tukasl', 's')";
//        String userQuery2 = "insert into users values ('hasan123@gmail.com', 'tukas', 's')";
//        String userQuery3 = "insert into users values ('jamal234@gmail.com', 'tuka', 't')";
//        try {
//            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
//            statement = connection.createStatement();
//
//            statement.executeUpdate(dropQuery);
//            statement.executeUpdate(createTableQuery);
//
//            statement.executeUpdate(userQuery1);
//            statement.executeUpdate(userQuery2);
//            statement.executeUpdate(userQuery3);
//            statement.close();
//        } catch (Exception e) {
//            System.out.println("Error creating user table");
//            throw new RuntimeException(e);
//        }
//    }
    public void initiateTablesWithQuery() throws SQLException {

//         UsersTable
        String[] insertUser ={
                "insert into users values ('shakun650@gmail.com', 'tukasl', 's')",
                "insert into users values ('hasan123@gmail.com', 'tukas', 's')",
                "insert into users values ('jamal234@gmail.com', 'tuka', 't')",
                "insert into users values ('shuvro234@gmail.com', 'tukasl', 's')"
        };
        String tableName = "users";
        String tableQuery = "create table users(email text, password text, type varchar(10),constraint pk_users primary key (email) );";
        initiateAllTable(tableName,tableQuery,insertUser);

        // Student Table
        tableName = "student";
        tableQuery = "Create table Student(S_ID varchar(15) not null primary key,S_Name varchar(60) not null, S_email varchar(60) not null,Dept varchar(20),Semester varchar(10),DOB date,S_contact varchar(15) not null);";
        String[] insertStudent = {
                "insert into student values('200041111','Hasan','hasan123@gmail.com','CSE','4','2001-01-01','01711111111');"
        };
        initiateAllTable(tableName,tableQuery,insertStudent);

        // Teacher Table
        tableName = "teacher";
        tableQuery = "Create table Teacher(T_ID varchar(15) not null primary key,T_Name varchar(60) not null,T_email varchar(60) not null,Dept varchar(20),DOB date,T_contact varchar not null);";
        String[] insertTeacher = {
                "insert into teacher values('123456','Jamal','jamal234@gmail.com','CSE','1995-01-01','01711111111');"
        };
        initiateAllTable(tableName,tableQuery,insertTeacher);

        // Admin Table
        tableName = "admins";
        tableQuery = "create table admins( Ad_ID varchar(15) not null primary key, Ad_Name varchar(60) not null, Ad_email varchar(60) not null, DOB date, Ad_contact varchar(20) not null );";
        String[] insertAdmin = {};
        initiateAllTable(tableName,tableQuery,insertAdmin);

        // Course Table
        tableName = "courses";
        tableQuery = "Create Table Courses( Course_code Varchar(20) not null, Course_title varchar(30),dept varchar(10), offered_dept varchar(10), Course_credit double precision, Primary Key (Course_code,dept,offered_dept) );";
        String[] insertCourse = {
                "INSERT INTO COURSES VALUES('CSE 4403','Algorithm','CSE','CSE',3.0);",
                "INSERT INTO COURSES VALUES('CSE 4405','Data and Telecommunication','CSE','CSE',4.0);",
                "INSERT INTO COURSES VALUES('CSE 4407','System Analysis and Design','CSE','CSE',2.0);","INSERT INTO COURSES VALUES('MATH 4441','Probability','CSE','CSE',3.0);",
                "INSERT INTO COURSES VALUES('EEE 4481','Digital Pulse Technic','EEE','CSE',3.0);",
                "INSERT INTO COURSES VALUES('HUM 4441','Engineering Ethics','CSE','CSE',3.0);",
        };
        initiateAllTable(tableName,tableQuery,insertCourse);

//         CourseTakenByTeacher
        tableName = "Teacher_takes_course";
        tableQuery = "Create table Teacher_takes_course(courseteacher_ID varchar(15) not null,T_coursedept varchar(20), T_coursecode varchar(20),T_OfferedDept varchar(20), semester varchar(10),Foreign key(courseteacher_ID) references Teacher(T_ID),Foreign key(T_coursedept,T_OfferedDept,T_coursecode) references courses(dept,offered_dept,Course_code));";
        tableQuery = "Create table Teacher_takes_course(courseteacher_ID varchar(15) not null,T_coursedept varchar(20), T_coursecode varchar(20),T_OfferedDept varchar(20), semester varchar(10),Foreign key(courseteacher_ID) references Teacher(T_ID),Foreign key(T_coursedept,T_OfferedDept,T_coursecode) references courses(dept,offered_dept,Course_code));";
        String[] insertData = {};
        initiateAllTable(tableName,tableQuery,insertData);

//        Student_takes_course
        tableName = "Student_takes_course";
        tableQuery = "Create table Student_takes_course(S_ID varchar(20) not null,Student_dept varchar(20), S_coursedept varchar(20), S_coursecode varchar(20),s_courseOfferedDept varchar(20),semester varchar(10),Total_class int not null,Attended_class int not null,Quiz_1 double precision not null,Quiz_2 double precision not null,Quiz_3 double precision not null,Quiz_4 double precision not null,Mis_marks double precision not null,Final_marks double precision not null,Foreign key(S_coursedept,s_courseOfferedDept,S_coursecode) references Courses(dept,offered_dept,Course_code));";
        initiateAllTable(tableName,tableQuery,insertData);
    }

    public void initiateAllTable(String tableName,String createTableQuery,String[] insertQuery) throws SQLException {
        Statement statement = null;
//        ResultSet resultSet=null;
        String dropQuery = "drop table if exists " +tableName;
//        String createTableQuery = ;

        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            statement = connection.createStatement();

            statement.executeUpdate(dropQuery);
            statement.executeUpdate(createTableQuery);

            for(int i = 0;i < insertQuery.length;i++) {
                statement.executeUpdate(insertQuery[i]);
            }
            statement.close();
        } catch (Exception e) {
            System.out.println("Error creating user table");
            throw new RuntimeException(e);
        }
    }

    //this function queries the userinput and checks the password with database
    public boolean loginNow(String user, String pass) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from users where email=? and password=?";
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception thrown in login");
            return false;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public String getUserType(String email) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from users where email=?";
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
//                System.out.println(resultSet.getString(3));
                return resultSet.getString(3);
            } else {
                return null;
            }

        } catch (Exception e) {
            System.out.println("Error getting user type");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }


}
