/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package nl.fontys.ais.businesslogic.objects;

import nl.fontys.ais.datarecords.FlightData;

/**
 *
 * @author Danylo
 */
public class Flight {

    private final FlightData flightData;

    public Flight(FlightData flightData) {
        this.flightData = flightData;
    }

    public FlightData getData() {
        return flightData;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightData=" + flightData +
                '}';
    }
}
