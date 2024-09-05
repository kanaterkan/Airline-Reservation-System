/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package nl.fontys.ais.businesslogic;

import java.time.LocalTime;
import java.util.List;
import nl.fontys.ais.datarecords.RouteData;
import nl.fontys.ais.persistence.PersistenceException;
import nl.fontys.ais.persistence.RouteStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


/**
 *
 * @author nedus
 */
public class StatisticsCalculatorTest {

    RouteData rd = new RouteData(LocalTime.of(1, 10), "VNO", "AMS");

    @Mock
    RouteStorageService rss;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test of getTotalRevenueOfRoute method, of class StatisticsCalculator.
     * @throws nl.fontys.ais.persistence.PersistenceException - ignore
     */
    @Test
    public void testGetTotalRevenueOfRoute() throws PersistenceException{
        StatisticsCalculator statCalc = new StatisticsCalculator(rss);
        when(rss.getBookingAndFlightPriceDifference(rd)).thenReturn(100.0);
        when(rss.getNumberOfRoutesAssociatedOnSameFlightAsRoute(rd)).thenReturn(2);

        var actual = statCalc.getTotalRevenueOfRoute(rd);

        assertThat(actual).isEqualTo(50.0);
    }

    /**
     * Test of economyPassengers method, of class StatisticsCalculator.
     * @throws nl.fontys.ais.persistence.PersistenceException - ignore
     */
    @Test
    public void testgetEconomyPassengers() throws PersistenceException {
        StatisticsCalculator statCalc = new StatisticsCalculator(rss);
        when(rss.getEconomyClassPassengers(rd)).thenReturn(5);

        var actual = statCalc.economyPassengers(rd);

        assertThat(actual).isEqualTo(5);
    }

    /**
     * Test of firstPassengers method, of class StatisticsCalculator.
     * @throws nl.fontys.ais.persistence.PersistenceException - ignore
     */
    @Test
    public void testFirstPassengers() throws PersistenceException {
        StatisticsCalculator statCalc = new StatisticsCalculator(rss);
        when(rss.getFirstClassPassengers(rd)).thenReturn(2);

        var actual = statCalc.firstPassengers(rd);

        assertThat(actual).isEqualTo(2);
    }

    /**
     * Test of economyPlusPassengers method, of class StatisticsCalculator.
     * @throws nl.fontys.ais.persistence.PersistenceException - ignore
     */
    @Test
    public void testEconomyPlusPassengers() throws PersistenceException {
        StatisticsCalculator statCalc = new StatisticsCalculator(rss);
        when(rss.getEconomyPlusClassPassengers(rd)).thenReturn(3);

        var actual = statCalc.economyPlusPassengers(rd);

        assertThat(actual).isEqualTo(3);
    }

    /**
     * Test of extraOptionPassengers method, of class StatisticsCalculator.
     * @throws nl.fontys.ais.persistence.PersistenceException - ignore
     */
    @Test
    public void testExtraOptionPassengers() throws PersistenceException {
        StatisticsCalculator statCalc = new StatisticsCalculator(rss);
        when(rss.getExtraOptionPassengers(rd)).thenReturn(10);

        var acual = statCalc.extraOptionPassengers(rd);

        assertThat(acual).isEqualTo(10);
    }

    /**
     * Test of extraLegroomPassengers method, of class StatisticsCalculator.
     * @throws nl.fontys.ais.persistence.PersistenceException - ignore
     */
    @Test
    public void testExtraLegroomPassengers() throws PersistenceException {
        StatisticsCalculator statCalc = new StatisticsCalculator(rss);
        when(rss.getExtraLegroomPassengers(rd)).thenReturn(5);

        var acual = statCalc.extraLegroomPassengers(rd);

        assertThat(acual).isEqualTo(5);
    }

    /**
     * Test of extraFoodPassengers method, of class StatisticsCalculator.
     * @throws nl.fontys.ais.persistence.PersistenceException - ignore
     */
    @Test
    public void testExtraFoodPassengers() throws PersistenceException {
        StatisticsCalculator statCalc = new StatisticsCalculator(rss);
        when(rss.getExtraFoodPassengers(rd)).thenReturn(5);

        var acual = statCalc.extraFoodPassengers(rd);

        assertThat(acual).isEqualTo(5);
    }

    /**
     * Test of extraLuggagePassengers method, of class StatisticsCalculator.
     * @throws nl.fontys.ais.persistence.PersistenceException - ignore
     */
    @Test
    public void testExtraLuggagePassengers() throws PersistenceException {
        StatisticsCalculator statCalc = new StatisticsCalculator(rss);
        when(rss.getExtraLuggagePassengers(rd)).thenReturn(5);

        var acual = statCalc.extraLuggagePassengers(rd);

        assertThat(acual).isEqualTo(5);
    }

    /**
     * Test of getRoutes method, of class StatisticsCalculator.
     * @throws nl.fontys.ais.persistence.PersistenceException - ignore
     */
    @Test
    public void testGetRoutes() throws PersistenceException {
        StatisticsCalculator statCalc = new StatisticsCalculator(rss);

        List<RouteData> list = rss.getAll();
        when(rss.getAll()).thenReturn(list);
        list.add(rd);

        var actual = statCalc.getRoutes();

        assertThat(actual.size()).isEqualTo(1);
    }

    /**
     * Test of getRoutes method, of class StatisticsCalculator.
     * @throws nl.fontys.ais.persistence.PersistenceException - ignore
     */
    @Test
    public void testGetRoutesByStart() throws PersistenceException {
        StatisticsCalculator statCalc = new StatisticsCalculator(rss);

            List<RouteData> list = rss.getMatchingRoutesByStartOrEndAirport(rd.departureAirport());
            list.add(rd);
            when(rss.getMatchingRoutesByStartOrEndAirport(rd.departureAirport())).thenReturn(list);

        var searchByStart = statCalc.getRoutes("VNO");

        assertThat(searchByStart.get(0)).isEqualTo(rd);

    }

    @Test
    public void testGetRoutesByEnd() throws PersistenceException {
        StatisticsCalculator statCalc = new StatisticsCalculator(rss);

            List<RouteData> list = rss.getMatchingRoutesByStartOrEndAirport(rd.arrivalAirport());
            list.add(rd);
            when(rss.getMatchingRoutesByStartOrEndAirport(rd.arrivalAirport())).thenReturn(list);

        var searchByEnd = statCalc.getRoutes("AMS");

        assertThat(searchByEnd.get(0)).isEqualTo(rd);

    }

    @Test
    public void testGetRoutesByNullReturnsEmpty() throws PersistenceException{
        StatisticsCalculator statCalc = new StatisticsCalculator(rss);

            List<RouteData> list = rss.getMatchingRoutesByStartOrEndAirport(rd.arrivalAirport());
            list.add(rd);
            when(rss.getMatchingRoutesByStartOrEndAirport(rd.arrivalAirport())).thenReturn(list);

        var searchByEnd = statCalc.getRoutes("");

        assertThat(searchByEnd.contains(rd)).isFalse();

    }

}
