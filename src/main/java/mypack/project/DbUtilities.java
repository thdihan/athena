package mypack.project;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import userPack.Courses;
import userPack.Student;
import userPack.Teacher;

import java.sql.*;
import java.util.ArrayList;

/**
 * This class is dedicated solely for database related functions
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */

public class DbUtilities {

    //connection function for database

    /**
     * For connecting to posgresql database
     * @param dbName Name of the database
     * @param user Name of the user
     * @param pass Password of the user
     * @return Return the Connection object. Else null if connection failed
     */
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

    /**
     * To create and initiate data on all the tables of the database
     * @throws SQLException If problems with query
     */
    public void initiateTablesWithQuery() throws SQLException {

//         UsersTable
        String[] insertUser = {
                "insert into users values ('shakun650@gmail.com', 'tukasl', 's')",
                "insert into users values ('hasan123@gmail.com', 'tukas', 's')",
                "insert into users values ('z', 'z', 't')",
                "insert into users values ('shuvro234@gmail.com', 'tukasl', 's')",
                "insert into users values ('a', 'a', 's')",
                "insert into users values ('s', 's', 's')"
        };
        String tableName = "users";
        String tableQuery = "create table users(email text, password text, type varchar(10),constraint pk_users primary key (email) );";
        initiateAllTable(tableName, tableQuery, insertUser);

        // Student Table
        tableName = "student";
        tableQuery = "Create table Student(S_ID varchar(15) not null primary key,S_Name varchar(60) not null, S_email varchar(60) not null,Dept varchar(20),Semester varchar(10),DOB date,S_contact varchar(15) not null);";
        String[] insertStudent = {
                "insert into student values('200041111','Hasan','hasan123@gmail.com','CSE','4','2001-01-01','01711111111');",
                "insert into student values('200041112','Kamal','a','CSE','4','2001-01-01','01711111111');",
                "insert into student values('200041113','Wolf','s','CSE','4','2001-01-01','01711111111');"
        };
        initiateAllTable(tableName, tableQuery, insertStudent);

        // Teacher Table
        tableName = "teacher";
        tableQuery = "Create table Teacher(T_ID varchar(15) not null primary key,T_Name varchar(60) not null,T_email varchar(60) not null,Dept varchar(20),DOB date,T_contact varchar not null);";
        String[] insertTeacher = {
                "insert into teacher values('123456','Jamal','z','CSE','1995-01-01','01711111111');"
        };
        initiateAllTable(tableName, tableQuery, insertTeacher);

        // Admin Table
        tableName = "admins";
        tableQuery = "create table admins( Ad_ID varchar(15) not null primary key, Ad_Name varchar(60) not null, Ad_email varchar(60) not null, DOB date, Ad_contact varchar(20) not null );";
        String[] insertAdmin = {};
        initiateAllTable(tableName, tableQuery, insertAdmin);

        // Course Table
        tableName = "courses";
        tableQuery = "Create Table Courses( Course_code Varchar(20) not null, Course_title varchar(80),dept varchar(10), offered_dept varchar(10), Course_credit double precision, Primary Key (Course_code,dept,offered_dept) );";
        String[] insertCourse = {
                "INSERT INTO COURSES VALUES('CSE 4403','Algorithm','CSE','CSE',3.0);",
                "INSERT INTO COURSES VALUES('CSE 4405','Data and Telecommunication','CSE','CSE',4.0);",
                "INSERT INTO COURSES VALUES('CSE 4407','System Analysis and Design','CSE','CSE',2.0);", "INSERT INTO COURSES VALUES('MATH 4441','Probability','CSE','CSE',3.0);",
                "INSERT INTO COURSES VALUES('EEE 4481','Digital Electronics and Pulse Techniques','EEE','CSE',3.0);",
                "INSERT INTO COURSES VALUES('HUM 4441','Engineering Ethics','CSE','CSE',3.0);",
        };
        initiateAllTable(tableName, tableQuery, insertCourse);

//         CourseTakenByTeacher
        tableName = "Teacher_takes_course";
//        tableQuery = "Create table Teacher_takes_course(courseteacher_ID varchar(15) not null,T_coursedept varchar(20), T_coursecode varchar(20),T_OfferedDept varchar(20), semester varchar(10),Foreign key(courseteacher_ID) references Teacher(T_ID),Foreign key(T_coursedept,T_OfferedDept,T_coursecode) references courses(dept,offered_dept,Course_code));";
        tableQuery = "Create table Teacher_takes_course(courseteacher_ID varchar(15) not null,T_coursedept varchar(20), T_coursecode varchar(20),T_OfferedDept varchar(20),CONSTRAINT ttc_teacher Foreign key(courseteacher_ID) references Teacher(T_ID),CONSTRAINT ttc_course Foreign key(T_coursedept,T_OfferedDept,T_coursecode) references courses(dept,offered_dept,Course_code));";
        String[] insertData = {};
//        initiateAllTable(tableName, tableQuery, insertData);

//        Student_takes_course
        tableName = "Student_takes_course";
        tableQuery = "Create table Student_takes_course(S_ID varchar(20) not null,Student_dept varchar(20), S_coursedept varchar(20), S_coursecode varchar(20),s_courseOfferedDept varchar(20),semester varchar(10),Total_class int not null,Attended_class int not null,Quiz_1 double precision not null,Quiz_2 double precision not null,Quiz_3 double precision not null,Quiz_4 double precision not null,Mid_marks double precision not null,Final_marks double precision not null,CONSTRAINT stc_student Foreign key(S_ID) references student(S_ID),CONSTRAINT stc_course Foreign key(S_coursedept,s_courseOfferedDept,S_coursecode) references Courses(dept,offered_dept,Course_code));";
//        initiateAllTable(tableName, tableQuery, insertData);
    }

