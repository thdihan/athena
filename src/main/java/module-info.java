module mypack.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens mypack.project to javafx.fxml;
    exports mypack.project;
}