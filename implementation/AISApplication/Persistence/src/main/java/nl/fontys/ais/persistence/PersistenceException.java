package nl.fontys.ais.persistence;

import java.sql.SQLException;

public class PersistenceException extends SQLException {
    public PersistenceException(String message, Throwable cause){
        super(message, cause);
    }
    public PersistenceException(String message){
        super(message);
    }
}
