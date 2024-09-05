package nl.fontys.ais.persistence;

import nl.fontys.ais.datarecords.AirportData;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AirportStorageServiceImpl implements AirportStorageService{

    List<AirportData> list = new ArrayList<>();
    /**
     * {@inheritDoc}
     */
    @Override
    public AirportData add(AirportData data) {
        list.add(data);
        //UtilityClass.addAirport(data);
        return data;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<AirportData> getAll() {
        try {
            return UtilityClass.getAirports();
        } catch (PersistenceException ex) {
            Logger.getLogger(AirportStorageServiceImpl.class.getName()).log(Level.INFO, null, ex);
        }
        return null;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(AirportData data) {

    }
}
