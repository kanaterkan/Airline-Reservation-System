package nl.fontys.ais.persistence;

import java.util.List;
import nl.fontys.ais.datarecords.BookingData;

public interface BookingStorageService extends StorageService<BookingData> {
    
    @Override
    List<BookingData> getAll() throws PersistenceException;
    
    List<BookingData> getBookingsById(int id) throws PersistenceException;
}
