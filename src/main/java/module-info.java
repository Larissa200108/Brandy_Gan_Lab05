module com.example.mponaganbrandylarissa_comp228sec_lab5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.example.mponaganbrandylarissa_comp228sec_lab5 to javafx.fxml;
    exports com.example.mponaganbrandylarissa_comp228sec_lab5;
}