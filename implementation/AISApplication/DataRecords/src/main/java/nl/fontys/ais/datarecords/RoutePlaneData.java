package nl.fontys.ais.datarecords;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public record RoutePlaneData(RouteData routeData, PlaneData planeData, int departureTimezone, LocalDateTime departure, int arrivalTimezone, LocalDateTime arrival) {
    public PlaneData getPlaneData() {
        return planeData;
    }
    public RouteData getRouteData() {
        return routeData;
    }
    public LocalTime getDepartureTime(){
        return departure.toLocalTime();
    }
    public LocalTime getArrivalTime(){
        return arrival.toLocalTime();
    }
    public LocalDate getDepartureDate(){
        return departure.toLocalDate();
    }
    public LocalDate getArrivalDate(){
        return arrival.toLocalDate();
    }

    public String getDepartureTimezone(){
        int t = departureTimezone;
        return "UTC" + (t >= 0 ? "+" : "") + t;
    }
    public String getArrivalTimezone(){
        int t = arrivalTimezone;
        return "UTC" + (t >= 0 ? "+" : "") + t;
    }
}
