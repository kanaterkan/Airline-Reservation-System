package nl.fontys.ais.datarecords;

public record AirportData(String IATA, String airportName, String airportLocation) {
    //implement timezone thing later
    //matches database table 5/22/2023
}