module nl.fontys.ais.persistence_module { 
    requires nl.fontys.ais.datarecords_module;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires java.naming;
    exports nl.fontys.ais.persistence;
}
