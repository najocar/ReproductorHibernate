module com.group.reproductorjava {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires java.persistence;

//    opens com.group1.reproductorjava to javafx.fxml;
    opens com.group.reproductorjava;
    opens com.group.reproductorjava.controller to javafx.fxml;
//    opens com.group1.reproductorjava.model.Entity to javafx.base;
//    opens com.group1.reproductorjava.model.Entity to org.hibernate.orm.core, javafx.base;
    opens com.group.reproductorjava.model.Entity;

    exports com.group.reproductorjava;
}