/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nl.fontys.ais.businesslogic;

import nl.fontys.ais.businesslogic.objects.*;
import nl.fontys.ais.datarecords.*;
import nl.fontys.ais.persistence.*;

import java.util.ArrayList;
import java.util.List;

import nl.fontys.ais.datarecords.PlaneData;
import nl.fontys.ais.datarecords.RouteData;

/**
 *
 * @author=ks
 */
public class FlightManager {

    private final FlightStorageService flightStorageService;
    private final RouteStorageService routeStorageService;
    private final PlaneStorageService planeStorageService;
    private final FlightRouteStorageService flightRouteStorageService;

    /**
     * Helper method to invoke add method on storage service in persistence layer
     *
     * @param flightData
     * @return flight data that was added
     * @throws PersistenceException
     * @throws IllegalArgumentException
     * @author=ks
     */
    public FlightData add(FlightData flightData) throws PersistenceException, IllegalArgumentException {

        if(flightData.routes().size() == 0){
            throw new IllegalArgumentException("No routes were added");
        }

        if(flightData.creationDate().isAfter(flightData.routes().get(0).getDepartureDate()) && !flightData.routes().get(0).getDepartureDate().isEqual(flightData.creationDate())){
            throw new IllegalArgumentException("Departure date has to be newer than today");
        }

        return flightStorageService.add(flightData);
    }

    /**
     * Helper method to invoke delete method on storage service in persistence layer
     *
     * @param flightID
     * @throws PersistenceException in case no db connection is established
     * @author=ks
     */
    public void delete(int flightID) throws PersistenceException {
        flightStorageService.delete(new FlightData(flightID));
    }

    private final ArrayList<Route> routes = new ArrayList<>();
    private final ArrayList<Plane> planes = new ArrayList<>();

    /**
     * Initializes all storage services
     * @param flightStorageService
     * @param routeStorageService
     * @param planeStorageService
     * @param flightRouteStorageService
     * @author=ks
     */
    public FlightManager(FlightStorageService flightStorageService, RouteStorageService routeStorageService, PlaneStorageService planeStorageService, FlightRouteStorageService flightRouteStorageService) {
        this.flightStorageService = flightStorageService;
        this.routeStorageService = routeStorageService;
        this.planeStorageService = planeStorageService;
        this.flightRouteStorageService = flightRouteStorageService;
    }

    /**
     * Helper method that invokes getAll method on storage service in persistence layer
     *
     * @return plane objects
     * @throws PersistenceException in case no db connection is established
     * @author=ks
     */
    public List<Plane> getPlanes() throws PersistenceException {
        this.planes.clear();
        planeStorageService.getAll().forEach(planeData -> {
            Plane p = new Plane(new PlaneData(planeData.ID(), planeData.numberOfSeats(), planeData.capacity()));
            planes.add(p);
        });
        return planes;
    }

    /**
     * Helper method that invokes getAll method on storage service in persistence layer
     *
     * @return route objects
     * @throws PersistenceException in case no db connection is established
     * @author=ks
     */
    public List<Route> getRoutes() throws PersistenceException {
        this.routes.clear();
        routeStorageService.getAll().forEach(routeData -> {
            Route r = new Route(new RouteData(routeData.ID(), routeData.cost(), routeData.estDuration(), routeData.departureAirport(), routeData.departureTimezone(), routeData.arrivalAirport(), routeData.arrivalTimezone()));
            routes.add(r);
        });
        return routes;
    }

    /**
     * Helper method that invokes getAll method on storage service in persistence layer
     *
     * @return
     * @throws PersistenceException in case no db connection is established
     * @author=ks
     */
    public List<FlightRoute> getFlights() throws PersistenceException{
        List<FlightRouteData> frd = flightRouteStorageService.getAll();
        List<FlightRoute> flights = new ArrayList<>();
        frd.forEach(fr -> flights.add(new FlightRoute(fr)));
        return flights;
    }
}
