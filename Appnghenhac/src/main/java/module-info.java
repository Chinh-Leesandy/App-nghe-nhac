module com.mycompany.appnghenhac {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.appnghenhac to javafx.fxml;
    exports com.mycompany.appnghenhac;
}
