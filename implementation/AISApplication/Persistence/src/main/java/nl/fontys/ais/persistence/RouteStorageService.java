package nl.fontys.ais.persistence;

import java.util.List;
import nl.fontys.ais.datarecords.RouteData;

public interface RouteStorageService extends StorageService<RouteData> {
    
    int getFirstClassPassengers(RouteData rd) throws PersistenceException;
    int getEconomyPlusClassPassengers(RouteData rd) throws PersistenceException;
    int getEconomyClassPassengers(RouteData rd) throws PersistenceException;
    int getExtraOptionPassengers(RouteData rd) throws PersistenceException;
    int getExtraLegroomPassengers(RouteData rd) throws PersistenceException;
    int getExtraFoodPassengers(RouteData rd) throws PersistenceException;
    int getExtraLuggagePassengers(RouteData rd) throws PersistenceException;
    double getBookingAndFlightPriceDifference(RouteData rd) throws PersistenceException;
    int getNumberOfRoutesAssociatedOnSameFlightAsRoute(RouteData rd) throws PersistenceException;
    List<RouteData> getMatchingRoutesByStartOrEndAirport(String pattern) throws PersistenceException;
}