    /**
     * To create a table and insert demo data in it
     * @param tableName Name of the table
     * @param createTableQuery Query for creating table
     * @param insertQuery Query for demo data in created table
     * @throws SQLException If problems with query
     */
    public void initiateAllTable(String tableName, String createTableQuery, String[] insertQuery) throws SQLException {


        Statement statement = null;
//        ResultSet resultSet=null;
        String dropQuery = "drop table if exists " + tableName;
//        String createTableQuery = ;

        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            statement = connection.createStatement();

            if (tableName.equals("teacher")) {
                statement.executeUpdate("ALTER TABLE Teacher_takes_course DROP CONSTRAINT   IF EXISTS ttc_teacher");
                statement.executeUpdate("ALTER TABLE Teacher_takes_course DROP CONSTRAINT   IF EXISTS ttc_course");
            }
            if (tableName.equals("student")) {
                statement.executeUpdate("ALTER TABLE Student_takes_course DROP CONSTRAINT   IF EXISTS stc_course");
                statement.executeUpdate("ALTER TABLE Student_takes_course DROP CONSTRAINT   IF EXISTS stc_student");
            }

            statement.executeUpdate(dropQuery);
            statement.executeUpdate(createTableQuery);

            for (int i = 0; i < insertQuery.length; i++) {
                statement.executeUpdate(insertQuery[i]);
            }
            statement.close();
        } catch (Exception e) {
            System.out.println("Error creating user table");
            throw new RuntimeException(e);
        }
    }

    //this function queries the userinput and checks the password with database

    /**
     * For login authentication of users
     * @param user Username or email
     * @param pass Password of the user
     * @return True if matched. Else return false
     * @throws SQLException If problems with query
     */
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

    /**
     * To get the type of user (teacher, student, admin)
     * @param email Username or email of the user
     * @return User type (s for student, t for teacher, a for admin)
     * @throws SQLException If problems with query
     */
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

    /**
     * To get the personal information of a student
     * @param email Username or email of the student
     * @return Student object if found. Else return null
     * @throws SQLException If problems with query
     */
    public Student getStudentInfo(String email) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from student where s_email=?";
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
//                System.out.println(resultSet.getString(3));
                Student temp = new Student(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getString(7));
                return temp;
            } else {
                return null;
            }

        } catch (Exception e) {
            System.out.println("Exception getting student info");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public Teacher getTeacherInfo(String email) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from teacher where t_email=?";
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
//                System.out.println(resultSet.getString(3));
                Teacher temp = new Teacher(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getDate(5), resultSet.getString(6));
                return temp;
            } else {
                return null;
            }

        } catch (Exception e) {
            System.out.println("Exception getting student info");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    /**
     * To get the list of the offered courses (may need to change based on semester)
     * @param dept Department which offered the course
     * @return List of courses if found. Else return empty list
     * @throws SQLException
     */
    public ArrayList<Courses> getOfferedCourses(String dept, String userType) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query;
        if(userType.equals("s"))
            query = "select * from courses where offered_dept=?";
        else
            query = "select * from courses where dept=?";
        ArrayList<Courses> courses= new ArrayList<>();
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, dept);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                courses.add(new Courses(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5)));
            }
            return  courses;
        } catch (SQLException e) {
            System.out.println("Exception getting offered courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    /**
     * For course registration of students
     * @param vBox Vbox element containing the offered courses
     * @param currentStudent The student doing the registration
     * @param offered_courses List of offered courses to the student
     * @return List of registered course if registration successful. Else return empty list
     * @throws SQLException If problems with query
     */
    public ArrayList<Courses> registerCourses(VBox vBox, Student currentStudent, ArrayList<Courses>offered_courses) throws SQLException {
        PreparedStatement preparedStatement = null;
        String query = "insert into student_takes_course values(?, ?, ?, ?, ?, ?, 0,0,0,0,0,0,0,0);";
        ArrayList<Courses> registered_courses=new ArrayList<>();
        try{
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);

            for(int i=0 ; i<offered_courses.size() ; i++){
                CheckBox course= (CheckBox) vBox.getChildren().get(i);
                if(course.isSelected()){
                    registered_courses.add(offered_courses.get(i));
                    preparedStatement.setString(1, currentStudent.getId());
                    preparedStatement.setString(2, currentStudent.getDept());
                    preparedStatement.setString(3,offered_courses.get(i).getDept());
                    preparedStatement.setString(4, offered_courses.get(i).getCode());
                    preparedStatement.setString(5, offered_courses.get(i).getOffered_dept());
                    preparedStatement.setString(6, currentStudent.getSemester());
                    preparedStatement.executeUpdate();
                }
            }
            return registered_courses;



        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        }finally {
            preparedStatement.close();
        }
    }

    public ArrayList<Courses> registerTeacherCourses(VBox vBox, Teacher currentTeacher, ArrayList<Courses>offered_courses) throws SQLException {
        PreparedStatement preparedStatement = null;
        String query = "insert into teacher_takes_course values(?, ?, ?, ?);";
        ArrayList<Courses> registered_courses=new ArrayList<>();
        try{
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);

            for(int i=0 ; i<offered_courses.size() ; i++){
                CheckBox course= (CheckBox) vBox.getChildren().get(i);
                if(course.isSelected()){
                    registered_courses.add(offered_courses.get(i));
                    preparedStatement.setString(1, currentTeacher.getId());
                    preparedStatement.setString(2, currentTeacher.getDept());
                    preparedStatement.setString(3,offered_courses.get(i).getCode());
                    preparedStatement.setString(4, offered_courses.get(i).getDept());
//                    preparedStatement.setString(6, currentStudent.getSemester());
                    preparedStatement.executeUpdate();
                }
            }
            return registered_courses;



        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        }finally {
            preparedStatement.close();
        }
    }

    /**
     * To get the list registered courses of a student
     * @param id Id of the student
     * @param semester Semester of the student
     * @return List of registered courses if registered. Else return empty list
     * @throws SQLException If problems with query
     */
    public ArrayList<Courses> getRegisteredCourses(String id, String semester) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select course_code, course_title, dept, offered_dept, course_credit from courses , (select s_coursecode, s_courseoffereddept from Student_takes_course where s_id=? and semester=?) sub where s_coursecode=course_code and s_courseoffereddept=offered_dept;";
        ArrayList<Courses> courses= new ArrayList<>();

        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, semester);
            resultSet = preparedStatement.executeQuery();


            while(resultSet.next()){
//                System.out.println("NOT NULL");
                courses.add(new Courses(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5)));
            }
