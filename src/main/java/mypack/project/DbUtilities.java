package mypack.project;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import userPack.*;

import java.sql.*;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class is dedicated solely for database related functions
 *
 * @author Unknown
 * @version 1.0
 * @since March, 2023
 */

public class DbUtilities {

    //connection function for database

    /**
     * For connecting to postgresql database
     *
     * @param dbName Name of the database
     * @param user   Name of the user
     * @param pass   Password of the user
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



    /**
     * To create and initiate data on all the tables of the database
     *
     * @throws SQLException If problems with query
     */
    public void initiateTablesWithQuery() throws SQLException {

//         UsersTable
        String[] insertUser = {
                "insert into users values ('jamal@gmail.com', 'z', 't')",
                "insert into users values ('shakun650@gmail.com', '21752926f73d037a19c53a9f172dd00c2b08d4b7b6d6e3b096835842faf24f57', 's')",
                "insert into users values ('hasan@gmail.com', 'd38b6b3ca3e5bac0547c3cf6ea5b92a4f633bd6b2c8c94d28e009736d02ab3f4', 's')",
                "insert into users values ('z', '594e519ae499312b29433b7dd8a97ff068defcba9755b6d5d00e84c524d67b06', 't')",
//                "insert into users values ('shuvro234@gmail.com', 'tukasl', 's')",
                "insert into users values ('a', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', 's')",
                "insert into users values ('s', '43a718774c572bd8a25adbeb1bfcd5c0256ae11cecf9f9c3f925d0e52beaf89', 's')",
                "insert into users values ('admin@gmail.com','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','a')"
        };
        String tableName = "users";
        String tableQuery = "create table users(email text, password text, type varchar(10),constraint pk_users primary key (email) );";
        initiateAllTable(tableName, tableQuery, insertUser);

        // Student Table
        tableName = "student";
        tableQuery = "Create table Student(S_ID varchar(15) not null primary key,S_Name varchar(100) not null, S_email varchar(100) not null,Dept varchar(20),Semester varchar(10),DOB varchar(20),S_contact varchar(15) not null);";
        String[] insertStudent = {
                "insert into student values('200041111','Hasan','hasan@gmail.com','CSE','4','2001-01-01','01711111111');",
                "insert into student values('200041112','Kamal','a','CSE','4','2001-01-01','01711111111');",
                "insert into student values('200041113','Wolf','s','CSE','4','2001-01-01','01711111111');",
                "insert into student values('200041114','Shakun','shakun650@gmail.com','CSE','3','2001-01-01','01711111111');"
        };
        initiateAllTable(tableName, tableQuery, insertStudent);

        // Teacher Table
        tableName = "teacher";
        tableQuery = "Create table Teacher(T_ID varchar(15) not null primary key,T_Name varchar(60) not null,T_email varchar(60) not null,Dept varchar(20),DOB varchar(20),T_contact varchar not null);";
        String[] insertTeacher = {
                "insert into teacher values('123456','Jamal','jamal@gmail.com','CSE','1995-01-01','01711111111');",
                "insert into teacher values('1233456','Jamal','z','CSE','1995-01-01','01711111111');"
        };
        initiateAllTable(tableName, tableQuery, insertTeacher);

        // Admin Table
        tableName = "admins";
        tableQuery = "create table admins( admin_ID varchar(15) not null primary key, admin_Name varchar(60) not null, admin_email varchar(60) not null, DOB varchar(30), admin_contact varchar(20) not null );";
        String[] insertAdmin = {
                "insert into admins values('123456', 'Admin 1','admin@gmail.com','2000-01-01','0171111111')"
        };
        initiateAllTable(tableName, tableQuery, insertAdmin);

        // Course Table
        tableName = "courses";
        tableQuery = "Create Table Courses( " +
                "Course_code Varchar(20) not null, " +
                "Course_title varchar(80)," +
                "dept varchar(10), " +
                "offered_dept varchar(10)," +
                " Course_credit double precision, " +
                "Semester varchar(10)," +
                "Primary Key (Course_code,dept,offered_dept) );";
        String[] insertCourse = {
                "INSERT INTO COURSES VALUES('CSE 4403','Algorithm','CSE','CSE',3.0,'4');",
                "INSERT INTO COURSES VALUES('CSE 4405','Data and Telecommunication','CSE','CSE',4.0,'4');",
                "INSERT INTO COURSES VALUES('CSE 4407','System Analysis and Design','CSE','CSE',2.0,'4');", "INSERT INTO COURSES VALUES('MATH 4441','Probability','CSE','CSE',3.0,'4');",
                "INSERT INTO COURSES VALUES('EEE 4481','Digital Electronics and Pulse Techniques','EEE','CSE',3.0,'4');",
                "INSERT INTO COURSES VALUES('HUM 4441','Engineering Ethics','CSE','CSE',3.0,'4');",
//                "INSERT INTO COURSES VALUES('CSE 4303','DBMS','CSE','CSE',3.0,'3');"
        };
        initiateAllTable(tableName, tableQuery, insertCourse);

//         CourseTakenByTeacher
        tableName = "Teacher_takes_course";
//        tableQuery = "Create table Teacher_takes_course(courseteacher_ID varchar(15) not null,T_coursedept varchar(20), T_coursecode varchar(20),T_OfferedDept varchar(20), semester varchar(10),Foreign key(courseteacher_ID) references Teacher(T_ID),Foreign key(T_coursedept,T_OfferedDept,T_coursecode) references courses(dept,offered_dept,Course_code));";
        tableQuery = "Create table Teacher_takes_course(" +
                "courseteacher_ID varchar(15) not null," +
                "T_coursedept varchar(20), " +
                "T_coursecode varchar(20)," +
                "T_OfferedDept varchar(20)," +
                "Total_class int not null," +
                "Academic_Year int not null," +
                "CONSTRAINT ttc_teacher Foreign key(courseteacher_ID) references Teacher(T_ID),CONSTRAINT ttc_course Foreign key(T_coursedept,T_OfferedDept,T_coursecode) references courses(dept,offered_dept,Course_code));";
        String[] insertData = {};
        initiateAllTable(tableName, tableQuery, insertData);

//        Student_takes_course
        tableName = "Student_takes_course";
        tableQuery = "Create table Student_takes_course(S_ID varchar(20) not null," +
                "Student_dept varchar(20), " +
                "S_coursedept varchar(20), " +
                "S_coursecode varchar(20)," +
                "s_courseOfferedDept varchar(20)," +
                "semester varchar(10)," +
                "Attended_class int not null," +
                "Quiz_1 double precision ," +
                "Quiz_2 double precision ," +
                "Quiz_3 double precision ," +
                "Quiz_4 double precision ," +
                "Mid_marks double precision ," +
                "Final_marks double precision ," +
                "Academic_Year int not null," +
                "CONSTRAINT stc_student Foreign key(S_ID) references student(S_ID),CONSTRAINT stc_course Foreign key(S_coursedept,s_courseOfferedDept,S_coursecode) references Courses(dept,offered_dept,Course_code));";
        initiateAllTable(tableName, tableQuery, insertData);

        // Post Table

        String[] demoPost = {
                "INSERT INTO POST VALUES('P001','CSE 4405','jamal@gmail.com','t','This is demo post','assignment',null,'2023-04-01 12:00:00');",
                "INSERT INTO post VALUES ('P002', 'CSE 4405', 'jamal@gmail.com', 't', 'Urgent update required', 'announcement', null, null);",
                "INSERT INTO post VALUES ('P003','CSE 4405',  'hasan@gmail.com', 's', 'Check out our latest product', 'announcement', null, null);"
        };

        tableName = "Post";
        tableQuery = "Create table post(" +
                "postid varchar(50)," +
                "courseCode varchar(50),"+
                "post_giver_email varchar(100)," +
                "post_giver_type varchar(20)," +
                "post_text varchar(5000)," +
                "post_type varchar(50)," +
                "attachment BYTEA," +
                "deadline varchar(50)," +
                "constraint pk_post primary key (postid)," +
                "constraint fk_user_post foreign key (post_giver_email) references users(email)" +
                ");";
        initiateAllTable(tableName,tableQuery,demoPost);


        // comment table

        String[] demoComment = {
                "insert into comment values('C001', 'P001','jamal@gmail.com','t','Comment 1','2023-03-01 12:33:00')"
        };
        tableName = "comment";
        tableQuery = "create table comment (" +
                "    commentid varchar(30)," +
                "    postid varchar(30)," +
                "    commenter_email varchar(50)," +
                "    commenter_type varchar(20)," +
                "    comment_text varchar(5000)," +
                "    comment_time varchar(30),"+
                "    constraint pk_comment primary key(commentid)," +
                "    constraint fk_comment_post foreign key (postid) references post (postid)" +
                ");";
        initiateAllTable(tableName, tableQuery, demoComment);


//        Submission Table
        String[] demoSubmission = {
                "insert into submission values('S001', 'P001','hasan@gmail.com',null,'2023-03-01 12:33:00');"
        };
        tableName = "submission";
        tableQuery = "create table submission(\n" +
                "\tsubmissionid varchar(30),\n" +
                "\tpostid varchar(30),\n" +
                "\tsubmitter_email varchar(50),\n" +
                "\tsubmission_file BYTEA,\n" +
                "\tsubmission_time varchar(30),\n" +
                "\tconstraint pk_submission primary key (submissionid),\n" +
                "\tconstraint fk_sub_post foreign key(postid) references post(postid)\n" +
                ");";

        initiateAllTable(tableName,tableQuery,demoSubmission);


        // Notification Table
        String[] demoNotifiaction = {
                "insert into notification values('N001','P001','post','jamal@gmail.com','a','New Post 1','2023-04-01 10:03:29');",
                "insert into notification values('N002','P001','post','jamal@gmail.com','a','New Post 2','2023-04-01 10:03:41');",
                "insert into notification values('N003','P001','post','jamal@gmail.com','a','New Post 3','2023-04-01 10:03:45');",
                "insert into notification values('N004','P001','post','jamal@gmail.com','a','New Post 4','2023-04-01 10:04:45');"
        };
        tableName = "notification";
        tableQuery = "create table notification (\n" +
                "\tnotificationid varchar(100),\n" +
                "\tpostid varchar(100),\n" +
                "\tnotification_type varchar(100),\n" +
                "notifier_email varchar(50),"+
                "notify_email varchar(50),"+
                "notification_text varchar(200),"+
                "notificationTime varchar(100),"+
                "\tconstraint pk_notification primary key(notificationid),\n" +
                "\tconstraint fk_notification_post foreign key (postid) references post(postid)\n" +
                ");";
        initiateAllTable(tableName,tableQuery,demoNotifiaction);
    }


