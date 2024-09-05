    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nl.fontys.ais.businesslogic;

import nl.fontys.ais.persistence.PersistenceAPI;

/**
 * @author Danylo
 */

record BusinessLogicAPIImpl(PersistenceAPI persistenceAPI) implements BusinessLogicAPI {

    @Override
    public FlightManager getFlightManager() {
        return new FlightManager(persistenceAPI.getFlightStorageService(), persistenceAPI.getRouteStorageService(), persistenceAPI.getPlaneStorageService(), persistenceAPI.getFlightRouteStorageService());
    }

    @Override
    public BookingManager getBookingManager(){
        return new BookingManager(persistenceAPI.getBookingStorageService(), persistenceAPI.getCustomerStorageService(), persistenceAPI.getPassengerStorageService(), persistenceAPI.getMenuStorageService(), persistenceAPI.getSeatStorageService());
    }

    @Override
    public StatisticsCalculator getStatisticsCalculator() {
        return new StatisticsCalculator(persistenceAPI.getRouteStorageService());
    }
}
