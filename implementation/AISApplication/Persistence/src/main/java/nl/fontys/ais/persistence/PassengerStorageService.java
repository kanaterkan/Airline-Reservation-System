package nl.fontys.ais.persistence;

import java.util.List;
import nl.fontys.ais.datarecords.PassengerData;

public interface PassengerStorageService extends StorageService<PassengerData> {
    List<PassengerData> getPassengersByBookingId(int bookingId) throws PersistenceException;
    
    boolean setSeatNumber(String seatNumber, PassengerData pd) throws PersistenceException;
    
    boolean setMenuItem(String itemName, PassengerData pd) throws PersistenceException;
    
    boolean setLuggageWeight(double weight, PassengerData pd) throws PersistenceException;
    
    
}