    public void runResetQuery(String query) throws SQLException {
        // delete all data from teacherTakesCourse DELETE FROM teacher_takes_course;
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate(query);
//            System.out.println("working");

        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }
    }
    public void reset() throws SQLException {
        // delete all data from teacherTakesCourse DELETE FROM teacher_takes_course;
        String query = "DELETE FROM teacher_takes_course";
        runResetQuery(query);

        // update all students semester unless 8
        query = "UPDATE student SET semester = \n" +
                "    CASE \n" +
                "        WHEN semester <> '8' THEN (CAST(semester AS INTEGER) + 1)::VARCHAR \n" +
                "        ELSE semester \n" +
                "    END;";
        runResetQuery(query);

        // delete all data from Notification DELETE FROM notification;
        query = "DELETE FROM notification";
        runResetQuery(query);

        // delete all data from Comment DELETE FROM comment;
        query = "DELETE FROM comment";
        runResetQuery(query);

        // delete all data from Submission DELETE FROM submission;
        query = "DELETE FROM submission";
        runResetQuery(query);

        // delete all data from post DELETE FROM Post;
        query = "DELETE FROM Post";
        runResetQuery(query);

    }

    public void delete_studentTakesCourse(String id) throws SQLException {
        // delete all data from teacherTakesCourse DELETE FROM teacher_takes_course;
        String query = "DELETE FROM student_takes_course where s_id = ?";
        // delete all data from teacherTakesCourse DELETE FROM teacher_takes_course;
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            preparedStatement.executeUpdate();

//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate(query);
//            System.out.println("working");

        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }
    }

    public void delete_teacherTakesCourse(String id) throws SQLException {
        // delete all data from teacherTakesCourse DELETE FROM teacher_takes_course;
        String query = "DELETE FROM teacher_takes_course where courseteacher_id = ?";
        // delete all data from teacherTakesCourse DELETE FROM teacher_takes_course;
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            preparedStatement.executeUpdate();

//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate(query);
//            System.out.println("working");

        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }
    }
    public void delete_resetRequest(String id) throws SQLException {
        // delete all data from teacherTakesCourse DELETE FROM teacher_takes_course;
        String query = "DELETE FROM reset_request where request_giver_id = ?";
        // delete all data from teacherTakesCourse DELETE FROM teacher_takes_course;
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            preparedStatement.executeUpdate();

//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate(query);
//            System.out.println("working");

        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }
    }

    public void addUser(User user) throws SQLException {
        PreparedStatement preparedStatement = null;
        String query = "insert into users values(?,?,?)";
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getType());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }


    }

    public void addStudent(Student student) throws SQLException {
        PreparedStatement preparedStatement = null;
        String query = "insert into student values(?,?,?,?,?,?,?)";
//        "insert into student values('200041111','Hasan','hasan@gmail.com','CSE','4','2001-01-01','01711111111');",
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getEmail());
            preparedStatement.setString(4, student.getDept());
            preparedStatement.setString(5, student.getSemester());
            preparedStatement.setString(6, student.getDob());
            preparedStatement.setString(7, student.getContact());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }

    }




    public void addTeacher(Teacher teacher) throws SQLException {
        PreparedStatement preparedStatement = null;
        String query = "insert into teacher values(?,?,?,?,?,?)";
//        "insert into student values('200041111','Hasan','hasan@gmail.com','CSE','4','2001-01-01','01711111111');",
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, teacher.getId());
            preparedStatement.setString(2, teacher.getName());
            preparedStatement.setString(3, teacher.getEmail());
            preparedStatement.setString(4, teacher.getDept());
            preparedStatement.setString(5, teacher.getDob());
            preparedStatement.setString(6, teacher.getContact());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }

    }



    public void addCourses(Courses course) throws SQLException {
        PreparedStatement preparedStatement = null;
        String query = "insert into courses values(?,?,?,?,?,?)";
//        "insert into student values('200041111','Hasan','hasan@gmail.com','CSE','4','2001-01-01','01711111111');",
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, course.getCode());
            preparedStatement.setString(2, course.getTitle());
            preparedStatement.setString(3, course.getDept());
            preparedStatement.setString(4, course.getOffered_dept());
            preparedStatement.setDouble(5, course.getCredit());
            preparedStatement.setString(6, course.getSemester());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }

    }



    public ArrayList<Pair<String,String,Double>> getResults(Student currentStudent, String semester){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Year thisYear = Year.now();
        String query = "select s_coursecode, course_title, quiz_1,quiz_2,quiz_3,quiz_4,mid_marks,final_marks,course_credit from Student_takes_course,(select course_code,course_title,course_credit from courses) as courseData where s_id=? and semester=? and courseData.course_code = Student_takes_course.s_coursecode";
        ArrayList<Pair<String,String,Double>>courses = new ArrayList<>();

        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, currentStudent.getId());
            preparedStatement.setString(2, semester);
            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                String getGrade = calculateGrade(resultSet.getDouble(3),resultSet.getDouble(4),resultSet.getDouble(5),resultSet.getDouble(6),resultSet.getDouble(7),resultSet.getDouble(8),resultSet.getDouble(9));
                Pair<String,String,Double> tempCourse = new Pair<>(resultSet.getString(1) + ": "+ resultSet.getString(2),getGrade,resultSet.getDouble(9));
                courses.add(tempCourse);
            }
