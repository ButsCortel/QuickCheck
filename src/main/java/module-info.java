module com.buts.research {
    requires javafx.controls;
    requires javafx.fxml;
	requires com.jfoenix;

    opens com.buts.research to javafx.fxml;
    exports com.buts.research;
}