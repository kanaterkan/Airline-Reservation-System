package nl.fontys.ais.persistence;

import java.util.ArrayList;
import nl.fontys.ais.datarecords.SeatData;

import java.util.List;

public class SeatStorageServiceImpl implements SeatStorageService {
    @Override
    public SeatData add(SeatData data) throws PersistenceException {
        return null;
    }

    @Override
    public List<SeatData> getAll() throws PersistenceException {
        return UtilityClass.getSeats();
    }

    @Override
    public void delete(SeatData data) throws PersistenceException {

    }
}
