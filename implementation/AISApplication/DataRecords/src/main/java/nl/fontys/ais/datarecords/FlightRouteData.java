package nl.fontys.ais.datarecords;

import java.time.LocalDate;
import java.time.LocalTime;

public record FlightRouteData(Integer ID, LocalDate creationDate, LocalTime estDuration, String startAirport, int timezoneStartAirport, String endAirport, int timezoneEndAirport, int cost, LocalDate departureDate, LocalTime departureTime, LocalDate arrivalDate, LocalTime arrivalTime, PlaneData plane) {
    public FlightRouteData( LocalDate creationDate, LocalTime estDuration, String startAirport, int timezoneStartAirport,  String endAirport, int timezoneEndAirport, int cost, LocalDate departureDate, LocalTime departureTime, LocalDate arrivalDate, LocalTime arrivalTime, PlaneData plane){
        this(1,creationDate, estDuration, startAirport,timezoneStartAirport,endAirport,timezoneEndAirport, cost, departureDate,departureTime,arrivalDate,arrivalTime, plane);
    }
}