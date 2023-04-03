package mypack.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import userPack.Pair;
import userPack.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PreviousResultController {

    @FXML
    private Pane infoPane;
    @FXML
    private Accordion resultBox;

    public Node getHBox(String courseName, String grade) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultBox.fxml"));
        loader.load(); //loader.load() must be used otherwise controller isn't created
        ResultBoxController resultBoxController=loader.getController();

        resultBoxController.initiateSingleCourse(courseName,grade);
        Node childNode= resultBoxController.getSingleCourse();
        return childNode;
    }

    public TitledPane getTitlePane(ArrayList <Node> allCourseResult) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultBox.fxml"));
        loader.load(); //loader.load() must be used otherwise controller isn't created
        ResultBoxController resultBoxController=loader.getController();

        resultBoxController.initiateTitlePane(allCourseResult);
        TitledPane childNode= resultBoxController.getTitlePane();
        return childNode;
    }

    public Double calculateCgpa(ArrayList<Pair<String,String,Double>> results){
        HashMap<String,Double> cgpa = new HashMap<>();
        cgpa.put("A+",4.00);
        cgpa.put("A",3.75);
        cgpa.put("A-",3.50);
        cgpa.put("B+",3.25);cgpa.put("B",3.00);
        cgpa.put("B-",2.75);
        cgpa.put("C",2.50);
        cgpa.put("D",2.25);
        cgpa.put("F",0.00);

        double total_cg = 0;
        double total_credit = 0;
        for(int i = 0;i < results.size();i++) {
            String grade =results.get(i).getValue();
                if(grade.equals("F")){
                    return 0.0;

                }
                System.out.println(grade);
                total_cg += cgpa.get(grade) * results.get(i).getCredit();

            total_credit += results.get(i).getCredit();
        }
        return total_cg/total_credit;
    }
    public  void initiate(Student currentStudent) throws IOException {


        // Get Result from Database
        DbUtilities dbUtilities = new DbUtilities();

        int currentSemester = Integer.parseInt(currentStudent.getSemester());
        System.out.println("Current Sem " + currentSemester);
        ArrayList <TitledPane> allSemester = new ArrayList<>();
        for(int j = 1;j < currentSemester;j++) {
            ArrayList<Pair<String,String,Double>> results = dbUtilities.getResults(currentStudent,Integer.toString(j));
            ArrayList <Node> allCourseResult = new ArrayList<>();
            for(int i = 0;i < results.size();i++) {
                Node courseResult = getHBox(results.get(i).getKey(),results.get(i).getValue());
                allCourseResult.add(courseResult);
            }
            double cgpa = calculateCgpa(results);
            Node courseResult = getHBox("CGPA : ",Double.toString(cgpa));
            allCourseResult.add(courseResult);
            TitledPane semesterTitlePane = getTitlePane(allCourseResult);
            semesterTitlePane.setText("Semester : "+j);
            allSemester.add(semesterTitlePane);
        }

        resultBox.getPanes().addAll(allSemester);
    }
    public Pane getPane() {
        return infoPane;
    }

    public  String getUiName() {
        return "Previous Result";
    }

}