//            System.out.println("NULL");
            return  courses;
        } catch (SQLException e) {
            System.out.println("Exception getting registered courses");
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Courses> getTeacherRegisteredCourses(String id) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select course_code, course_title, dept, offered_dept, course_credit from courses , (select t_coursecode, T_OfferedDept from teacher_takes_course where courseteacher_ID=?) sub where t_coursecode=course_code and T_OfferedDept=offered_dept;";
        ArrayList<Courses> courses= new ArrayList<>();

        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();


            while(resultSet.next()){
//                System.out.println("NOT NULL");
                courses.add(new Courses(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5)));
            }
//            System.out.println("NULL");
            return  courses;
        } catch (SQLException e) {
            System.out.println("Exception getting registered courses");
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getStudentList(String courseCode){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select s_name, student.s_id from student, (select s_id from student_takes_course where s_coursecode=?) sub where student.s_id=sub.s_id;";
        ArrayList<String> studentList= new ArrayList<>();
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, courseCode);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                studentList.add(resultSet.getString(1)+": "+ resultSet.getString(2)); //format: ID: FullName
            }
            return studentList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStudentMarks(String studentId, Double marks, String courseCode,String examType){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        //maybe need to do something about it
        String quiz_1 = "update student_takes_course\n" +
                "set quiz_1=?\n" +
                "where s_id=? and s_coursecode=?;";
        String quiz_2 = "update student_takes_course\n" +
                "set quiz_2=?\n" +
                "where s_id=? and s_coursecode=?;";
        String quiz_3 = "update student_takes_course\n" +
                "set quiz_3=?\n" +
                "where s_id=? and s_coursecode=?;";
        String quiz_4 = "update student_takes_course\n" +
                "set quiz_4=?\n" +
                "where s_id=? and s_coursecode=?;";
        String mid_marks = "update student_takes_course\n" +
                "set mid_marks=?\n" +
                "where s_id=? and s_coursecode=?;";
        String final_marks = "update student_takes_course\n" +
                "set final_marks=?\n" +
                "where s_id=? and s_coursecode=?;";
        String query;
        if(examType.equals("quiz_1"))
            query=quiz_1;
        else if (examType.equals("quiz_2")) {
            query=quiz_2;
        }else if (examType.equals("quiz_3")) {
            query=quiz_3;
        }else if (examType.equals("quiz_4")) {
            query=quiz_4;
        }else if (examType.equals("mid_marks")) {
            query=mid_marks;
        }else   {
            query=final_marks;
        }


        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, marks);
            preparedStatement.setString(2, studentId);
            preparedStatement.setString(3, courseCode);
            preparedStatement.executeUpdate();
            System.out.println("Marks updated successfully");


        } catch (Exception e) {
            System.out.println("Error updating student marks");
            throw new RuntimeException(e);
        }
    }

    public void takeAttendance(String courseCode, ArrayList<String> presentList, ArrayList<String> absentList){
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;
        String presentQuery="update student_takes_course\n" +
                "set total_class=total_class+1, attended_class=attended_class+1\n" +
                "where s_id=? and s_coursecode=?;";
        String absentQuery="update student_takes_course\n" +
                "set total_class=total_class+1\n" +
                "where s_id=? and s_coursecode=?;";
        try{
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");

            preparedStatement1 = connection.prepareStatement(presentQuery);
            preparedStatement2=connection.prepareStatement(absentQuery);
            preparedStatement1.setString(2,courseCode);
            preparedStatement2.setString(2,courseCode);

            for(int i=0 ; i<presentList.size() ; i++){
                preparedStatement1.setString(1,presentList.get(i));
                preparedStatement1.executeUpdate();
            }
            System.out.println("Present List updated");

            for(int i=0 ; i<absentList.size() ; i++){
                preparedStatement2.setString(1,absentList.get(i));
                preparedStatement2.executeUpdate();
            }
            System.out.println("Absent List updated");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
