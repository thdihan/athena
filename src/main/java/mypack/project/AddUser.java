package mypack.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import userPack.Courses;
import userPack.Student;
import userPack.Teacher;
import userPack.User;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


public class AddUser {
    @FXML
    Pane infoPane;
    @FXML
    Label warning;
    @FXML
    Pane warning_box;
    public Pane getInfoPane() {
        return infoPane;
    }

    private ArrayList<User> newUsers = new ArrayList<>();
    private ArrayList<Student> newStudents = new ArrayList<>();

    private ArrayList<Teacher> newTeacher = new ArrayList<>();
    private ArrayList<Courses> newCourses=new ArrayList<>();

    @FXML
    public void userDataFileChooseBtn_clicked(ActionEvent event) {
        warning.setText("");
        warning_box.setVisible(false);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select PDF File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File csvFile = fileChooser.showOpenDialog(null);


        LoginController loginController = new LoginController();
        if (csvFile != null && csvFile.getName().endsWith(".csv")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] columns = line.split(",");

                    // Adding User
                    User tempUser = new User(columns[0], loginController.encryptPassword(columns[1]));
                    tempUser.setType(columns[2]);
                    newUsers.add(tempUser);

                    // Adding Student
//                    public Student(String id, String name, String email, String dept, String semester, Date dob, String contact)
                    if (tempUser.getType().equals("s")) {
                        Student tempStudent = new Student(columns[3], columns[4], tempUser.getEmail(), columns[5], columns[6], columns[7], columns[8]);
                        newStudents.add(tempStudent);
                    }


                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void teacherDataChoose_clicked() {
        warning.setText("");
        warning_box.setVisible(false);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select PDF File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File csvFile = fileChooser.showOpenDialog(null);


        LoginController loginController = new LoginController();
        if (csvFile != null && csvFile.getName().endsWith(".csv")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] columns = line.split(",");

                    // Adding User
                    User tempUser = new User(columns[0], loginController.encryptPassword(columns[1]));
                    tempUser.setType(columns[2]);
                    newUsers.add(tempUser);

                    // Adding Teacher
//                    "insert into teacher values('1233456','Jamal','z','CSE','1995-01-01','01711111111');"
//                    public Teacher(String id, String name, String email, String dept, Date dob, String contact)
                    if (tempUser.getType().equals("t")) {
                        Teacher tempTeacher = new Teacher(columns[3], columns[4], tempUser.getEmail(), columns[5], columns[6], columns[7]);
                        newTeacher.add(tempTeacher);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }



    @FXML
    public void addUsersBtn_clicked(ActionEvent event) throws SQLException {
        DbUtilities dbUtilities = new DbUtilities();
        for (int i = 0; i < newUsers.size(); i++) {
            dbUtilities.addUser(newUsers.get(i));
        }
        for (int i = 0; i < newStudents.size(); i++) {
            dbUtilities.addStudent(newStudents.get(i));
        }
        newUsers.clear();
        warning_box.setVisible(true);
        warning.setText("Added Successfully");
    }
    @FXML
    public void addTeacher_clicked(ActionEvent event) throws SQLException {

        DbUtilities dbUtilities = new DbUtilities();
        for (int i = 0; i < newUsers.size(); i++) {
            dbUtilities.addUser(newUsers.get(i));
        }

        for (int i = 0; i < newTeacher.size(); i++) {
            dbUtilities.addTeacher(newTeacher.get(i));
        }
        warning_box.setVisible(true);
        warning.setText("Added Successfully");

    }


    @FXML
    public void coursecsvfile_click(ActionEvent event) throws SQLException {
        warning.setText("");
        warning_box.setVisible(false);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select PDF File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File csvFile = fileChooser.showOpenDialog(null);
        if (csvFile != null && csvFile.getName().endsWith(".csv")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] columns = line.split(",");
                    //Adding Courses
                    // public Courses(String code, String title, String dept, String offered_dept, Double credit)
//                    Student tempStudent = new Student(columns[3],columns[4],tempUser.getEmail(),columns[5],columns[6],columns[7],columns[8]);
//                    newStudents.add(tempStudent);
                    Courses tempcourse = new Courses(columns[0], columns[1], columns[2], columns[3], Double.parseDouble(columns[4]),columns[5]);
                    System.out.println(tempcourse.getCode()+ " " +tempcourse.getCredit());
                    newCourses.add(tempcourse);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    public void addcoursebutton_click (ActionEvent event) throws SQLException{

        DbUtilities dbUtilities = new DbUtilities();
        for (int i = 0; i < newCourses.size(); i++) {
            dbUtilities.addCourses(newCourses.get(i));
        }

        warning_box.setVisible(true);
        warning.setText("Added Successfully");


    }
}



