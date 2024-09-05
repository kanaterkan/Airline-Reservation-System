/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package nl.fontys.ais.persistence;

/**
 *
 * @author Danylo
 */
public interface PersistenceAPI {

    FlightStorageService getFlightStorageService();
    AirportStorageService getAirportStorageService();
    PlaneStorageService getPlaneStorageService();

    RouteStorageService getRouteStorageService();
    FlightRouteStorageService getFlightRouteStorageService();

    MenuStorageService getMenuStorageService();

    BookingStorageService getBookingStorageService();

    CustomerStorageService getCustomerStorageService();

    PassengerStorageService getPassengerStorageService();

    SeatStorageService getSeatStorageService();
}
