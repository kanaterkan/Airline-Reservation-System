module nl.fontys.ais.gui_mod {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.base;
    requires nl.fontys.ais.businesslogic_mod;
    requires nl.fontys.ais.datarecords_module;
    requires nl.fontys.ais.persistence_module;
    requires java.sql;
    requires java.desktop;

    opens nl.fontys.ais.gui to javafx.fxml;
    exports nl.fontys.ais.gui;
}
