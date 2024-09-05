package nl.fontys.ais.businesslogic.objects;

import nl.fontys.ais.datarecords.FlightRouteData;
import nl.fontys.ais.datarecords.PlaneData;

import java.time.LocalDate;
import java.time.LocalTime;

public class FlightRoute {
    FlightRouteData flightRouteData;

    public FlightRoute(FlightRouteData flightRouteData){
        this.flightRouteData = flightRouteData;
    }
    public Integer getID(){
        return this.flightRouteData.ID();
    }
    public Integer getCost(){
        return this.flightRouteData.cost();
    }

    public LocalDate getCreationDate(){
        return this.flightRouteData.creationDate();
    }
    public String getDepartureAirport(){
        return this.flightRouteData.startAirport();
    }
    public String getArrivalAirport(){
        return this.flightRouteData.endAirport();
    }
    public LocalDate getDepartureDate(){
        return this.flightRouteData.departureDate();
    }
    public LocalTime getDepartureTime(){
        return this.flightRouteData.departureTime();
    }
    public LocalDate getArrivalDate(){
        return this.flightRouteData.arrivalDate();
    }
    public LocalTime getArrivalTime(){
        return this.flightRouteData.arrivalTime();
    }
    public PlaneData getPlane(){
        return this.flightRouteData.plane();
    }

    public String getDepartureTimezone(){
        int t = this.flightRouteData.timezoneStartAirport();
        return "UTC" + (t >= 0 ? "+" : "") + t;
    }
    public String getArrivalTimezone(){
        int t = this.flightRouteData.timezoneEndAirport();
        return "UTC" + (t >= 0 ? "+" : "") + t;
    }

    public LocalTime getEstDuration(){
        return this.flightRouteData.estDuration();
    }


    @Override
    public String toString() {
        return "FlightRoute{" +
                "flightRouteData=" + flightRouteData +
                '}';
    }
}
