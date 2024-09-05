/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nl.fontys.ais.businesslogic;

import java.util.List;
import nl.fontys.ais.datarecords.RouteData;
import nl.fontys.ais.persistence.PersistenceException;
import nl.fontys.ais.persistence.RouteStorageService;


/**
 * linking between GUI and Persistence
 * @author nedus
 */
public class StatisticsCalculator {

    private final RouteStorageService rss;

    public StatisticsCalculator(RouteStorageService rss) {
        this.rss = rss;
    }
    
    public double getTotalRevenueOfRoute(RouteData rd) throws PersistenceException{
        double bookingFlightPriceDiff = this.rss.getBookingAndFlightPriceDifference(rd);
        double numRoutesAssociated = this.rss.getNumberOfRoutesAssociatedOnSameFlightAsRoute(rd);
        
        double totalRevenueOfRoute = bookingFlightPriceDiff / numRoutesAssociated;
        
        return totalRevenueOfRoute;
    }
    
    public int economyPassengers(RouteData rd) throws PersistenceException {
        return this.rss.getEconomyClassPassengers(rd);
    }
    
    public int firstPassengers(RouteData rd) throws PersistenceException {
        return this.rss.getFirstClassPassengers(rd);
    }
    
    public int economyPlusPassengers(RouteData rd) throws PersistenceException {
        return this.rss.getEconomyPlusClassPassengers(rd);
    }
    
    public int extraOptionPassengers(RouteData rd) throws PersistenceException{
        return this.rss.getExtraOptionPassengers(rd);
    }
    
    public int extraLegroomPassengers(RouteData rd) throws PersistenceException{
        return this.rss.getExtraLegroomPassengers(rd);
    }
    
    public int extraFoodPassengers(RouteData rd) throws PersistenceException{
        return this.rss.getExtraFoodPassengers(rd);
    }
    
    public int extraLuggagePassengers(RouteData rd) throws PersistenceException{
        return this.rss.getExtraLuggagePassengers(rd);
    }
    
    public List<RouteData> getRoutes() throws PersistenceException{
            return this.rss.getAll();
    }
    
    public List<RouteData> getRoutes(String pattern) throws PersistenceException{
            return this.rss.getMatchingRoutesByStartOrEndAirport(pattern);
    }
    
}