//            System.out.println("NULL");

        } catch (SQLException e) {
            System.out.println("Exception getting registered courses");
            throw new RuntimeException(e);
        }
        return courses;
    }

    /**
     * To create a table and insert demo data in it
     *
     * @param tableName        Name of the table
     * @param createTableQuery Query for creating table
     * @param insertQuery      Query for demo data in created table
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


            if(tableName.equals("Post")) {
                statement.executeUpdate("ALTER TABLE comment DROP CONSTRAINT   IF EXISTS fk_comment_post");
                statement.executeUpdate("ALTER TABLE submission DROP CONSTRAINT   IF EXISTS fk_sub_post");
                statement.executeUpdate("ALTER TABLE notification DROP CONSTRAINT   IF EXISTS fk_notification_post");

            }
            if (tableName.equals("users")) {
                statement.executeUpdate("ALTER TABLE post DROP CONSTRAINT   IF EXISTS fk_user_post");
            }
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
     *
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
     *
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
     *
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
                Student temp = new Student(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7));
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



    public ArrayList<Student> getAllStudentInfo() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Student> allStudents = new ArrayList<>();
        String query = "select * from student";
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student temp = new Student(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7));

                allStudents.add(temp);

            }

            return allStudents;

        } catch (Exception e) {
            System.out.println("Exception getting student info");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    /**
     * To get personal information of teacher
     * @param email Email of the teacher
     * @return Teacher object containing their information
     * @throws SQLException If problems with query
     */
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
                Teacher temp = new Teacher(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
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

    public  ArrayList<Teacher> getAllTeacherInfo() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Teacher> allteachers = new ArrayList<>();
        String query = "select * from teacher";
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
//                System.out.println(resultSet.getString(3));
                Teacher temp = new Teacher(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
                allteachers.add(temp);
            }
            return allteachers;
        } catch (Exception e) {
            System.out.println("Exception getting student info");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }



    public Admin getAdminInfo(String email) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from admins where admin_email=?";
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
//                System.out.println(resultSet.getString(3));
                Admin temp = new Admin(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),  resultSet.getString(4), resultSet.getString(5));
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


    public ArrayList<Courses> getAllCourses() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query;

        query = "select * from courses";
        ArrayList<Courses> courses = new ArrayList<>();
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courses.add(new Courses(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5),resultSet.getString(6)));
            }
            return courses;
        } catch (SQLException e) {
            System.out.println("Exception getting offered courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    /**
     * To get the list of the offered courses
     * @param dept Department of teacher and offered department of student
     * @param userType Type of user (teacher(t), student(s))
     * @param semester Ongoing semester
     * @return List of courses if found. Else return empty list
     * @throws SQLException If problems with query
     */
    public ArrayList<Courses> getOfferedCourses(String dept, String userType, String semester) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query;
        if (userType.equals("s"))
            query = "select * from courses where offered_dept=? and semester=?";
        else
            query = "select * from courses where dept=?";
        ArrayList<Courses> courses = new ArrayList<>();
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            if (userType.equals("s")) {
                preparedStatement.setString(1, dept);
                preparedStatement.setString(2, semester);
            } else {
                preparedStatement.setString(1, dept);
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courses.add(new Courses(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5),semester));
            }
            return courses;
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
     * @param vBox            Vbox element containing the offered courses
     * @param currentStudent  The student doing the registration
     * @param offered_courses List of offered courses to the student
     * @return List of registered course if registration successful. Else return empty list
     * @throws SQLException If problems with query
     */
    public ArrayList<Courses> registerCourses(VBox vBox, Student currentStudent, ArrayList<Courses> offered_courses) throws SQLException {
        PreparedStatement preparedStatement = null;
        Year thisYear = Year.now();
        String query = "insert into student_takes_course values(?, ?, ?, ?, ?, ?,0, -1, -1, -1, -1, -1, -1, ?);";
        ArrayList<Courses> registered_courses = new ArrayList<>();
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);

            for (int i = 0; i < offered_courses.size(); i++) {
                HBox hBox = (HBox) vBox.getChildren().get(i);
                CheckBox course= (CheckBox) hBox.getChildren().get(0);

                if (course.isSelected()) {
                    registered_courses.add(offered_courses.get(i));
                    preparedStatement.setString(1, currentStudent.getId());
                    preparedStatement.setString(2, currentStudent.getDept());
                    preparedStatement.setString(3, offered_courses.get(i).getDept());
                    preparedStatement.setString(4, offered_courses.get(i).getCode());
                    preparedStatement.setString(5, offered_courses.get(i).getOffered_dept());
                    preparedStatement.setString(6, currentStudent.getSemester());
                    preparedStatement.setInt(7, Integer.parseInt(String.valueOf(thisYear)));
                    preparedStatement.executeUpdate();
                }
            }
            return registered_courses;


        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }
    }



    public void setNotification(Post post, String notificationType) throws SQLException {

        ArrayList<String> userList = new ArrayList<>();
        Notification newNotification = new Notification();
        String query = "select s_email from student, student_takes_course where student_takes_course.s_coursecode = ? and student_takes_course.s_id = student.s_id; ";

        PreparedStatement preparedStatement = null;

        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,post.getCourseCode());
            ResultSet users = preparedStatement.executeQuery();

            while(users.next()) {
                System.out.println("Notification To : "  + users.getString(1));

                userList.add(users.getString(1));
            }

        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }

        // selectFromTeacher
        query = "select t_email from teacher, teacher_takes_course where teacher_takes_course.t_coursecode = ? and teacher_takes_course.courseteacher_id = teacher.t_id;";



        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,post.getCourseCode());
            ResultSet users = preparedStatement.executeQuery();

            while(users.next()) {
                System.out.println("Notification To : "  + users.getString(1));

                userList.add(users.getString(1));
            }

        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }


        // setNotification


        // generate uniqueId
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = dateFormat.format(new java.util.Date());
        Random random = new Random();
        int randomNum = random.nextInt(10000);
        randomNum %= 12;
        String uniqueCode = date + randomNum;

        // prepare notification text
        String postSubstr;
        if(post.getPost_text().length() <= 15) postSubstr = post.getPost_text();
        else postSubstr = post.getPost_text().substring(0,15);
        String notifiaction_text = "New " + notificationType + " from '" + post.getPost_giver_email() +  "' : " + postSubstr;

        // get current date
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentDateTimeString = currentDateTime.format(formatter);


        for(int i = 0; i < userList.size();i++) {

            query =  "INSERT INTO NOTIFICATION VALUES(?,?,?,?,?,?,?);";
//            "insert into notification values('N001','P001','post','jamal@gmail.com','a','New Post','2023-04-01 12:44:33');"
          try {
                Connection connection = connectToDB("projectDb", "postgres", "tukasl");
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, notificationType+"_"+uniqueCode+userList.get(i));
                preparedStatement.setString(2, post.getPostid());
                preparedStatement.setString(3, notificationType);
                preparedStatement.setString(4, post.getPost_giver_email());
                preparedStatement.setString(5, userList.get(i));
                preparedStatement.setString(6, notifiaction_text);

                preparedStatement.setString(7, currentDateTimeString);
                preparedStatement.executeUpdate();

            } catch (Exception e) {
                System.out.println("Exception registering courses");
                throw new RuntimeException(e);
            } finally {
                preparedStatement.close();
            }
        }





    }



    public ArrayList<Notification> getAllNotification(String useremail) throws SQLException {

        PreparedStatement preparedStatement = null;
        String query = "select * from notification where  notify_email = ?;";
        ArrayList<Notification> allNotification = new ArrayList<>();
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,useremail);
            ResultSet notifications = preparedStatement.executeQuery();

            while(notifications.next()) {
                Notification tempNotification = new Notification();
                tempNotification.setNotificationId(notifications.getString(1));
                tempNotification.setPostId(notifications.getString(2));
                tempNotification.setNotificationType(notifications.getString(3));
                tempNotification.setNotifierEmail(notifications.getString(4));
                tempNotification.setNotifyToEmail(notifications.getString(5));
                tempNotification.setNotificationText(notifications.getString(6));
                tempNotification.setNotifacationDeadline(notifications.getString(7));


                allNotification.add(tempNotification);
            }

            // sorting notifications
            Collections.sort(allNotification, new Comparator<>() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                public int compare(Notification dateTime1, Notification dateTime2) {
                    try {
                        java.util.Date date1 = dateFormat.parse(dateTime1.getNotifacationDeadline());
                        java.util.Date date2 = dateFormat.parse(dateTime2.getNotifacationDeadline());
                        return -date1.compareTo(date2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });

            for(int i = 0;i < allNotification.size();i++) {
                System.out.println(allNotification.get(i).getNotificationText());
            }

        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }
        return allNotification;
    }




    public ArrayList<Submission> getAllSubmission(String postId) throws SQLException {

        PreparedStatement preparedStatement = null;
        String query = "select * from submission where postId = ?;";
        ArrayList<Submission> allSubmission = new ArrayList<>();
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,postId);
            ResultSet submissions = preparedStatement.executeQuery();

            while(submissions.next()) {
//                System.out.println(posts.getString(1));
                Submission submission = new Submission();
                submission.setSubmissionId(submissions.getString(1));
                submission.setPostId(submissions.getString(2));
                submission.setSubmitterEmail(submissions.getString(3));
                byte[] blob = submissions.getBytes(4);
                if(blob!=null)
//                post.setAttachment(blob.getBytes(1,(int) blob.length()));
                    submission.setSubmissionFile(blob);
                submission.setSubmissionTime(submissions.getString(5));
                allSubmission.add(submission);

            }

        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }
        return allSubmission;
    }

    public ArrayList<Post> getAllPost(String courseCode) throws SQLException {

        PreparedStatement preparedStatement = null;
        String query = "select * from post where coursecode = ?;";
        ArrayList<Post> allPost = new ArrayList<>();
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,courseCode);
            ResultSet posts = preparedStatement.executeQuery();

            while(posts.next()) {
//                System.out.println(posts.getString(1));
                Post post = new Post();
                post.setPostid(posts.getString(1));
                post.setCourseCode(posts.getString(2));
                post.setPost_giver_email(posts.getString(3));
                post.setPost_giver_type(posts.getString(4));
                post.setPost_text(posts.getString(5));
                post.setPost_type(posts.getString(6));
                byte[] blob = posts.getBytes(7);
                if(blob!=null)
//                post.setAttachment(blob.getBytes(1,(int) blob.length()));
                    post.setAttachment(blob);
                post.setDeadline(posts.getString(8));

//                 Print the values of the columns to the console
                System.out.println("Post ID: " + post.getPostid());
                System.out.println("Course Code: " + post.getCourseCode());
                System.out.println("Post Giver Email: " + post.getPost_giver_email());
                System.out.println("Post Giver Type: " + post.getPost_giver_type());
                System.out.println("Post Text: " + post.getPost_text());
                System.out.println("Post Type: " + post.getPost_type());
                System.out.println("Attachment Link: " + post.getAttachment());
                System.out.println("Deadline: " + post.getDeadline());
                allPost.add(post);
            }

        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }
        return allPost;
    }


    public Post getPost(String postid) throws SQLException {
        Post tempPost = new Post();
        PreparedStatement preparedStatement = null;
        String query = "select * from post where postid = ?;";
        ArrayList<Post> allPost = new ArrayList<>();
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,postid);
            ResultSet posts = preparedStatement.executeQuery();

            while(posts.next()) {
//                System.out.println(posts.getString(1));
                Post post = new Post();
                post.setPostid(posts.getString(1));
                post.setCourseCode(posts.getString(2));
                post.setPost_giver_email(posts.getString(3));
                post.setPost_giver_type(posts.getString(4));
                post.setPost_text(posts.getString(5));
                post.setPost_type(posts.getString(6));
                byte[] blob = posts.getBytes(7);
                if(blob!=null)
//                post.setAttachment(blob.getBytes(1,(int) blob.length()));
                    post.setAttachment(blob);
                post.setDeadline(posts.getString(8));

//                 Print the values of the columns to the console
                System.out.println("Post ID: " + post.getPostid());
                System.out.println("Course Code: " + post.getCourseCode());
                System.out.println("Post Giver Email: " + post.getPost_giver_email());
                System.out.println("Post Giver Type: " + post.getPost_giver_type());
                System.out.println("Post Text: " + post.getPost_text());
                System.out.println("Post Type: " + post.getPost_type());
                System.out.println("Attachment Link: " + post.getAttachment());
                System.out.println("Deadline: " + post.getDeadline());
                tempPost = post;
            }

        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }
        return tempPost;
    }

    public void setPost(Post post) throws SQLException {

        PreparedStatement preparedStatement = null;
        String query =  "INSERT INTO POST VALUES(?,?,?,?,?,?,?,?);";
//        "INSERT INTO POST VALUES('P001','CSE 4405','jamal@gmail.com','t','This is demo post','assignment',null,'2023-04-01 12:00:00');",
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, post.getPostid());
            preparedStatement.setString(2, post.getCourseCode());
            preparedStatement.setString(3, post.getPost_giver_email());
            preparedStatement.setString(4, post.getPost_giver_type());
            preparedStatement.setString(5, post.getPost_text());
            preparedStatement.setString(6, post.getPost_type());

            preparedStatement.setBytes(7, post.getAttachment());
            preparedStatement.setString(8, post.getDeadline());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }

        // set notification
        setNotification(post,post.getPost_type());
    }


    public void setRegistrationResetRequest(String id,String email) throws SQLException {

        PreparedStatement preparedStatement = null;
        String query =  "INSERT INTO reset_request VALUES(?,?);";
//        "INSERT INTO POST VALUES('P001','CSE 4405','jamal@gmail.com','t','This is demo post','assignment',null,'2023-04-01 12:00:00');",
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }

    }

    public boolean getResetRequest(String id) throws SQLException {
        PreparedStatement preparedStatement = null;
        String query =  "select * from reset_request where request_giver_id = ?  ;";
//        "INSERT INTO POST VALUES('P001','CSE 4405','jamal@gmail.com','t','This is demo post','assignment',null,'2023-04-01 12:00:00');",
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet res = preparedStatement.executeQuery();
            while(res.next())
                return true;
        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }
        return false;
    }

    public ArrayList<Pair2<String, String>> getAllReset () throws SQLException {
        PreparedStatement preparedStatement = null;
        ArrayList<Pair2<String, String>> newRequest = new ArrayList<>();
        String query =  "select * from reset_request;";
//        "INSERT INTO POST VALUES('P001','CSE 4405','jamal@gmail.com','t','This is demo post','assignment',null,'2023-04-01 12:00:00');",
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            ResultSet res = preparedStatement.executeQuery();

            while (res.next()){
                Pair2<String, String> temp = new Pair2<>(res.getString(1),res.getString(2));
                newRequest.add(temp);
            }

        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }
        return newRequest;
    }


    public ArrayList<Comment> getAllComments(String postid) throws SQLException {

        PreparedStatement preparedStatement = null;
        String query = "select * from comment where postid = ?;";
        ArrayList<Comment> allComment = new ArrayList<>();
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,postid);
            ResultSet comments = preparedStatement.executeQuery();

            while(comments.next()) {
//                System.out.println(posts.getString(1));
                Comment comment = new Comment();
                comment.setCommentId(comments.getString(1));
                comment.setPostId(comments.getString(2));
                comment.setCommenterEmail(comments.getString(3));
                comment.setCommenterType(comments.getString(4));
                comment.setCommentText(comments.getString(5));
                comment.setCommentTime(comments.getString(6));



//                 Print the values of the columns to the console
//                System.out.println("Post ID: " + post.getPostid());
//                System.out.println("Course Code: " + post.getCourseCode());
//                System.out.println("Post Giver Email: " + post.getPost_giver_email());
//                System.out.println("Post Giver Type: " + post.getPost_giver_type());
//                System.out.println("Post Text: " + post.getPost_text());
//                System.out.println("Post Type: " + post.getPost_type());
//                System.out.println("Attachment Link: " + post.getAttachment_link());
//                System.out.println("Deadline: " + post.getDeadline());
                allComment.add(comment);
            }

        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }
        return allComment;
    }
    public void setComment(Comment comment, Post post) throws SQLException {
        PreparedStatement preparedStatement = null;
        String query =  "INSERT INTO COMMENT VALUES(?,?,?,?,?,?);";
//        "insert into comment values('C001', 'P001','jamal@gmail.com','t','Comment 1')
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, comment.getCommentId());
            preparedStatement.setString(2, comment.getPostId());
            preparedStatement.setString(3, comment.getCommenterEmail());
            preparedStatement.setString(4, comment.getCommenterType());
            preparedStatement.setString(5, comment.getCommentText());
            preparedStatement.setString(6, comment.getCommentTime());


            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }

        // set notification
        setNotification(post,"Comment");

    }


    public void setSubmission(Submission submission, Post post) throws SQLException {
        PreparedStatement preparedStatement = null;
        String query =  "INSERT INTO SUBMISSION VALUES(?,?,?,?,?);";
//        insert into submission values('S001', 'P001','hasan@gmail.com',null,'2023-03-01 12:33:00');
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, submission.getSubmissionId());
            preparedStatement.setString(2, submission.getPostId());
            preparedStatement.setString(3, submission.getSubmitterEmail());
            preparedStatement.setBytes(4,submission.getSubmissionFile());
            preparedStatement.setString(5, submission.getSubmissionTime());

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }

        // set notification
        setNotification(post,"Submission");

    }




    public ArrayList<Courses> registerTeacherCourses(VBox vBox, Teacher currentTeacher, ArrayList<Courses> offered_courses) throws SQLException {
        PreparedStatement preparedStatement = null;
        Year thisYear = Year.now();
        String query = "insert into teacher_takes_course values(?, ?, ?, ?, 0, ?);";
        ArrayList<Courses> registered_courses = new ArrayList<>();
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);

            for (int i = 0; i < offered_courses.size(); i++) {
                HBox hBox = (HBox) vBox.getChildren().get(i);
                CheckBox course= (CheckBox) hBox.getChildren().get(0);
                if (course.isSelected()) {
                    registered_courses.add(offered_courses.get(i));
                    preparedStatement.setString(1, currentTeacher.getId());
                    preparedStatement.setString(2, currentTeacher.getDept());
                    preparedStatement.setString(3, offered_courses.get(i).getCode());
                    preparedStatement.setString(4, offered_courses.get(i).getDept());
                    preparedStatement.setInt(5, Integer.parseInt(String.valueOf(thisYear)));
                    preparedStatement.executeUpdate();
                }
            }
            return registered_courses;


        } catch (Exception e) {
            System.out.println("Exception registering courses");
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
        }
    }

    /**
     * To get the list registered courses of a student
     *
     * @param id       Id of the student
     * @param semester Semester of the student
     * @return List of registered courses if registered. Else return empty list
     * @throws SQLException If problems with query
     */
    public ArrayList<Courses> getStudentRegisteredCourses(String id, String semester) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Year thisYear = Year.now();
        String query = "select course_code, course_title, dept, offered_dept, course_credit from courses , (select s_coursecode, s_courseoffereddept from Student_takes_course where s_id=? and semester=? and academic_year=?) sub where s_coursecode=course_code and s_courseoffereddept=offered_dept;";
        ArrayList<Courses> courses = new ArrayList<>();

        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, semester);
            preparedStatement.setInt(3,Integer.parseInt(String.valueOf(thisYear)));
            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
