/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package nl.fontys.ais.businesslogic;



/**
 *
 * @author Danylo
 */
public interface BusinessLogicAPI {
    
          FlightManager getFlightManager();

          BookingManager getBookingManager();
          
          StatisticsCalculator getStatisticsCalculator();
        
}
