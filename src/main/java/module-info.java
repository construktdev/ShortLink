module de.construkter.shortlink {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.net;
    requires java.desktop;


    opens de.construkter.shortlink to javafx.fxml;
    exports de.construkter.shortlink;
}