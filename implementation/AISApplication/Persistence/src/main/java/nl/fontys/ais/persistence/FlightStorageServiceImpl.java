/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nl.fontys.ais.persistence;

import nl.fontys.ais.datarecords.FlightData;
import nl.fontys.ais.datarecords.RouteData;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DanyloA
 */
class FlightStorageServiceImpl implements FlightStorageService {
    
    List<FlightData> list = new ArrayList<>();
    /**
     * {@inheritDoc}
     */
    @Override
    public FlightData add(FlightData flightData) throws PersistenceException {
        UtilityClass.addFlight(flightData);
        return flightData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FlightData> getAll() throws PersistenceException {
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(FlightData data) {
        UtilityClass.deleteFlight(data.ID());
    }
}
