
package nl.fontys.ais.businesslogic.objects;

import nl.fontys.ais.datarecords.AirportData;

/**
 *
 * @author Danylo
 */
public class Airport {
    
    private final AirportData airportData;
    
    public Airport(AirportData airportData){
        this.airportData = airportData;
    }
    
    public AirportData getData(){
        return this.airportData;
    }
}
