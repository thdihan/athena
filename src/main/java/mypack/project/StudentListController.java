package mypack.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import userPack.Courses;
import userPack.Student;
import userPack.Teacher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentListController {

    @FXML
    private Pane infoPane;

    @FXML
    private VBox list_vbox;


    public HBox getHbox(String code, String name, String dept) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("listBox.fxml"));
        loader.load(); //loader.load() must be used otherwise controller isn't created
        ListBoxController listBoxController = loader.getController();
        HBox childNode = listBoxController.initiate(code,name,dept);
        return childNode;
    }
    public void initiate(String listType) throws SQLException, IOException {
        ArrayList<Student> allStudent = new ArrayList<>();
        ArrayList<Teacher> allTeachers = new ArrayList<>();
        ArrayList<Courses> allCourses = new ArrayList<>();

        ArrayList<HBox> hboxList = new ArrayList<>();

        DbUtilities dbUtilities = new DbUtilities();
        if(listType.equals("studentList")) {
            allStudent = dbUtilities.getAllStudentInfo();
            hboxList.add(getHbox("Student ID","Student Name","Department"));
            for(int i = 0;i < allStudent.size();i++) {
                hboxList.add(getHbox(allStudent.get(i).getId(),allStudent.get(i).getName(),allStudent.get(i).getDept()));
            }
        }

        else if(listType.equals("teacherList")) {
            allTeachers = dbUtilities.getAllTeacherInfo();
            hboxList.add(getHbox("Teacher ID","Teacher Name","Department"));
            for(int i = 0;i < allTeachers.size();i++) {
                hboxList.add(getHbox(allTeachers.get(i).getId(),allTeachers.get(i).getName(),allTeachers.get(i).getDept()));
            }
        }

        else if(listType.equals("courseList")) {
            allCourses = dbUtilities.getAllCourses();
            hboxList.add(getHbox("Course Code","Course Title","Offered Department"));
            for(int i = 0;i < allCourses.size();i++) {
                hboxList.add(getHbox(allCourses.get(i).getCode(),allCourses.get(i).getTitle(),allCourses.get(i).getOffered_dept()));
            }
        }
        hboxList.get(0).setStyle("-fx-background-color: #36373E");
        list_vbox.getChildren().addAll(hboxList);
    }
    public Pane getInfoPane(){
        return infoPane;
    }
}
