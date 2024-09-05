package nl.fontys.ais.datarecords;

// change 'estDuration' type to LocalTime, 4 database accuracy
import java.time.LocalTime;

public record RouteData(int ID, int cost, LocalTime estDuration, String departureAirport, Integer departureTimezone, String arrivalAirport, Integer arrivalTimezone) {

    public RouteData(int cost, LocalTime estDuration, String startAirport, int timezoneStartAirport, String endAirport, int timezoneEndAirport) {
        this(1, cost, estDuration, startAirport, timezoneStartAirport, endAirport, timezoneEndAirport);
    }

    public RouteData(LocalTime estDuration, String startAirport, String endAirport) {
        this(1, 1, estDuration, startAirport,null,endAirport,null);
    }

    public RouteData(int id, LocalTime durt, String start, String end) {
        this(id,0, durt, start,null, end,null);
    }

    @Override
    public String toString() {
        return departureAirport() + "-" + arrivalAirport() + ", estDuration " + estDuration();
    }

}
