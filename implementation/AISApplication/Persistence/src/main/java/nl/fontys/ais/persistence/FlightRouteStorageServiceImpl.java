package nl.fontys.ais.persistence;

import nl.fontys.ais.datarecords.FlightRouteData;

import java.util.List;

public class FlightRouteStorageServiceImpl implements FlightRouteStorageService{

    /**
     * {@inheritDoc}
     */
    @Override
    public FlightRouteData add(FlightRouteData data) throws PersistenceException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FlightRouteData> getAll() throws PersistenceException {
        return UtilityClass.getFlights();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(FlightRouteData data) throws PersistenceException {

    }
}