//                System.out.println("NOT NULL");
                courses.add(new Courses(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5),semester));
            }
//            System.out.println("NULL");
            return courses;
        } catch (SQLException e) {
            System.out.println("Exception getting registered courses");
            throw new RuntimeException(e);
        }
    }

    /**
     * Gives the registered courses of the teacher
     * @param id Id of the teacher
     * @return List of registered courses if any. Else returns empty list
     * @throws SQLException If problems with query
     */
    public ArrayList<Courses> getTeacherRegisteredCourses(String id) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Year thisYear = Year.now();
        String query = "select course_code, course_title, dept, offered_dept, course_credit,semester from courses , (select t_coursecode, T_OfferedDept from teacher_takes_course where courseteacher_ID=? and academic_year=?) sub where t_coursecode=course_code and T_OfferedDept=offered_dept;";
        ArrayList<Courses> courses = new ArrayList<>();

        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setInt(2, Integer.parseInt(String.valueOf(thisYear)));
            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
//                System.out.println("NOT NULL");
                courses.add(new Courses(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5),resultSet.getString(6)));
            }
//            System.out.println("NULL");
            return courses;
        } catch (SQLException e) {
            System.out.println("Exception getting registered courses");
            throw new RuntimeException(e);
        }
    }

    /**
     * To get the student list of a particular course code
     * @param courseCode Course code of a course
     * @return Student list if any. Else return empty list
     */
    public ArrayList<String> getStudentList(String courseCode) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Year thisYear = Year.now();
        String query = "select s_name, student.s_id from student, (select s_id from student_takes_course where s_coursecode=? and academic_year=?) sub where student.s_id=sub.s_id;";
        ArrayList<String> studentList = new ArrayList<>();
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, courseCode);
            preparedStatement.setInt(2,Integer.parseInt(String.valueOf(thisYear)));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                studentList.add(resultSet.getString(1) + ": " + resultSet.getString(2)); //format: ID: FullName
            }
            Collections.sort(studentList);
            return studentList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * For updating the student marks in the table
     * @param studentId Id of the student
     * @param marks Marks obtained by the student
     * @param courseCode Course code of the course
     * @param examType Type of exam the marks were given for
     */
    public void updateStudentMarks(String studentId, Double marks, String courseCode, String examType) {
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
        if (examType.equals("quiz_1"))
            query = quiz_1;
        else if (examType.equals("quiz_2")) {
            query = quiz_2;
        } else if (examType.equals("quiz_3")) {
            query = quiz_3;
        } else if (examType.equals("quiz_4")) {
            query = quiz_4;
        } else if (examType.equals("mid_marks")) {
            query = mid_marks;
        } else {
            query = final_marks;
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

    /**
     * Updating the attendance of the student
     * @param courseCode Course code of the course
     * @param presentList List of students that were present
     * @param absentList List of students that were absent
     * @param teacher Teacher taking the course
     */
    public void takeAttendance(String courseCode, ArrayList<String> presentList, ArrayList<String> absentList, Teacher teacher) {
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;
        ResultSet resultSet = null;
        String presentQuery = "update student_takes_course\n" +
                "set attended_class=attended_class+1\n" +
                "where s_id=? and s_coursecode=?;";
        String absentQuery = "update student_takes_course\n" +
                "set attended_class=attended_class+0\n" +
                "where s_id=? and s_coursecode=?;";
        String teacherTotalQuery = "update teacher_takes_course\n" +
                "set total_class=total_class+1\n" +
                "where courseteacher_id=? and t_coursecode=?";
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");

            preparedStatement1 = connection.prepareStatement(presentQuery);
            preparedStatement2 = connection.prepareStatement(absentQuery);
            preparedStatement1.setString(2, courseCode);
            preparedStatement2.setString(2, courseCode);

            for (int i = 0; i < presentList.size(); i++) {
                preparedStatement1.setString(1, presentList.get(i));
                preparedStatement1.executeUpdate();
            }
            System.out.println("Present List updated");

            for (int i = 0; i < absentList.size(); i++) {
                preparedStatement2.setString(1, absentList.get(i));
                preparedStatement2.executeUpdate();
            }
            System.out.println("Absent List updated");
            preparedStatement3 = connection.prepareStatement(teacherTotalQuery);
            preparedStatement3.setString(1, teacher.getId());
            preparedStatement3.setString(2, courseCode);
            preparedStatement3.executeUpdate();
            System.out.println("Total classes updated successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * For calculating the academic progress of the student
     * @param quiz1 Marks of quiz 1
     * @param quiz2 Marks of quiz 2
     * @param quiz3 Marks of quiz 3
     * @param quiz4 Marks of quiz 4
     * @param mid Marks of mid exam
     * @param final_marks Marks of final exam
     * @param credit Credits of the course
     * @return Academic progress of the student in percentage
     */
    public Double calculateProgress(double quiz1, double quiz2, double quiz3, double quiz4, double mid, double final_marks, double credit) {
        Double progress = 0.0;
        progress += (quiz1 == -1 ? 0 : quiz1);
        progress += (quiz2 == -1 ? 0 : quiz2);
        progress += (quiz3 == -1 ? 0 : quiz3);
        progress += (quiz4 == -1 ? 0 : quiz4);
        progress += (mid == -1 ? 0 : mid);
        progress += (final_marks == -1 ? 0 : final_marks);
        return (progress / credit);
    }

    /**
     * For calculating the expected grade of a student
     * @param quiz1 Marks of quiz 1
     * @param quiz2 Marks of quiz 2
     * @param quiz3 Marks of quiz 3
     * @param quiz4 Marks of quiz 4
     * @param mid Marks of mid exam
     * @param final_marks Marks of final exam
     * @param credit Credits of the course
     * @return String containing the grade of the student
     */
    public String calculateGrade(double quiz1, double quiz2, double quiz3, double quiz4, double mid, double final_marks, double credit) {
        double obtained = 0;
        double current_total = 0;
        if (quiz1 != -1) {
            obtained+=quiz1;
            current_total+=5*credit;
        }
        if (quiz2 != -1) {
            obtained+=quiz2;
            current_total+=5*credit;
        }
        if (quiz3 != -1) {
            obtained+=quiz3;
            current_total+=5*credit;
        }
        if (quiz4 != -1) {
            obtained+=quiz4;
            current_total+=5*credit;
        }
        if (mid != -1) {
            obtained+=mid;
            current_total+=(credit*100/4);
        }
        if (final_marks != -1) {
            obtained+=final_marks;
            current_total+=(credit*100/2);
        }
        if(current_total==0)
            return "F";
        double percentageMarks=(obtained/current_total)*100;
        if(percentageMarks>=80)
            return "A+";
        else if (percentageMarks>=75) {
            return "A";
        } else if (percentageMarks>=70) {
            return "A-";
        }else if (percentageMarks>=65) {
            return "B+";
        }else if (percentageMarks>=60) {
            return "B";
        }else if (percentageMarks>=55) {
            return "B-";
        }else if (percentageMarks>=50) {
            return "C+";
        }else if (percentageMarks>=45) {
            return "C";
        }else if (percentageMarks>=40) {
            return "D";
        }else
            return "F";
    }

    /**
     * To get the list of academic progress of student for table view
     * @param student Student object
     * @return List of academic records if any. Else return empty list
     */
    public ArrayList<AcademicProgressModel> getStudentProgressData(Student student) {
        ArrayList<AcademicProgressModel> studentData = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select t_coursecode, total_class, attended_class, quiz_1, quiz_2, mid_marks,quiz_3, quiz_4, final_marks, course_credit\n" +
                "from courses,(select t_coursecode, total_class, attended_class, quiz_1, quiz_2, mid_marks,quiz_3, quiz_4, final_marks\n" +
                "from teacher_takes_course ,(select * from student_takes_course where s_id=?) sub\n" +
                "where t_coursecode=s_coursecode) nsub where t_coursecode=course_code;";
        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
//                System.out.println(resultSet.getDouble(4));
                String courseCode=resultSet.getString(1);
                double total_class=resultSet.getInt(2);
                double attended_class=resultSet.getInt(3);
                Double quiz1=resultSet.getDouble(4);
                Double quiz2=resultSet.getDouble(5);
                Double mid=resultSet.getDouble(6);
                Double quiz3=resultSet.getDouble(7);
                Double quiz4=resultSet.getDouble(8);
                Double final_marks=resultSet.getDouble(9);
                Double credit=resultSet.getDouble(10);

                Double tempProgress=calculateProgress(quiz1, quiz2, quiz3, quiz4, mid, final_marks,credit);
                DecimalFormat decimalFormat=new DecimalFormat("0.00");
                Double progress=Double.parseDouble(decimalFormat.format(tempProgress));

                String grade=calculateGrade(quiz1, quiz2, quiz3, quiz4, mid, final_marks,credit);
                quiz1=(quiz1==-1?0:quiz1);
                quiz2=(quiz2==-1?0:quiz2);
                quiz3=(quiz3==-1?0:quiz3);
                quiz4=(quiz4==-1?0:quiz4);
                mid=(mid==-1?0:mid);
                final_marks=(final_marks == -1 ? 0 : final_marks);
                double attedance;
                if(total_class!=0) {
                    attedance = attended_class / total_class * 100;
                }
                else
                    attedance=100;
                attedance=Double.parseDouble(decimalFormat.format(attedance));
                studentData.add(new AcademicProgressModel(courseCode, attedance, quiz1, quiz2,mid, quiz3, quiz4, final_marks, progress, grade));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return studentData;
    }

    /**
     * To get the marks of a student for modification by a teacher
     * @param courseCode Course code of the course
     * @param examType Exam type of the exam
     * @return Hashmap containing student_id as key and exam_marks as value
     */
    public HashMap<String, Double> getStudentMarkList(String courseCode, String examType) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Year thisYear = Year.now();
        String query = "select student.s_id,"+examType+" from student, (select s_id,"+examType+" from student_takes_course where s_coursecode=? and academic_year=?) sub where student.s_id=sub.s_id;";
        ArrayList<String> studentList = new ArrayList<>();
        HashMap<String, Double> hashMap =new HashMap<>();

        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, courseCode);
            preparedStatement.setInt(2,Integer.parseInt(String.valueOf(thisYear)));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if(resultSet.getDouble(2)==-1)
                    return hashMap;
                studentList.add(resultSet.getString(1) + ": " + resultSet.getString(2)); //format: ID: FullName
                hashMap.put(resultSet.getString(1), resultSet.getDouble(2));
//                System.out.println(resultSet.getString(2));
            }
            return hashMap;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * For updating the information of the student
     * @param user User object of the student
     * @param newName Updated name of the student
     * @param newContact Updated contact information of the student
     * @param newDob Updated date of birth of the student
     */
    public void updateStudentInfo(User user,String newName, String newContact, String newDob){
        PreparedStatement userStatement=null;
        PreparedStatement studentStatement=null;
        String userTableName;
        if(user.getType().equals("t")){
            userTableName="teacher";
        }else if(user.getType().equals("s")) {
            userTableName="student";
        }
        else{
            userTableName="admins";
        }
        String userQuery="update users\n" +
                "set password=?\n" +
                "where email=?;";
        String studentQuery="update "+userTableName+" \n" +
                "set s_name=?, dob=?, s_contact=?\n" +
                "where s_email=?;";
        String teacherQuery="update "+userTableName+" \n" +
                "set t_name=?, dob=?, t_contact=?\n" +
                "where t_email=?;";
        String adminQuery="update "+userTableName+" \n" +
                "set admin_name=?, dob=?, admin_contact=?\n" +
                "where admin_email=?;";
        try {
            Connection connection=connectToDB("projectDb", "postgres", "tukasl");

            userStatement=connection.prepareStatement(userQuery);
            userStatement.setString(1,user.getPassword());
            userStatement.setString(2,user.getEmail());
            userStatement.executeUpdate();

            if(userTableName.equals("student"))
            studentStatement=connection.prepareStatement(studentQuery);
            else if(userTableName.equals("teacher"))
                studentStatement=connection.prepareStatement(teacherQuery);
            else
                studentStatement=connection.prepareStatement(adminQuery);
            studentStatement.setString(1,newName);
            studentStatement.setString(2, newDob);
            studentStatement.setString(3,newContact);
            studentStatement.setString(4,user.getEmail());
            studentStatement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public double getCourseCredit(String courseCode){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select course_credit from courses where Course_code=?;";

        try {
            Connection connection = connectToDB("projectDb", "postgres", "tukasl");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, courseCode);
            resultSet = preparedStatement.executeQuery();
            double credit =-1;
            if(resultSet.next()){
                credit=resultSet.getDouble(1);
            }
            return credit;
        } catch (SQLException e) {
            System.out.println("Exception getting course credit");
            throw new RuntimeException(e);
        }
    }


}
